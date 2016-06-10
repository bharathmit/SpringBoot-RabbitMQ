package com.cable.app.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.cable.app.exception.FacesUtil;
import com.cable.app.exception.RestUtil;
import com.cable.app.utils.RestClient;
import com.cable.rest.constants.Gender;
import com.cable.rest.constants.Status;
import com.cable.rest.dto.ProjectDto;
import com.cable.rest.dto.RoleDto;
import com.cable.rest.dto.UserDto;
import com.cable.rest.dto.UserRoleDto;
import com.cable.rest.dto.ZipCodeDto;
import com.cable.rest.response.ErrorResource;
import com.cable.rest.search.ProjectSearch;
import com.cable.rest.search.UserSearch;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@ManagedBean
@SessionScoped
@Log4j
public class UserBean {
	
	@ManagedProperty(value="#{restTemplate}")
	@Getter @Setter
	RestTemplate restTemplate;

	@ManagedProperty(value="#{restClient}")
	@Getter @Setter
	RestClient restClient;

	@ManagedProperty(value="#{objectMapper}")
	@Getter @Setter
	ObjectMapper objectMapper;
	
	@Setter @Getter
	String action;
	
	@Getter @Setter
	int numberOfRecords;
	
	@Getter @Setter
	UserSearch userSearch=new UserSearch();
	
	@Getter @Setter
	List<UserDto> userList=new ArrayList<UserDto>();
	
	@Getter @Setter
	UserDto userSelected=new UserDto();
	
	@Getter @Setter
	List<RoleDto> roleList=new ArrayList<RoleDto>();
	
	@Getter @Setter
	RoleDto selectedRole=new RoleDto();
	
	
	
	public String saveUser(){
		try{
			
			UserRoleDto userRole=new UserRoleDto();
			
			userRole.setRole(selectedRole);
			userRole.setStatus(Status.Active);			
			userSelected.getUserRoles().add(userRole);
			
			HttpEntity<UserDto> requestEntity = new HttpEntity<UserDto>(userSelected, LoginBean.header);

			ResponseEntity<String> response = restTemplate.exchange(restClient.createUrl("user/saveuser"),HttpMethod.POST,requestEntity,String.class);

			String responseBody = response.getBody();

			if (RestUtil.isError(response.getStatusCode())) {
				ErrorResource error = objectMapper.readValue(responseBody, ErrorResource.class);

				FacesUtil.error(error.getFieldErrors().get(0).getMessage());
				return null;

			} else {
				UserDto result = objectMapper.readValue(responseBody, UserDto.class);
				FacesUtil.info("saveUser has been saved.");

			}
		}
		catch(Exception e){
			log.error("saveuser", e);

		}
		showUserList();
		return "/pages/user/userlist.xhtml";
	}

	
	public String showUserList(){

		
		userSelected = new UserDto();
		numberOfRecords = 0;
		
		try{
           
			HttpEntity<UserSearch> requestEntity = new HttpEntity<UserSearch>(userSearch, LoginBean.header);

			ResponseEntity<String> response = restTemplate.exchange(restClient.createUrl("user/userlist"),HttpMethod.POST,requestEntity,String.class);

			String responseBody = response.getBody();

			if (RestUtil.isError(response.getStatusCode())) {
				ErrorResource error = objectMapper.readValue(responseBody, ErrorResource.class);

				FacesUtil.warn(error.getFieldErrors().get(0).getMessage());
				return "";

			} else {
				userList = objectMapper.readValue(responseBody, new TypeReference<List<UserDto>>(){});
				if(userList != null){
					numberOfRecords = userList.size();
				}

			}

		}
		catch(Exception e){
			log.error("showUserList", e);
		}
		userSearch = new UserSearch();
		return "/pages/user/userlist.xhtml";
		
	}
	
	public String showUserForm(){
		log.info("show User Form Called Action is "+action);
		log.info("show User Form Value is "+userSearch);
		if(action.equals("Add")){
			userSelected = new UserDto();
			getRolesList();
		}else if(action.equals("Edit")){
			if(userSelected == null){
				FacesUtil.warn("Please select any one User");
				return null;
			}
		}else if(action.equals("View")){
			if(userSelected == null){
				FacesUtil.warn("Please select any one User");
				return null;
			}
		}
		return "/pages/user/userprofile.xhtml";
	}
	
	public void clearSearch(){
		userSearch = new UserSearch();
	}
	
	public void clearForm(){		
		userSelected.setUserId(null);
		userSelected.setFirstName("");
		userSelected.setLastName("");
		userSelected.setMobile("");
		userSelected.setLoginId("");	
		userSelected.setPassword("");	
		userSelected.setEmailId("");	
		userSelected.setDob(null);	
		userSelected.setGender(Gender.Female);	
		userSelected.setAddress("");	
		userSelected.setStatus(Status.InActive);	
		userSelected.getUserRoles().clear();
	}
	
	public String cancelZipCodeForm(){
		userSelected = new 	UserDto();
		return "/pages/user/userlist.xhtml";
		
	}
	
	public void getRolesList(){

		UserSearch userSearch=new UserSearch();
		try{
           
			HttpEntity<UserSearch> requestEntity = new HttpEntity<UserSearch>(userSearch, LoginBean.header);

			ResponseEntity<String> response = restTemplate.exchange(restClient.createUrl("role/getrolelist"),HttpMethod.POST,requestEntity,String.class);

			String responseBody = response.getBody();

			if (RestUtil.isError(response.getStatusCode())) {
				ErrorResource error = objectMapper.readValue(responseBody, ErrorResource.class);

				FacesUtil.warn(error.getFieldErrors().get(0).getMessage());
				return ;

			} else {
				roleList = objectMapper.readValue(responseBody, new TypeReference<List<RoleDto>>(){});
			}

		}
		catch(Exception e){
			log.error("getRolesList", e);
		}
	}
	

}
