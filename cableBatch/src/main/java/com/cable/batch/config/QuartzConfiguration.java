package com.cable.batch.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import com.cable.batch.job.paygen.PayGenJobConfig;



@Configuration
public class QuartzConfiguration {

	
	
	@Bean
	public JobDetailFactoryBean jobDetailFactoryBean(){
		JobDetailFactoryBean factory = new JobDetailFactoryBean();
		factory.setJobClass(SchedulerJob.class);
		factory.setDurability(true);
		factory.setGroup("cablebatch");
		factory.setName("paygen");
		return factory;
	}
	@Bean
	public CronTriggerFactoryBean cronTriggerFactoryBean(){
		CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
		stFactory.setJobDetail(jobDetailFactoryBean().getObject());
		stFactory.setStartDelay(1);
		stFactory.setCronExpression("0 0/1 * * * ?");
		stFactory.setName("mytrigger");
		stFactory.setGroup("cablebatch");
		return stFactory;
	}
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
		scheduler.setTriggers(cronTriggerFactoryBean().getObject());
		return scheduler;
	}
}
