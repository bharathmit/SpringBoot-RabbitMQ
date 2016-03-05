package com.cable.rest.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.cable.rest.constants.Gender;
import com.cable.rest.constants.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;


@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto  implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	
	@Getter	@Setter	
	private Long userId;
	
	@NotNull(message = "First Name can not be null ... ")
	@Getter	@Setter	
	private String firstName;
	
	@Getter	@Setter	
	private String lastName;
	
	@NotNull(message = "Mobile Number can not be null ... ")
	@Getter	@Setter	
	private String mobile;
	
	@NotNull(message = "Login ID can not be null ... ")
	@Getter	@Setter	
	private String loginId;
	
	@NotNull(message = "Password can not be null ... ")
	@Getter	@Setter	
	private String password;
	
	@Getter	@Setter	
	private String emailId;
	
	@Getter	@Setter	
	private Date dob;
	
	@Getter	@Setter	
	private Gender gender;
	
	@Getter	@Setter	
	private String address;
	
	@Getter	@Setter	
	private Status status;
	
	@Getter	@Setter	
	private List<UserRoleDto> userRoles;
	
}
