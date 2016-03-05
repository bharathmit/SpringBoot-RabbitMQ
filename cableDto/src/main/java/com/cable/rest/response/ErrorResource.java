package com.cable.rest.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResource {
	
	@Getter	@Setter	
	private String code;
	@Getter	@Setter	
    private String message;
	@Getter	@Setter	
    private List<FieldErrorResource> fieldErrors;

    public ErrorResource() { }

    public ErrorResource(String code, String message) {
        this.code = code;
        this.message = message;
    }

    
}
