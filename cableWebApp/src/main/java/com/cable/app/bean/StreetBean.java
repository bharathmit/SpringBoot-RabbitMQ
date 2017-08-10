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
import com.cable.rest.dto.AreaDto;
import com.cable.rest.dto.OrganizationDto;
import com.cable.rest.dto.ProjectDto;
import com.cable.rest.dto.StreetDto;
import com.cable.rest.response.ErrorResource;
import com.cable.rest.search.MasterSearch;
import com.cable.rest.search.ProjectSearch;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@ManagedBean
@SessionScoped
@Log4j
public class StreetBean {
	
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
	MasterSearch masterSearch=new MasterSearch();
	
	@Getter @Setter
	List<StreetDto> streetList=new ArrayList<StreetDto>();
	
	@Getter @Setter
	StreetDto streetSelected=new StreetDto();
	
	@Getter @Setter
	List<ProjectDto> projectList=new ArrayList<ProjectDto>();
	
	@Getter @Setter
	List<AreaDto> areaList=new ArrayList<AreaDto>();
	
	@Setter @Getter
	String action;
	
	@Getter @Setter
	int numberOfRecords;
	
	
	public String saveStreet(){
		try{
			
			
			/*//need to change to JPA
			ProjectDto project=new ProjectDto();
			project.setProjectId(1l);
			OrganizationDto org=new OrganizationDto();
			org.setOrgId(1l);
			project.setOrganization(org);
			
			AreaDto area=new AreaDto();
			area.setProject(project);
			streetSelected.setArea(area);*/
			
			HttpEntity<StreetDto> requestEntity = new HttpEntity<StreetDto>(streetSelected, LoginBean.header);

			ResponseEntity<String> response = restTemplate.exchange(restClient.createUrl("master/savestreet"),HttpMethod.POST,requestEntity,String.class);

			String responseBody = response.getBody();

			if (RestUtil.isError(response.getStatusCode())) {
				ErrorResource error = objectMapper.readValue(responseBody, ErrorResource.class);

				FacesUtil.error(error.getFieldErrors().get(0).getMessage());
				return null;

			} else {
				StreetDto result = objectMapper.readValue(responseBody, StreetDto.class);
				FacesUtil.info("Projecte has been saved.");

			}
		}
		catch(Exception e){
			log.error("saveProject", e);

		}
		showStreetList();
		return "/pages/master/streetlist.xhtml";
	}
	
	public String showStreetList(){

		
		streetSelected = new StreetDto();
		numberOfRecords = 0;
		
		try{
           
			HttpEntity<MasterSearch> requestEntity = new HttpEntity<MasterSearch>(masterSearch, LoginBean.header);

			ResponseEntity<String> response = restTemplate.exchange(restClient.createUrl("master/streetlist"),HttpMethod.POST,requestEntity,String.class);

			String responseBody = response.getBody();

			if (RestUtil.isError(response.getStatusCode())) {
				ErrorResource error = objectMapper.readValue(responseBody, ErrorResource.class);

				FacesUtil.warn(error.getFieldErrors().get(0).getMessage());
				return "";

			} else {
				streetList = objectMapper.readValue(responseBody, new TypeReference<List<StreetDto>>(){});
				if(streetList != null){
					numberOfRecords = streetList.size();
				}

			}

		}
		catch(Exception e){
			FacesUtil.warn("Connection refused by remote server, please try again later");
			log.error("showStreetList", e);
		}
		masterSearch = new MasterSearch();
		return "/pages/master/streetlist.xhtml";
		
	}
	
	public String showStreetForm(){
		log.info("show Street Form Called Action is "+action);
		
		if(action.equals("Add")){
			getProjectsList();
			streetSelected = new StreetDto();
		}else if(action.equals("Edit")){
			if(streetSelected == null){
				FacesUtil.warn("Please select any one Street");
				return null;
			}
		}else if(action.equals("View")){
			if(streetSelected == null){
				FacesUtil.warn("Please select any one Street");
				return null;
			}
		}
		return "/pages/master/streetform.xhtml";
	}
	
	public void clearSearch(){
		masterSearch=new MasterSearch();
	}
	
	public void clearForm(){
		streetSelected.setStreetName("");
		streetSelected.setArea(new AreaDto());
		  
	}
	
	public String cancelStreetForm(){
		streetSelected = new StreetDto();
		return "/pages/master/streetlist.xhtml";
		
	}
	
	
	public void getProjectsList(){

		ProjectSearch projectSearch=new ProjectSearch();
		try{
           
			HttpEntity<ProjectSearch> requestEntity = new HttpEntity<ProjectSearch>(projectSearch, LoginBean.header);

			ResponseEntity<String> response = restTemplate.exchange(restClient.createUrl("project/projectlist"),HttpMethod.POST,requestEntity,String.class);

			String responseBody = response.getBody();

			if (RestUtil.isError(response.getStatusCode())) {
				ErrorResource error = objectMapper.readValue(responseBody, ErrorResource.class);

				FacesUtil.warn(error.getFieldErrors().get(0).getMessage());
				return ;

			} else {
				projectList = objectMapper.readValue(responseBody, new TypeReference<List<ProjectDto>>(){});
			}

		}
		catch(Exception e){
			FacesUtil.warn("Connection refused by remote server, please try again later");
			log.error("showProjectList", e);
		}
	}
	
	
	public void updateArea(){
		masterSearch.setProjectId(streetSelected.getArea().getProject().getProjectId());
		getAreaLists();
	}
	
	public void getAreaLists(){

		try{
           
			HttpEntity<MasterSearch> requestEntity = new HttpEntity<MasterSearch>(masterSearch, LoginBean.header);

			ResponseEntity<String> response = restTemplate.exchange(restClient.createUrl("master/arealist"),HttpMethod.POST,requestEntity,String.class);

			String responseBody = response.getBody();

			if (RestUtil.isError(response.getStatusCode())) {
				ErrorResource error = objectMapper.readValue(responseBody, ErrorResource.class);
				FacesUtil.warn(error.getFieldErrors().get(0).getMessage());
			} else {
				areaList = objectMapper.readValue(responseBody, new TypeReference<List<AreaDto>>(){});
			}

		}
		catch(Exception e){
			log.error("showAreaList", e);
		}
	}
	
	
}
