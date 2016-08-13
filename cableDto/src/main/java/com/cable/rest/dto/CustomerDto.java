package com.cable.rest.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.cable.rest.constants.Gender;
import com.cable.rest.constants.Status;

public class CustomerDto {
	
	@Getter	@Setter	
	private Long customerId;
	
	@Getter	@Setter	
	private String firstName;
	
	@Getter	@Setter	
	private String lastName;
	
	@Getter	@Setter	
	private String emailId;
	
	@Getter	@Setter	
	private int emailIdVerified;
	
	@Getter	@Setter	
	private String password;
	
	@Getter	@Setter	
	private Date dob;
	
	@Getter	@Setter	
	private String mobile;
	
	@Getter	@Setter	
	private int mobileVerified;
	
	@Getter	@Setter	
	private Gender gender;
	
	@Getter	@Setter	
	private Status status;

}
