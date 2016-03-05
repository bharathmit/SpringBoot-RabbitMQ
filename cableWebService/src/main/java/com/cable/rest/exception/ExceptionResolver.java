package com.cable.rest.exception;

import java.util.ArrayList;
import java.util.List;

import com.cable.rest.response.ErrorResource;
import com.cable.rest.response.FieldErrorResource;


public class ExceptionResolver {

	public static ErrorResource businessRule(Throwable exception) {
		
		RestException ire = (RestException) exception;
  
        ErrorResource error = new ErrorResource("BusinessRule", "Exception");
        error.setFieldErrors(ire.getFieldErrorResources());
        
		return error;
	}
	
	public static ErrorResource invalidDataAccess(Exception exception) {
		List<FieldErrorResource> fieldErrorResources = new ArrayList<FieldErrorResource>();
        ErrorResource error = new ErrorResource("InvalidDataAccess", "Exception");
        FieldErrorResource display=new FieldErrorResource();
        
        display.setCode(""+exception.getCause().getClass());
        display.setMessage(""+exception.getCause().getMessage());
        fieldErrorResources.add(display);
        
        error.setFieldErrors(fieldErrorResources);
		
        return error;
	}
	
	public static ErrorResource nullPointer() {
		List<FieldErrorResource> fieldErrorResources = new ArrayList<FieldErrorResource>();
        ErrorResource error = new ErrorResource("NullPointer", "Exception");
        FieldErrorResource display=new FieldErrorResource();
        
        display.setCode("Method Retun Type");
        display.setMessage("Null Pointer Exception");
        fieldErrorResources.add(display);
        
        error.setFieldErrors(fieldErrorResources);
		
        return error;
	}
	
	
	
}
