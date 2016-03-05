package com.cable.rest.exception;

import com.cable.rest.response.ErrorResource;


public class BadRequestException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private ErrorResource errors;
	
	public BadRequestException(String message, ErrorResource errors) {
        super(message);
        this.errors = errors;
    }

    public ErrorResource getErrors() { 
    	return errors; 
    }
}
