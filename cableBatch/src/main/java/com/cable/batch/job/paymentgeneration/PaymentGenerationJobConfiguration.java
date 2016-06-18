package com.cable.batch.job.paymentgeneration;

import javax.sql.DataSource;

import org.cable.batch.common.listeners.LogProcessListener;
import org.cable.batch.common.listeners.ProtocolListener;
import org.cable.batch.common.utils.BatchUtils;
import org.cable.batch.common.utils.PayGenRowMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cable.rest.model.GeneratePayment;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Configuration
@EnableBatchProcessing
public class PaymentGenerationJobConfiguration {
	
	@Autowired
	private JobBuilderFactory jobBuilders;
 
	@Autowired
	private StepBuilderFactory stepBuilders;
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public ProtocolListener protocolListener(){
		return new ProtocolListener();
	}
 
	@Bean
	public LogProcessListener logProcessListener(){
		return new LogProcessListener();
	}    
	
	@Bean
	public Job paymentGenerationJob(){
		return jobBuilders.get("paymentGenerationJob")
				.listener(protocolListener())
				.start(paymentGenerationStep())
				.build();
	}	
	
	@Bean
	public Step paymentGenerationStep(){
		return stepBuilders.get("paymentGenerationStep")
				.<GeneratePayment,GeneratePayment>chunk(1) //important to be one in this case to commit after every line read
				.reader(paymentGenerationReader())
				//.processor(paymentGenerationProcessor())
				.writer(paymentGenerationWriter())
				.listener(logProcessListener())
				.faultTolerant()
				.skipLimit(10) //default is set to 0
				.skip(MySQLIntegrityConstraintViolationException.class)
				.build();
	}	
	
	
	@Bean
	public ItemReader<GeneratePayment> paymentGenerationReader(){
		
		JdbcCursorItemReader<GeneratePayment> reader = new JdbcCursorItemReader<GeneratePayment>();
		String sql = " select c.account_id,c.mobile,c.email_id,c.rent_amount,p.payment_due_date,p.payment_generate_date ,u.user_id "
						+ " from organization o, project p,connection_account c,user u  "
						+ "	where o.org_id=p.org_id and p.org_id=u.org_id and p.project_id=c.project_id  "
						+ " and o.`status`='ACTIVE' and p.`status`='ACTIVE' and c.`status`='ACTIVE' and c.pay_gen_month <> "+BatchUtils.getCurrentMonth()+" ";
		reader.setSql(sql);
		reader.setDataSource(dataSource);
		reader.setRowMapper(rowMapper());		
	
		return reader;
	}

	@Bean
	public PayGenRowMapper rowMapper(){
		return new PayGenRowMapper();
	}

	/** configure the processor related stuff */
    /*@Bean
    @StepScope
    public ItemProcessor<User, User> paymentGenerationProcessor() {
        return new NotifySubscribersItemProcessor();
    }*/
    
    @Bean
    public ItemWriter<GeneratePayment> paymentGenerationWriter() {
    	return new PaymentGenerationWriter();
    }
	
	
}
