package com.cable.rest.response;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldErrorResource {
	@Getter	@Setter	
	private String code;
	@Getter	@Setter	
	private String message;
	
}
