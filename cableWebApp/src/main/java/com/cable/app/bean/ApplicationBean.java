package com.cable.app.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import com.cable.rest.constants.Status;

@ManagedBean
@ApplicationScoped
@Log4j
public class ApplicationBean {
	
	@Getter @Setter
	List<Status> statusList=new ArrayList<Status>();
	
	
	@PostConstruct
	public void lookUp(){
		statusList.add(Status.Active);
		statusList.add(Status.InActive);
	}

}
