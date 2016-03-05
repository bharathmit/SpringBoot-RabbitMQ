package com.cable.rest.exception;

import java.util.ArrayList;
import java.util.List;

import com.cable.rest.response.ErrorCodeDescription;
import com.cable.rest.response.FieldErrorResource;

public class RestException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	List<FieldErrorResource> fieldErrorResources = new ArrayList<FieldErrorResource>();
	
	public RestException(final ErrorCodeDescription error) {
		  
		FieldErrorResource fieldErrorResource = new FieldErrorResource();
		fieldErrorResource.setCode("" + error.getErrorCode());
		fieldErrorResource.setMessage(error.getErrorDescription());

		fieldErrorResources.add(fieldErrorResource);
	}

	
	
	/**
	 * Gets the fieldErrorResources
	 * 
	 * @return FieldErrorResource List
	 */
	public List<FieldErrorResource> getFieldErrorResources() {
		return fieldErrorResources;
	}
  
	

}
