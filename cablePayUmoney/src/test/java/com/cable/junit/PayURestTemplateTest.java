package com.cable.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.cable.config.AppConfig;
import com.cable.payUMoney.PayURestTemplate;
import com.cable.payUMoney.PaymentBuilder;
import com.cable.payUMoney.PaymentReturn;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class,classes=AppConfig.class)
public class PayURestTemplateTest {
	
	@Autowired
	PayURestTemplate payURestTemplate;
	
	@Test
    public void shouldCreatePayment() {
		PaymentBuilder paymentBuilder=new PaymentBuilder();
		
		
		paymentBuilder.setTxnid("b31ae8bb9edf9d7c61db");
		paymentBuilder.setAmount("100");
		paymentBuilder.setProductinfo("book");
		paymentBuilder.setFirstname("bharath");
		paymentBuilder.setEmail("bharathkumar.feb14@gmail.com");
		paymentBuilder.setPhone("9789944159");
		System.out.println("Test Method");
		
		PaymentReturn PaymentReturn=payURestTemplate.createPayment(paymentBuilder);
		
		System.out.println("Test Method Return"+PaymentReturn.getStatus());
		
		
		
    }

}
