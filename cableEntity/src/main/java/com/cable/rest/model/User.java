package com.cable.rest.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cable.rest.constants.Gender;
import com.cable.rest.constants.Status;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="user")
public class User extends Audit implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable=false)
	@Getter	@Setter	
	private Long userId;
	
	@Column(nullable=false)
	@Getter	@Setter	
	private String firstName;
	
	@Column(nullable=false)
	@Getter	@Setter	
	private String lastName;
	
	@Column(unique=true,nullable=false)
	@Getter	@Setter	
	private String loginId;
	
	@Column(nullable=false)
	@Getter	@Setter	
	private String password;
	
	@Column(unique=true,nullable=false)
	@Getter	@Setter	
	private String mobile;
	
	@Column(unique=true,nullable=false)
	@Getter	@Setter	
	private String emailId;
	
	@Temporal( TemporalType.DATE)
	@Column(nullable=false)
	@Getter	@Setter	
	private Date dob;
	
	@Temporal( TemporalType.DATE)
	@Column(nullable=false)
	@Getter	@Setter	
	private Date doj;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	@Getter	@Setter	
	private Gender gender;
	
	@Column(nullable=false,length = 65535,columnDefinition="Text")
	@Getter	@Setter	
	private String address;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	@Getter	@Setter	
	private Status status;
	
	@ManyToOne
	@JoinColumn(name="org_Id", nullable=false)
	@Getter	@Setter	
	private Organization organization;
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy="user")
	@Getter	@Setter	
	private List<UserRole> userRoles;
	
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(nullable=false)
	@Getter	@Setter	
	private Date lastLoginDate;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(nullable=false)
	@Getter	@Setter	
	private Date passwordChangeDate;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(nullable=false)
	@Getter	@Setter	
	private Date lockDate;
	
	@Column(nullable=false)
	@Getter	@Setter	
	private byte[] photo;
	
	

}
