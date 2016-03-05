package com.cable.rest.response;

import java.io.Serializable;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseResource implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Getter
    private int code; 
	@Getter
    private String message;
	
	public ResponseResource(final ErrorCodeDescription error){
		this.code =  error.getErrorCode() ;
		this.message= error.getErrorDescription();
	}

}
