package com.cable.app.bean;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.cable.app.exception.FacesUtil;
import com.cable.app.exception.RestUtil;
import com.cable.app.utils.RestClient;
import com.cable.rest.dto.AreaDto;
import com.cable.rest.dto.ConnectionAccountDto;
import com.cable.rest.dto.ProjectDto;
import com.cable.rest.dto.StreetDto;
import com.cable.rest.response.ErrorResource;
import com.cable.rest.search.MasterSearch;
import com.cable.rest.search.ProjectSearch;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@ManagedBean
@SessionScoped
@Log4j
public class MigrationBean {
	
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
	
	@Getter @Setter
	List<ProjectDto> projectList=new ArrayList<ProjectDto>();
	
	@Getter @Setter
	MasterSearch masterSearch=new MasterSearch();
	
	@Getter @Setter
	StreetDto streetSelected=new StreetDto();
	
	@Getter @Setter
	List<AreaDto> areaList=new ArrayList<AreaDto>();
	
	
	@Getter
	private StreamedContent downloadFile;
	
	@Getter @Setter
	List<ConnectionAccountDto> accountList=new ArrayList<ConnectionAccountDto>();
	
	
	public MigrationBean(){
		InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/template/tvconnection.xls");
        downloadFile = new DefaultStreamedContent(stream, "application/vnd.ms-excel", "template.xls");
	}
	
	public String showMigration(){
		getProjectsList();
		return "/pages/master/migrationform.xhtml";
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
			log.error("getProjectsList", e);
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
			FacesUtil.warn("Connection refused by remote server, please try again later");
			log.error("getAreaLists", e);
		}
	}
	
	
	
	
	//Display error msg in dilog
	public void uploadExel(FileUploadEvent event){
		List<ConnectionAccountDto> connectionList=new ArrayList<ConnectionAccountDto>();
		
		
		
		
		ConnectionAccountDto connection=new ConnectionAccountDto();
		
		
		System.out.println(event.getFile().getFileName());
		
	}
	

}
