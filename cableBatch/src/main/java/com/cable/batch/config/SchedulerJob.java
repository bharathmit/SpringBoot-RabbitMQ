
package com.cable.batch.config;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cable.batch.job.paygen.PayGenJobConfig;
import com.cable.rest.repository.GeneratePaymentJPARepo;

@DisallowConcurrentExecution
public class SchedulerJob extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		PayGenJobConfig job = ApplicationContextHolder.getContext().getBean(PayGenJobConfig.class);
		System.out.println("Quartz job started: ");
		try{
			job.performJob();          
        }catch(Exception exception){
            System.out.println("Job could not be executed : "+ exception.getMessage());
        }			
		System.out.println("Quartz job end");
		
	}

    
}
