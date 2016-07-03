package com.cable.rest.search;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSearch implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Getter	@Setter	
	Long userId;
	
	@Getter	@Setter	
	Long roleId;
	
	
	@Getter	@Setter	
	String firstName;
	
	@Getter	@Setter	
	String lastName;
	
	
	@Getter	@Setter	
	String email;
	
	@Getter	@Setter	
	String mobile;
	
	
	
	
}
