package com.cable.config;



import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.cable.payUMoney.PayURestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;



@ComponentScan(basePackages = { "com.cable" })
@Configuration
@Configurable
public class AppConfig {
	
	@Bean
	public ObjectMapper objectMapper(){
		return new ObjectMapper();
	}
	
	@Bean
	public PayURestTemplate payURestTemplate(){
		Map<String, String> config=new HashMap<String,String>();
		config.put("api", "https://test.payu.in");
		config.put("provider", "payu_paisa");
		config.put("surl", "http://localhost:8080/JavaIntegrationKit/index.html");
		config.put("furl", "http://localhost:8080/JavaIntegrationKit/index.html");
		
		return new PayURestTemplate("UFu3ed", "fE0aTrjr", config);
	}
	
	@Bean
	public RestTemplate restTemplate(){	
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
		return restTemplate;
	}	
	
	private ClientHttpRequestFactory clientHttpRequestFactory() {
	    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
	    factory.setReadTimeout(2000);
	    factory.setConnectTimeout(2000);
	    return factory;
    }
	
}
