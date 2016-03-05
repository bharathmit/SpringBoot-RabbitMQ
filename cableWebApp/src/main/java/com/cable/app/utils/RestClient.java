package com.cable.app.utils;





import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;



@Component
@ApplicationScoped
@PropertySources(value = {@PropertySource("classpath:dev.properties")})
public class RestClient implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Value("${endpoint}")
	private String endpoint;
	
	
	public String createUrl(String uri){
		 return endpoint+"/"+uri;
	 }
	 
	 
}
