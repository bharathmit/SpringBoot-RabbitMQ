package com.cable.rest.utils;

import java.io.InputStream;
import java.util.HashMap;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

 

@Component
public class ReportRenderer {
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
    private DataSource dataSource;
	
	
	public byte[] getPDFInputStream(String reportName, HashMap reportArgs){
		
		try{
			InputStream reportStream = resourceLoader.getResource("classpath:reports/"+reportName).getInputStream();
			JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream,reportArgs, dataSource.getConnection());
			return JasperExportManager.exportReportToPdf(jasperPrint);
			
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		

	}
	
	
}
