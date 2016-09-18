package com.cable.app.bean;

import java.util.ArrayList;
import java.util.Date;
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
import com.cable.rest.constants.PaymentStatus;
import com.cable.rest.dto.AreaDto;
import com.cable.rest.dto.ConnectionAccountDto;
import com.cable.rest.dto.GeneratePaymentDto;
import com.cable.rest.dto.ProjectDto;
import com.cable.rest.dto.StreetDto;
import com.cable.rest.dto.ZipCodeDto;
import com.cable.rest.response.ErrorResource;
import com.cable.rest.search.AccountSearch;
import com.cable.rest.search.MasterSearch;
import com.cable.rest.search.ProjectSearch;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@ManagedBean
@SessionScoped
@Log4j
public class ConnectionBean {
	
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
	AccountSearch accountSearch=new AccountSearch();
	
	@Getter @Setter
	List<ConnectionAccountDto> accountList=new ArrayList<ConnectionAccountDto>();
	
	@Getter @Setter
	ConnectionAccountDto accountSelected=new ConnectionAccountDto();
	
	@Getter @Setter
	MasterSearch masterSearch=new MasterSearch();
	
	@Getter @Setter
	List<ProjectDto> projectList=new ArrayList<ProjectDto>();
	
	@Getter @Setter
	List<AreaDto> areaList=new ArrayList<AreaDto>();
	
	@Getter @Setter
	List<StreetDto> streetList=new ArrayList<StreetDto>();
	
	@Getter @Setter
	GeneratePaymentDto genPayment=new GeneratePaymentDto();
	
	
	
	@Setter @Getter
	String action;
	
	@Getter @Setter
	int numberOfRecords;
	
	public String saveConnection(){
		try{

			HttpEntity<ConnectionAccountDto> requestEntity = new HttpEntity<ConnectionAccountDto>(accountSelected, LoginBean.header);

			ResponseEntity<String> response = restTemplate.exchange(restClient.createUrl("account/saveAccount"),HttpMethod.POST,requestEntity,String.class);

			String responseBody = response.getBody();

			if (RestUtil.isError(response.getStatusCode())) {
				ErrorResource error = objectMapper.readValue(responseBody, ErrorResource.class);

				FacesUtil.error(error.getFieldErrors().get(0).getMessage());
				return null;

			} else {
				ConnectionAccountDto result = objectMapper.readValue(responseBody, ConnectionAccountDto.class);
				FacesUtil.info("User has been saved.");

			}
		}
		catch(Exception e){
			log.error("saveuser", e);

		}
		showAccountList();
		return "/pages/connection/connectionlist.xhtml";
	}
	
	public String showAccountList(){

		
		accountSelected = new ConnectionAccountDto();
		numberOfRecords = 0;
		
		try{
           
			HttpEntity<AccountSearch> requestEntity = new HttpEntity<AccountSearch>(accountSearch, LoginBean.header);

			ResponseEntity<String> response = restTemplate.exchange(restClient.createUrl("account/accountlist"),HttpMethod.POST,requestEntity,String.class);

			String responseBody = response.getBody();

			if (RestUtil.isError(response.getStatusCode())) {
				ErrorResource error = objectMapper.readValue(responseBody, ErrorResource.class);

				FacesUtil.warn(error.getFieldErrors().get(0).getMessage());
				return "";

			} else {
				accountList = objectMapper.readValue(responseBody, new TypeReference<List<ConnectionAccountDto>>(){});
				if(accountList != null){
					numberOfRecords = accountList.size();
				}

			}

		}
		catch(Exception e){
			log.error("showAccountList", e);
		}
		accountSearch = new AccountSearch();
		return "/pages/connection/connectionlist.xhtml";
		
	}
	
	public String showAccountForm(){
		log.info("show Account Form Called Action is "+action);
		log.info("show Account Form Value is "+accountSearch);
		if(action.equals("Add")){
			getProjectsList();
			accountSelected = new ConnectionAccountDto();
		}else if(action.equals("Edit")){
			if(accountSelected == null){
				FacesUtil.warn("Please select any one Account");
				return null;
			}
		}else if(action.equals("View")){
			if(accountSelected == null){
				FacesUtil.warn("Please select any one Account");
				return null;
			}
		}
		return "/pages/connection/connectionform.xhtml";
	}
	
	public void clearForm(){		
		accountSelected.setAccountId(null);
		accountSelected.setAccountToken("");
		accountSelected.setName("");
		accountSelected.setMobile("");
		accountSelected.setEmailId("");
		accountSelected.setAddress("");
		accountSelected.setStreet(null);
		accountSelected.setProject(null);
		accountSelected.setRentAmount(0.0);
		accountSelected.setAdvancePaid(0.0);
	}
	
	public String cancelAccountForm(){
		accountSelected = new ConnectionAccountDto();
		return "/pages/connection/connectionlist.xhtml";
		
	}
	public void clearSearch(){
		accountSearch = new AccountSearch();
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
	
	
	public void updateArea(){
		masterSearch.setProjectId(accountSelected.getProject().getProjectId());
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
	
	public void updateStreet(){
		masterSearch.setProjectId(accountSelected.getStreet().getStreetId());
		getStreetLists();
	}
	
	public void getStreetLists(){
		
		try{
           
			HttpEntity<MasterSearch> requestEntity = new HttpEntity<MasterSearch>(masterSearch, LoginBean.header);

			ResponseEntity<String> response = restTemplate.exchange(restClient.createUrl("master/streetlist"),HttpMethod.POST,requestEntity,String.class);

			String responseBody = response.getBody();

			if (RestUtil.isError(response.getStatusCode())) {
				ErrorResource error = objectMapper.readValue(responseBody, ErrorResource.class);
				FacesUtil.warn(error.getFieldErrors().get(0).getMessage());
			} else {
				streetList = objectMapper.readValue(responseBody, new TypeReference<List<StreetDto>>(){});
			}

		}
		catch(Exception e){
			log.error("showStreetList", e);
		}
	}
	
	
	public String manualBill(){
		genPayment=new GeneratePaymentDto();
		genPayment.setConnectionAccount(accountSelected);
		return "/pages/payment/manualinvoice.xhtml";
	}
	
	public void clearBill(){
		genPayment=new GeneratePaymentDto();
	}
	
	public String saveInvoice(){
		try{
			
			genPayment.setPayGenDate(new Date());
			genPayment.setPayGenStatus(PaymentStatus.PENDING);
			
			HttpEntity<GeneratePaymentDto> requestEntity = new HttpEntity<GeneratePaymentDto>(genPayment, LoginBean.header);

			ResponseEntity<String> response = restTemplate.exchange(restClient.createUrl("payment/saveinvoice"),HttpMethod.POST,requestEntity,String.class);

			String responseBody = response.getBody();

			if (RestUtil.isError(response.getStatusCode())) {
				ErrorResource error = objectMapper.readValue(responseBody, ErrorResource.class);

				FacesUtil.error(error.getFieldErrors().get(0).getMessage());
				return null;

			} else {
				GeneratePaymentDto result = objectMapper.readValue(responseBody, GeneratePaymentDto.class);
				FacesUtil.info("Manual Invoice Sucess");

			}
		}
		catch(Exception e){
			log.error("saveuser", e);

		}
		showAccountList();
		return "/pages/connection/connectionlist.xhtml";
	}
	

}
