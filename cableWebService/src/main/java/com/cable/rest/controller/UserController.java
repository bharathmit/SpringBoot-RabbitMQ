package com.cable.rest.controller;

import javax.validation.Valid;

import lombok.extern.log4j.Log4j;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cable.rest.dto.UserDto;
import com.cable.rest.exception.InvalidRequestException;
import com.cable.rest.response.ResponseResource;
import com.cable.rest.search.UserSearch;

@RestController
@RequestMapping("/user")
@Log4j
public class UserController extends BaseController {
	
	@RequestMapping(value="/userexit",method=RequestMethod.POST)
	public UserDto  exitUser(@RequestBody UserDto reqObject){
		return (UserDto) sendtoMQ(reqObject, "exitUser", "userService");
	}
	
	@RequestMapping(value="/saveuser",method=RequestMethod.POST)
	public Object  saveUser(@RequestBody @Valid UserDto reqObject,BindingResult bindingResult){
		if (bindingResult.hasErrors()){
			throw new InvalidRequestException("Exception", bindingResult);
		}
		return sendtoMQ(reqObject, "saveUser", "userService");
	}
	
	
	@RequestMapping(value="/userlist",method=RequestMethod.POST)
	public Object getUser(@RequestBody UserSearch searchObject){
		return sendtoMQ(searchObject, "getUser", "userService");
	}
	
	@RequestMapping(value="/deleteuser",method=RequestMethod.POST)
	public ResponseResource deleteUser(@RequestBody UserSearch searchObject){
		return (ResponseResource) sendtoMQ(searchObject, "deleteUser", "userService");
	}
	

}
