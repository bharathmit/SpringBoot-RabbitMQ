package com.cable.rest.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import com.cable.rest.constants.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConnectionAccountDto implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Getter	@Setter	
	private Long accountId;
	
	@Getter	@Setter	
	private String accountToken;
	
	@Getter	@Setter	
	private String name;
	
	@Getter	@Setter	
	private String mobile;
	
	@Getter	@Setter	
	private String emailId;
	
	@Getter	@Setter	
	private String address;
	
	@Getter	@Setter	
	private String notes;
	
	@Getter	@Setter	
	private StreetDto street=new StreetDto();
	
	@Getter	@Setter	
	private AreaDto area=new AreaDto();
	
	@Getter	@Setter	
	private ProjectDto project=new ProjectDto();
	
	@Getter	@Setter	
	private double rentAmount;
	
	@Getter	@Setter	
	private double advancePaid;
	
	@Getter	@Setter	
	private int payGenMonth;
	
	@Getter	@Setter	
	private Status status;

}
