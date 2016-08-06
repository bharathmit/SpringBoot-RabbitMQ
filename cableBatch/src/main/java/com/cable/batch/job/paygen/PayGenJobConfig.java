
package com.cable.batch.job.paygen;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;



import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import com.cable.batch.common.listeners.LogProcessListener;
import com.cable.batch.common.listeners.ProtocolListener;
import com.cable.batch.common.utils.BatchUtils;
import com.cable.rest.model.GeneratePayment;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Configuration
@EnableBatchProcessing
@Component
public class PayGenJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilders;

    @Autowired
    private StepBuilderFactory stepBuilders;
    
    @Autowired
    private JobLauncher jobLauncher;
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private PlatformTransactionManager jpaTransactionManager;
    
    
    /*@Bean
    public MapJobRegistry jobRegistry(){
    	return new MapJobRegistry();
    }
    
    @Bean 
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(){
    	JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor=new JobRegistryBeanPostProcessor();
    	jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry());
    	return jobRegistryBeanPostProcessor;
    }*/
    
    public Job paymentGenerationJob() {
        return jobBuilders.get("paymentGenerationJob")
                .listener(protocolListener())
                .start(paymentGenerationStep())
                .build();
    }
    
    public ProtocolListener protocolListener() {
        return new ProtocolListener();
    }

    
    public LogProcessListener logProcessListener() {
        return new LogProcessListener();
    }

    
    public Step paymentGenerationStep() {
        return stepBuilders.get("paymentGenerationStep")
        		.transactionManager(jpaTransactionManager)
                .<GeneratePayment, GeneratePayment> chunk(1) //important to be one in this case to commit after every line read
                .reader(paymentGenerationReader())
                //.processor(paymentGenerationProcessor())
                .writer(paymentGenerationWriter())
                .listener(logProcessListener())
                .faultTolerant()
                .skipLimit(10) //default is set to 0
                .skip(MySQLIntegrityConstraintViolationException.class)
                .build();
    }

    
    public ItemReader<GeneratePayment> paymentGenerationReader() {

        JdbcCursorItemReader<GeneratePayment> reader = new JdbcCursorItemReader<GeneratePayment>();
        String sql = " select c.account_id,c.mobile,c.email_id,c.rent_amount,p.payment_due_date,p.payment_generate_date ,u.user_id "
                + " from organization o, project p,connection_account c,user u  "
                + "	where o.org_id=p.org_id and p.org_id=u.org_id and p.project_id=c.project_id  "
                + " and o.`status`='ACTIVE' and p.`status`='ACTIVE' and c.`status`='ACTIVE' and c.pay_gen_month <> " + BatchUtils.getCurrentMonth() + " ";
        reader.setSql(sql);
        reader.setDataSource(dataSource);
        reader.setRowMapper(rowMapper());

        return reader;
    }

    
    public PayGenRowMapper rowMapper() {
        return new PayGenRowMapper();
    }

    /** configure the processor related stuff */
    /*@Bean
    @StepScope
    public ItemProcessor<User, User> paymentGenerationProcessor() {
        return new NotifySubscribersItemProcessor();
    }*/

    
    public ItemWriter<GeneratePayment> paymentGenerationWriter() {
        return new PayGenWriter();
    }
    
    public void performJob() {

		try {
			JobParameters jobParameters =new JobParametersBuilder().addLong("time",System.currentTimeMillis()).toJobParameters();
			JobExecution result = this.jobLauncher.run(this.paymentGenerationJob(), jobParameters);
			System.out.println("ExamResult Job completetion details : "+result.toString()); 
		} catch (Exception  e) {
			e.printStackTrace();
		}
    }


}
