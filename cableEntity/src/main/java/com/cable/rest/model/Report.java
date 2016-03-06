package com.cable.rest.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cable.rest.constants.Status;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="report")
public class Report implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable=false)
	@Getter	@Setter	
	private Long reportId;
	
	@Column(nullable=false)
	@Getter	@Setter	
	private String reportName;
	
	@Column(nullable=false)
	@Getter	@Setter	
	private String reportFileName;
	
	@Column(nullable=false)
	@Getter	@Setter	
	private String reportType;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	@Getter	@Setter	
	private Status status;
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy="report")
	@Getter	@Setter	
	private List<ReportParameter> reportParameters;

}
