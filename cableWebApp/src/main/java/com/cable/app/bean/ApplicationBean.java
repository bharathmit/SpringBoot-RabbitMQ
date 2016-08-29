package com.cable.app.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import com.cable.rest.constants.Gender;
import com.cable.rest.constants.Status;
import com.cable.rest.dto.UserDto;

@ManagedBean
@ApplicationScoped
@Log4j
public class ApplicationBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Getter @Setter
	List<Status> statusList=new ArrayList<Status>();
	
	@Getter @Setter
	List<Gender> genderList=new ArrayList<Gender>();
	
	@Getter @Setter
	UserDto userContext=new UserDto();
	
	@PostConstruct
	public void lookUp(){
		statusLookUp();
		genderLookUp();
	}
	
	public void statusLookUp(){
		statusList.add(Status.Active);
		statusList.add(Status.InActive);
	}
	
	public void genderLookUp(){
		genderList.add(Gender.Male);
		genderList.add(Gender.Female);
	}

}
