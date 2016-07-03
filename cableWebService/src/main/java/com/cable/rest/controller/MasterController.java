package com.cable.rest.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cable.rest.dto.AreaDto;
import com.cable.rest.dto.StreetDto;
import com.cable.rest.dto.ZipCodeDto;
import com.cable.rest.search.MasterSearch;

@RestController
@RequestMapping("/master")
public class MasterController extends BaseController {
	
	
	@RequestMapping(value="/savezipcode",method=RequestMethod.POST)
	public Object  saveZipCode(@RequestBody ZipCodeDto reqObject){
		return sendtoMQ(reqObject, "saveZipCode", "masterService");
	}
	
	@RequestMapping(value="/zipcodelist",method=RequestMethod.POST)
	public Object getZipCode(@RequestBody MasterSearch searchObject){
		return sendtoMQ(searchObject, "getZipCode", "masterService");
	}
	
	@RequestMapping(value="/deletezipcode",method=RequestMethod.POST)
	public Object deleteZipCode(@RequestBody MasterSearch searchObject){
		return sendtoMQ(searchObject, "deleteZipCode", "masterService");
	}
	
	@RequestMapping(value="/savearea",method=RequestMethod.POST)
	public Object  saveArea(@RequestBody AreaDto reqObject){
		return sendtoMQ(reqObject, "saveArea", "masterService");
	}
	
	@RequestMapping(value="/arealist",method=RequestMethod.POST)
	public Object getArea(@RequestBody MasterSearch searchObject){
		return sendtoMQ(searchObject, "getArea", "masterService");
	}
	
	@RequestMapping(value="/deletearea",method=RequestMethod.POST)
	public Object deleteArea(@RequestBody MasterSearch searchObject){
		return sendtoMQ(searchObject, "deleteArea", "masterService");
	}
	
	
	
	
	@RequestMapping(value="/savestreet",method=RequestMethod.POST)
	public Object  saveStreet(@RequestBody StreetDto reqObject){
		return sendtoMQ(reqObject, "saveStreet", "masterService");
	}
	
	@RequestMapping(value="/streetlist",method=RequestMethod.POST)
	public Object getStreet(@RequestBody MasterSearch searchObject){
		return  sendtoMQ(searchObject, "getStreet", "masterService");
	}
	
	@RequestMapping(value="/deletestreet",method=RequestMethod.POST)
	public Object deleteStreet(@RequestBody MasterSearch searchObject){
		return sendtoMQ(searchObject, "deleteStreet", "masterService");
	}
	
}
