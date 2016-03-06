package com.cable.rest.model;


import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "auditlog")
public class AuditLog implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "AUDIT_LOG_ID", unique = true, nullable = false)
	@Getter	@Setter	
	private Long auditLogId;
	
	@Column(name = "ENTITY_ID", nullable = false)
	@Getter	@Setter	
	private long entityId;
	
	@Column(name = "ENTITY_NAME", nullable = false)
	@Getter	@Setter	
	private String entityName;
	
	@Column(name = "ACTION", nullable = false, length = 100)
	@Getter	@Setter	
	private String action;
	
	@Column(name = "DETAIL", nullable = false, length = 65535)
	@Getter	@Setter	
	private String detail;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="created_date", nullable=false)
	@Getter	@Setter	
	private Date createdDate;
	
	@Column(name="created_by", nullable=false)
	@Getter	@Setter	
	private Long createdBy;
	
	@Column(name="time_stamp", nullable=true,columnDefinition="timestamp default current_timestamp on update current_timestamp")
	@Getter	@Setter	
	private Date timeStamp ;
	
	
	public AuditLog() {
	}

	public AuditLog(String action, String detail, Date createdDate,
			long entityId, String entityName) {
		this.action = action;
		this.detail = detail;
		this.createdDate = createdDate;
		this.entityId = entityId;
		this.entityName = entityName;
	}
	
	

	

}
