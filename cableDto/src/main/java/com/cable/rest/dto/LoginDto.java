package com.cable.rest.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginDto  implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "Login ID can not be null ... ")
	@Getter	@Setter	
	private String loginId;
	
	@NotNull(message = "Password can not be null ... ")
	@Getter	@Setter	
	private String password;
}
