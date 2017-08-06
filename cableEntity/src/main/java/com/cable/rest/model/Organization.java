package com.cable.rest.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cable.rest.constants.Status;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="organization")
public class Organization extends Audit implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter	@Setter	
	private Long orgId;
	
	@Column(nullable=false)
	@Getter	@Setter	
	private String orgName;
	
	@Column(unique=true,nullable=false)
	@Getter	@Setter	
	private String orgToken;
	
	@Column(nullable=true,name="logo",columnDefinition="LONGBLOB")
	@Getter	@Setter	
	private byte[] logo;
	
	@Column(nullable=false,length = 65535,columnDefinition="Text")
	@Getter	@Setter	
	private String address;
	
	@Column(nullable=false)
	@Getter	@Setter	
	private String email;
	
	@Column(nullable=false)
	@Getter	@Setter	
	private String mobile;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	@Getter	@Setter	
	private Status status;
	
	
	
	

}
