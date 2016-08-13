package com.cable.rest.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import com.cable.rest.constants.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrganizationDto implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Getter	@Setter	
	private Long orgId;
	
	@Getter	@Setter	
	private String orgName;
	
	@Getter	@Setter	
	private String orgToken;
	
	@Getter	@Setter	
	private byte[] logo;
	
	@Getter	@Setter	
	private String address;
	
	@Getter @Setter
	private String email;
	
	@Getter @Setter
	private String mobile;
	
	@Getter	@Setter	
	private Status status;
	
	
}
