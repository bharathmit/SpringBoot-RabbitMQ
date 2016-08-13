package com.cable.rest.controller;

import lombok.extern.log4j.Log4j;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cable.rest.constants.ReportType;

@RestController
@RequestMapping("/report")
@Log4j
public class ReportController extends BaseController {

	
	@RequestMapping(value="/reportlist",method=RequestMethod.POST)
	public Object getProject(@RequestBody ReportType reportType){
		return sendtoMQ(reportType, "getReports", "reportService");
	}
}
