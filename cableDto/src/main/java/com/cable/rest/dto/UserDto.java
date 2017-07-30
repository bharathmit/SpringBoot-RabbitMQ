package com.cable.rest.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import com.cable.rest.constants.Gender;
import com.cable.rest.constants.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto extends BaseDto implements Serializable  {
	
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
	private Date doj;
	
	@Getter	@Setter	
	private Gender gender;
	
	@Getter	@Setter	
	private String address;
	
	@Getter	@Setter	
	private Status status;
	
	@Getter	@Setter	
	private OrganizationDto organization;
	
	@Getter	@Setter	
	private List<UserRoleDto> userRoles=new ArrayList<UserRoleDto>();
	
	@Getter	@Setter	
	private Date lastLoginDate;
	
	@Getter	@Setter	
	private Date passwordChangeDate;
	
	@Getter	@Setter	
	private Date lockDate;
	
	@Getter	@Setter	
	private byte[] photo;
	
	@Getter	@Setter	
	private String newPassword;
	
	@Getter	@Setter	
	private boolean passwordFlag;
	
}
