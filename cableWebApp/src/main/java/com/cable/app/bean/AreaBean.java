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
import com.cable.rest.dto.ZipCodeDto;
import com.cable.rest.response.ErrorResource;
import com.cable.rest.search.MasterSearch;
import com.cable.rest.search.ProjectSearch;
import com.cable.rest.search.UserSearch;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@ManagedBean
@SessionScoped
@Log4j
public class AreaBean {
	
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
	List<AreaDto> areaList=new ArrayList<AreaDto>();
	
	@Getter @Setter
	AreaDto areaSelected=new AreaDto();
	
	@Getter @Setter
	List<ProjectDto> projectList=new ArrayList<ProjectDto>();
	
	@Setter @Getter
	String action;
	
	@Getter @Setter
	int numberOfRecords;
	
	
	public String saveArea(){
		try{

			//need to change the JPA Casscade Type
			
			ProjectDto project=new ProjectDto();
			project.setProjectId(areaSelected.getProject().getProjectId());
			OrganizationDto org=new OrganizationDto();
			org.setOrgId(1l);
			project.setOrganization(org);
			
			areaSelected.setProject(project);
			HttpEntity<AreaDto> requestEntity = new HttpEntity<AreaDto>(areaSelected, LoginBean.header);

			ResponseEntity<String> response = restTemplate.exchange(restClient.createUrl("master/savearea"),HttpMethod.POST,requestEntity,String.class);

			String responseBody = response.getBody();

			if (RestUtil.isError(response.getStatusCode())) {
				ErrorResource error = objectMapper.readValue(responseBody, ErrorResource.class);

				FacesUtil.error(error.getFieldErrors().get(0).getMessage());
				return null;

			} else {
				AreaDto result = objectMapper.readValue(responseBody, AreaDto.class);
				FacesUtil.info("Area has been saved.");

			}
		}
		catch(Exception e){
			log.error("saveProject", e);

		}
		showAreaList();
		return "/pages/master/arealist.xhtml";
	}
	
	public String showAreaList(){

		
		areaSelected = new AreaDto();
		numberOfRecords = 0;
		
		try{
           
			HttpEntity<MasterSearch> requestEntity = new HttpEntity<MasterSearch>(masterSearch, LoginBean.header);

			ResponseEntity<String> response = restTemplate.exchange(restClient.createUrl("master/arealist"),HttpMethod.POST,requestEntity,String.class);

			String responseBody = response.getBody();

			if (RestUtil.isError(response.getStatusCode())) {
				ErrorResource error = objectMapper.readValue(responseBody, ErrorResource.class);

				FacesUtil.warn(error.getFieldErrors().get(0).getMessage());
				return "";

			} else {
				areaList = objectMapper.readValue(responseBody, new TypeReference<List<AreaDto>>(){});
				if(areaList != null){
					numberOfRecords = areaList.size();
				}

			}

		}
		catch(Exception e){
			log.error("showAreaList", e);
		}
		masterSearch = new MasterSearch();
		return "/pages/master/arealist.xhtml";
		
	}
	
	public String showAreaForm(){
		log.info("show Area Form Called Action is "+action);
		
		if(action.equals("Add")){
			areaSelected = new AreaDto();
			getProjectsList();
		}else if(action.equals("Edit")){
			if(areaSelected == null){
				FacesUtil.warn("Please select any one Area");
				return null;
			}
		}else if(action.equals("View")){
			if(areaSelected == null){
				FacesUtil.warn("Please select any one Area");
				return null;
			}
		}
		return "/pages/master/areaform.xhtml";
	}
	
	public void clearSearch(){
		masterSearch=new MasterSearch();
	}
	
	public void clearForm(){
		areaSelected.setAreaName("");
		areaSelected.setProject(new ProjectDto());
		  
	}
	
	public String cancelAreaForm(){
		areaSelected = new AreaDto();
		return "/pages/master/arealist.xhtml";
		
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
			log.error("showProjectList", e);
		}
	}
	
	
	
}
