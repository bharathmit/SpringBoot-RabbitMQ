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
import com.cable.rest.dto.OrganizationDto;
import com.cable.rest.dto.ZipCodeDto;
import com.cable.rest.response.ErrorResource;
import com.cable.rest.search.MasterSearch;
import com.cable.rest.search.ProjectSearch;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@ManagedBean
@SessionScoped
@Log4j
public class OrganizationBean {

	@ManagedProperty(value = "#{restTemplate}")
	@Getter
	@Setter
	RestTemplate restTemplate;

	@ManagedProperty(value = "#{restClient}")
	@Getter
	@Setter
	RestClient restClient;

	@ManagedProperty(value = "#{objectMapper}")
	@Getter
	@Setter
	ObjectMapper objectMapper;

	
	
	@Getter
	@Setter
	List<OrganizationDto> organizionList = new ArrayList<OrganizationDto>();

	@Getter @Setter
	MasterSearch masterSearch=new MasterSearch();
	
	@Getter @Setter
	OrganizationDto organizionSelected=new OrganizationDto();
	
	@Setter @Getter
	String action;

	@Getter @Setter
	Integer numberOfRecords;
	
	public void saveOrganization() {
		try {

			HttpEntity<OrganizationDto> requestEntity = new HttpEntity<OrganizationDto>(
					organizionSelected, LoginBean.header);

			ResponseEntity<String> response = restTemplate.exchange(
					restClient.createUrl("project/saveorganization"),
					HttpMethod.POST, requestEntity, String.class);

			String responseBody = response.getBody();

			if (RestUtil.isError(response.getStatusCode())) {
				ErrorResource error = objectMapper.readValue(responseBody,
						ErrorResource.class);

				FacesUtil.error(error.getFieldErrors().get(0).getMessage());

			} else {
				OrganizationDto result = objectMapper.readValue(responseBody,
						OrganizationDto.class);

			}

		} catch (Exception e) {
			log.error("saveOrganization", e);
		}
	}
	
	
public String getOrganizationValue(){
		
	organizionSelected = new OrganizationDto();
		numberOfRecords = 0;
		
		try{
           
			HttpEntity<MasterSearch> requestEntity = new HttpEntity<MasterSearch>(masterSearch, LoginBean.header);

			ResponseEntity<String> response = restTemplate.exchange(restClient.createUrl("project/organizationlist"),HttpMethod.POST,requestEntity,String.class);

			String responseBody = response.getBody();

			if (RestUtil.isError(response.getStatusCode())) {
				ErrorResource error = objectMapper.readValue(responseBody, ErrorResource.class);

				FacesUtil.warn(error.getFieldErrors().get(0).getMessage());
				return "";

			} else {				
				organizionList = objectMapper.readValue(responseBody, new TypeReference<List<OrganizationDto>>(){});
				if(organizionList != null){
					numberOfRecords = organizionList.size();
				}

			}

		}
		catch(Exception e){
			log.error("getOrganizationValue", e);
		}
		masterSearch = new MasterSearch();
		return "/pages/master/organizationlist.xhtml";
	}
	
	public String showOrganizationForm() {
		log.info("show ZIP Code Called Action is "+action);
		log.info("show ZIP Code Called Value is "+organizionSelected);
		if(action.equals("Add")){
			organizionSelected = new OrganizationDto();
		}else if(action.equals("Edit")){
			if(organizionSelected == null){
				FacesUtil.warn("Please select any one ZIP Code");
				return null;
			}
		}else if(action.equals("View")){
			if(organizionSelected == null){
				FacesUtil.warn("Please select any one ZIP Code");
				return null;
			}
		}
		return "/pages/master/organizationform.xhtml";
	}
	
	
	public void clearSearch(){
		masterSearch = new MasterSearch();	
	}
	
	
	public void clearForm(){
		organizionSelected.setOrgName("");
		organizionSelected.setOrgToken("");
		organizionSelected.setAddress("");
	}
	
	public String cancelOrganizationForm(){
		organizionSelected = new OrganizationDto();
		return "/pages/master/organizationlist.xhtml";
		
	}
	
	
	public String DisplayOrganizationForm() {
		log.info("Display Organization Form ");		
		
		ProjectSearch projectSearch = new ProjectSearch();
		
		try{
	           
			//need to change UserContext Value
			projectSearch.setOrgId(1l);
			
			HttpEntity<ProjectSearch> requestEntity = new HttpEntity<ProjectSearch>(projectSearch, LoginBean.header);

			ResponseEntity<String> response = restTemplate.exchange(restClient.createUrl("project/organizationlist"),HttpMethod.POST,requestEntity,String.class);

			String responseBody = response.getBody();

			if (RestUtil.isError(response.getStatusCode())) {
				ErrorResource error = objectMapper.readValue(responseBody, ErrorResource.class);

				FacesUtil.warn(error.getFieldErrors().get(0).getMessage());
				return "";

			} else {				
				organizionList = objectMapper.readValue(responseBody, new TypeReference<List<OrganizationDto>>(){});
				if(organizionList != null){
					organizionSelected=organizionList.get(0);
				}

			}

		}
		catch(Exception e){
			log.error("getOrganizationValue", e);
		}
		
		return "/pages/master/organizationform.xhtml";
	}
	

}
