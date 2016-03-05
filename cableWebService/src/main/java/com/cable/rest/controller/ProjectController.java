package com.cable.rest.controller;

import java.util.List;

import lombok.extern.log4j.Log4j;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cable.rest.dto.OrganizationDto;
import com.cable.rest.dto.ProjectDto;
import com.cable.rest.response.ResponseResource;
import com.cable.rest.search.ProjectSearch;

@RestController
@RequestMapping("/project")
@Log4j
public class ProjectController extends BaseController {
	
	
	
	@RequestMapping(value="/saveorganization",method=RequestMethod.POST)
	public Object saveOrganization(@RequestBody OrganizationDto reqObject){
		return  sendtoMQ(reqObject, "saveOrganization", "projectService");
	}
	
	@RequestMapping(value="/organizationlist",method=RequestMethod.POST)
	public List<OrganizationDto> getOrganization(@RequestBody ProjectSearch searchObject){
		return (List<OrganizationDto>) sendtoMQ(searchObject, "getOrganization", "projectService");
	}
	
	@RequestMapping(value="/deleteorganization",method=RequestMethod.POST)
	public ResponseResource deleteOrganization(@RequestBody ProjectSearch searchObject){
		return (ResponseResource) sendtoMQ(searchObject, "deleteOrganization", "projectService");
	}
	
	@RequestMapping(value="/saveproject",method=RequestMethod.POST)
	public Object  saveProject(@RequestBody ProjectDto reqObject){
		return sendtoMQ(reqObject, "saveProject", "projectService");
	}
	
	@RequestMapping(value="/projectlist",method=RequestMethod.POST)
	public Object getProject(@RequestBody ProjectSearch searchObject){
		return sendtoMQ(searchObject, "getProject", "projectService");
	}
	
	@RequestMapping(value="/deleteproject",method=RequestMethod.POST)
	public ResponseResource deleteProject(@RequestBody ProjectSearch searchObject){
		return (ResponseResource) sendtoMQ(searchObject, "deleteProject", "projectService");
	}
	
}
