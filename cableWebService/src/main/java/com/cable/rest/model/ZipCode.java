package com.cable.rest.model;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cable.rest.constants.Status;
import com.cable.rest.interceptor.IAuditLog;
import com.cable.rest.interceptor.LogListener;

@Entity
@Table(name="zip_code")
@Cacheable(true)
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@EntityListeners(LogListener.class)
public class ZipCode extends Audit implements Serializable , IAuditLog {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter	@Setter	
	private Long zipCode;
	
	@Column(nullable=false)
	@Getter	@Setter	
	private String locationName;
	
	@Column(nullable=false, unique=true )
	@Getter	@Setter	
	private String pinCode;
	
	@Column(nullable=false)
	@Getter	@Setter	
	private String district;
	
	@Column(nullable=false)
	@Getter	@Setter	
	private String state;
	
	@Column(nullable=false)
	@Getter	@Setter	
	private String country;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	@Getter	@Setter	
	private Status status;

	@Transient
	public Long getEntityId() {
		return this.zipCode.longValue();
	}

	@Transient
	public String getLogDeatil() {
		StringBuilder sb = new StringBuilder();
		sb.append(" Id : ").append(zipCode)
		.append(" pinCcodee : ").append(pinCode);
 
		return sb.toString();
	}

	
	
	

}
