package com.cable.rest.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cable.rest.constants.ReportParamType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="report_parameter")
public class ReportParameter extends Audit implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable=false)
	@Getter	@Setter	
	private Long reportParamId;
	
	@ManyToOne
	@JoinColumn(name="report_id", nullable=false)
	@Getter	@Setter	
	private Report report;
	
	@Column(nullable=false)
	@Getter	@Setter	
	private String reportParamName;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	@Getter	@Setter	
	private ReportParamType reportParamType;
	
	
	
	

}
