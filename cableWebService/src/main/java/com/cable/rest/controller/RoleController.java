package com.cable.rest.controller;

import java.util.List;

import javax.validation.Valid;

import lombok.extern.log4j.Log4j;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cable.rest.dto.RoleDto;
import com.cable.rest.exception.BadRequestException;
import com.cable.rest.exception.InvalidRequestException;
import com.cable.rest.response.ErrorResource;
import com.cable.rest.response.ResponseResource;
import com.cable.rest.search.UserSearch;

@RestController
@RequestMapping("/role")
@Log4j
public class RoleController extends BaseController {
	
	@RequestMapping(value="/saverole",method=RequestMethod.POST)
	public RoleDto  saveRole(@RequestBody RoleDto reqObject){
		return (RoleDto) sendtoMQ(reqObject, "saveRole", "roleService");
	}
	
	
	@RequestMapping(value="/getrolelist",method=RequestMethod.POST)
	public List<RoleDto> getRole(@RequestBody @Valid UserSearch searchObject,BindingResult bindingResult){
		if (bindingResult.hasErrors()){
			throw new InvalidRequestException("Exception", bindingResult);
		}
		
		Object object=sendtoMQ(searchObject, "getRole", "roleService");
		
		if(object.getClass() == ErrorResource.class){
			throw new BadRequestException("Exception", (ErrorResource)object);
		}
		
		return (List<RoleDto>) object;
	}
	
	@RequestMapping(value="/deleterole",method=RequestMethod.POST)
	public ResponseResource deleteRole(@RequestBody UserSearch searchObject){
		
		Object object = sendtoMQ(searchObject, "deleteRole", "roleService");
		
		if(object.getClass() == ErrorResource.class){
			throw new BadRequestException("Exception", (ErrorResource)object);
		}
		
		return (ResponseResource) object;
		
	}
}
