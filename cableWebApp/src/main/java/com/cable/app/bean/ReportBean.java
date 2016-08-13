package com.cable.app.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.cable.app.exception.FacesUtil;
import com.cable.app.exception.RestUtil;
import com.cable.app.utils.RestClient;
import com.cable.rest.constants.ReportType;
import com.cable.rest.dto.ProjectDto;
import com.cable.rest.dto.ReportDto;
import com.cable.rest.response.ErrorResource;
import com.cable.rest.search.ProjectSearch;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@ManagedBean
@SessionScoped
@Log4j
public class ReportBean {
	
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
	private TreeNode root;
	
	@Getter @Setter
	private TreeNode selectedNode;
	
	
	public String showReportList(){
		
		try{
			root = new DefaultTreeNode(new ReportDto(), null);
			
			for (ReportType reportType : ReportType.values()) {
				
				HttpEntity<ReportType> requestEntity = new HttpEntity<ReportType>(reportType, LoginBean.header);
				@SuppressWarnings("unused")
				List<ReportDto> reportList;
				
				ResponseEntity<String> response = restTemplate.exchange(restClient.createUrl("report/reportlist"),HttpMethod.POST,requestEntity,String.class);

				String responseBody = response.getBody();

				if (RestUtil.isError(response.getStatusCode())) {
					ErrorResource error = objectMapper.readValue(responseBody, ErrorResource.class);

					FacesUtil.warn(error.getFieldErrors().get(0).getMessage());
					return "";

				} else {
					reportList = objectMapper.readValue(responseBody, new TypeReference<List<ReportDto>>(){});
					ReportDto reportObj= new ReportDto();
					reportObj.setReportName(reportType.getValue());
					TreeNode child = new DefaultTreeNode(reportObj, root);
			         
					for(ReportDto report : reportList){
						TreeNode node = new DefaultTreeNode(report, child);
					}
				}
			}
			
			

		}
		catch(Exception e){
			log.error("showReportList", e);
		}
		return "/pages/report/report.xhtml";
		
	
	}
	
	
}
