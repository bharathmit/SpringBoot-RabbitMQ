package com.cable.rest.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponseDto implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Getter @Setter
    boolean authenticationStatus; 
    
    @Getter @Setter
    String sessionid; 
    
    @Getter @Setter
    UserDto user; 

}
