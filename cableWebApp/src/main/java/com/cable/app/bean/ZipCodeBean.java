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
import com.cable.rest.dto.ZipCodeDto;
import com.cable.rest.response.ErrorResource;
import com.cable.rest.search.MasterSearch;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@ManagedBean
@SessionScoped
@Log4j
public class ZipCodeBean {

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
	ZipCodeDto zipCodeSelected=new ZipCodeDto();

	@Getter @Setter
	List<ZipCodeDto> zipcodeList=new ArrayList<ZipCodeDto>();
	
	@Setter @Getter
	String action;

	@Getter @Setter
	Integer numberOfRecords;
	
	

	public String saveZipCode(){
		try{

			HttpEntity<ZipCodeDto> requestEntity = new HttpEntity<ZipCodeDto>(zipCodeSelected, LoginBean.header);

			ResponseEntity<String> response = restTemplate.exchange(restClient.createUrl("master/savezipcode"),HttpMethod.POST,requestEntity,String.class);

			String responseBody = response.getBody();

			if (RestUtil.isError(response.getStatusCode())) {
				ErrorResource error = objectMapper.readValue(responseBody, ErrorResource.class);

				FacesUtil.error(error.getFieldErrors().get(0).getMessage());
				return null;

			} else {
				ZipCodeDto result = objectMapper.readValue(responseBody, ZipCodeDto.class);
				FacesUtil.info("ZIP Code has been saved.");

			}
		}
		catch(Exception e){
			log.error("saveZipCode", e);

		}
		getZipCodeValue();
		return "/pages/master/zipcodelist.xhtml";
	}

	public String getZipCodeValue(){
		
		zipCodeSelected = new ZipCodeDto();
		numberOfRecords = 0;
		
		try{
           
			HttpEntity<MasterSearch> requestEntity = new HttpEntity<MasterSearch>(masterSearch, LoginBean.header);

			ResponseEntity<String> response = restTemplate.exchange(restClient.createUrl("master/zipcodelist"),HttpMethod.POST,requestEntity,String.class);

			String responseBody = response.getBody();

			if (RestUtil.isError(response.getStatusCode())) {
				ErrorResource error = objectMapper.readValue(responseBody, ErrorResource.class);

				FacesUtil.warn(error.getFieldErrors().get(0).getMessage());
				return "";

			} else {
				/*ZipCodeDto[] list = objectMapper.readValue(responseBody, ZipCodeDto[].class);
            	zipcodeList=Arrays.asList(list);*/
				zipcodeList = objectMapper.readValue(responseBody, new TypeReference<List<ZipCodeDto>>(){});
				if(zipcodeList != null){
					numberOfRecords = zipcodeList.size();
				}

			}

		}
		catch(Exception e){
			log.error("getZipCodeValue", e);
		}
		masterSearch = new MasterSearch();
		return "/pages/master/zipcodelist.xhtml";
	}
	
	
	public String showZipCodeForm(){
		log.info("show ZIP Code Called Action is "+action);
		log.info("show ZIP Code Called Value is "+zipCodeSelected);
		if(action.equals("Add")){
			zipCodeSelected = new ZipCodeDto();
		}else if(action.equals("Edit")){
			if(zipCodeSelected == null){
				FacesUtil.warn("Please select any one ZIP Code");
				return null;
			}
		}else if(action.equals("View")){
			if(zipCodeSelected == null){
				FacesUtil.warn("Please select any one ZIP Code");
				return null;
			}
		}
		return "/pages/master/zipcodeform.xhtml";
	}
	
	
	public void clearSearch(){
		masterSearch = new MasterSearch();	
	}
	
	
	public void clearForm(){
		zipCodeSelected.setLocationName("");
		zipCodeSelected.setPinCode("");
		zipCodeSelected.setDistrict("");
		zipCodeSelected.setState("");
		zipCodeSelected.setCountry("");	
	}
	
	public String cancelZipCodeForm(){
		zipCodeSelected = new ZipCodeDto();
		return "/pages/master/zipcodelist.xhtml";
		
	}
}
