package com.cable.app.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.cable.app.exception.FacesUtil;
import com.cable.app.exception.RestUtil;
import com.cable.app.utils.RestClient;
import com.cable.rest.constants.Status;
import com.cable.rest.dto.OrganizationDto;
import com.cable.rest.dto.ProjectDto;
import com.cable.rest.dto.ZipCodeDto;
import com.cable.rest.response.ErrorResource;
import com.cable.rest.search.MasterSearch;
import com.cable.rest.search.ProjectSearch;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@ManagedBean
@SessionScoped
@Log4j
public class ProjectBean {
	
	@ManagedProperty(value="#{restTemplate}")
	@Getter @Setter
	RestTemplate restTemplate;

	@ManagedProperty(value="#{restClient}")
	@Getter @Setter
	RestClient restClient;

	@ManagedProperty(value="#{objectMapper}")
	@Getter @Setter
	ObjectMapper objectMapper;
	
	@Getter @Setter
	ProjectSearch projectSearch=new ProjectSearch();
	
	@Getter @Setter
	List<ProjectDto> projectList=new ArrayList<ProjectDto>();
	
	@Getter @Setter
	ProjectDto projectSelected=new ProjectDto();
	
	@Setter @Getter
	String action;
	
	@Getter @Setter
	int numberOfRecords;
	
	@Getter @Setter
	List<ZipCodeDto> zipcodeList=new ArrayList<ZipCodeDto>();
	
	
	public String saveProject(){
		try{
			
			//need to change UserContext
			OrganizationDto org=new OrganizationDto();
			org.setOrgId(1l);
			projectSelected.setOrganization(org);
			
			HttpEntity<ProjectDto> requestEntity = new HttpEntity<ProjectDto>(projectSelected, LoginBean.header);

			ResponseEntity<String> response = restTemplate.exchange(restClient.createUrl("project/saveproject"),HttpMethod.POST,requestEntity,String.class);

			String responseBody = response.getBody();

			if (RestUtil.isError(response.getStatusCode())) {
				ErrorResource error = objectMapper.readValue(responseBody, ErrorResource.class);

				FacesUtil.error(error.getFieldErrors().get(0).getMessage());
				return null;

			} else {
				ProjectDto result = objectMapper.readValue(responseBody, ProjectDto.class);
				FacesUtil.info("Projecte has been saved.");

			}
		}
		catch(Exception e){
			log.error("saveProject", e);

		}
		showProjectList();
		return "/pages/master/projectlist.xhtml";
	}

	
	public String showProjectList(){

		
		projectSelected = new ProjectDto();
		numberOfRecords = 0;
		
		try{
           
			HttpEntity<ProjectSearch> requestEntity = new HttpEntity<ProjectSearch>(projectSearch, LoginBean.header);

			ResponseEntity<String> response = restTemplate.exchange(restClient.createUrl("project/projectlist"),HttpMethod.POST,requestEntity,String.class);

			String responseBody = response.getBody();

			if (RestUtil.isError(response.getStatusCode())) {
				ErrorResource error = objectMapper.readValue(responseBody, ErrorResource.class);

				FacesUtil.warn(error.getFieldErrors().get(0).getMessage());
				return "";

			} else {
				projectList = objectMapper.readValue(responseBody, new TypeReference<List<ProjectDto>>(){});
				if(projectList != null){
					numberOfRecords = projectList.size();
				}

			}

		}
		catch(Exception e){
			log.error("showProjectList", e);
		}
		projectSearch = new ProjectSearch();
		return "/pages/master/projectlist.xhtml";
		
	}
	
	
	
	public String showProjectForm(){
		log.info("show Project Form Called Action is "+action);
		log.info("show Project Form Value is "+projectSelected);
		getZipCodeValue();
		if(action.equals("Add")){
			projectSelected = new ProjectDto();
		}else if(action.equals("Edit")){
			if(projectSelected == null){
				FacesUtil.warn("Please select any one Project");
				return null;
			}
		}else if(action.equals("View")){
			if(projectSelected == null){
				FacesUtil.warn("Please select any one Project");
				return null;
			}
		}
		return "/pages/master/projectform.xhtml";
	}
	
	public void clearSearch(){
		projectSearch = new ProjectSearch();
	}
	
	public void clearForm(){
		projectSelected.setProjectCityVillage("");
		projectSelected.setOrganization(new OrganizationDto());
		projectSelected.setAddress("");
		projectSelected.setZipCode(new ZipCodeDto());		
		projectSelected.setEmail("");
		projectSelected.setMobile("");		
		projectSelected.setAdvanceAmount(0.0);
		projectSelected.setOnlinePaymentFlag(Status.InActive);
		projectSelected.setPaymentGenerateDate(0);
		projectSelected.setPaymentDueDate(0);		  
	}
	
	public String cancelProjectForm(){
		projectSelected = new ProjectDto();
		return "/pages/master/projectlist.xhtml";
		
	}
	
	
	public void getZipCodeValue(){
		
		try{
           
			HttpEntity<MasterSearch> requestEntity = new HttpEntity<MasterSearch>(new MasterSearch(), LoginBean.header);

			ResponseEntity<String> response = restTemplate.exchange(restClient.createUrl("master/zipcodelist"),HttpMethod.POST,requestEntity,String.class);

			String responseBody = response.getBody();

			if (RestUtil.isError(response.getStatusCode())) {
				ErrorResource error = objectMapper.readValue(responseBody, ErrorResource.class);
				FacesUtil.warn(error.getFieldErrors().get(0).getMessage());
			} else {				
				zipcodeList = objectMapper.readValue(responseBody, new TypeReference<List<ZipCodeDto>>(){});
			}

		}
		catch(Exception e){
			log.error("getZipCodeValue", e);
		}
	}
	
}
