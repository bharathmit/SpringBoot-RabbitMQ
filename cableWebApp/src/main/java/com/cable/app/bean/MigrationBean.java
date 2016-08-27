package com.cable.app.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.primefaces.event.FileUploadEvent;
import org.springframework.web.client.RestTemplate;

import com.cable.app.utils.RestClient;
import com.cable.rest.dto.ConnectionAccountDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@ManagedBean
@RequestScoped
@Log4j
public class MigrationBean {
	
	@ManagedProperty(value = "#{restTemplate}")
	@Getter
	@Setter
	RestTemplate restTemplate;

	@ManagedProperty(value = "#{restClient}")
	@Getter
	@Setter
	RestClient restClient;

	@ManagedProperty(value = "#{objectMapper}")
	@Getter
	@Setter
	ObjectMapper objectMapper;
	
	
	@Getter @Setter
	List<ConnectionAccountDto> accountList=new ArrayList<ConnectionAccountDto>();
	
	//Use multi map key is a row value
	//Validate first Excel ---mobile & email
	//API for SQL IN function 
	//Validate API ---Mobile & email
	//Display error msg in dilog
	public void saveMigration(FileUploadEvent event){
		
		
		
	}
	

}
