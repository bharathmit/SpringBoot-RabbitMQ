package com.cable.rest.utils;

/*import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.bte.hospital.core.UserContext;
import org.jfree.util.Log;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;*/

/*
 import net.sf.jasperreports.engine.JRParameter;
 import net.sf.jasperreports.engine.JasperCompileManager;
 import net.sf.jasperreports.engine.JasperExportManager;
 import net.sf.jasperreports.engine.JasperFillManager;
 import net.sf.jasperreports.engine.JasperPrint;
 import net.sf.jasperreports.engine.util.JRLoader;
 import net.sf.jasperreports.engine.util.SimpleFileResolver;
 */

public class ReportRenderer {/*
	//private static Logger log = Logger.getLogger(ReportRenderer.class);
	private static final int DEFAULT_BUFFER_SIZE = 10240;

	Connection connection;
	UserContext userContext;
	Properties pro = new Properties();

	public ReportRenderer() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		userContext = (UserContext) facesContext.getApplication()
				.evaluateExpressionGet(facesContext, "#{UserContext}",
						UserContext.class);

		connection = userContext.getConnection();

	}
	
	
	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();
		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}

	public boolean generateReport(String reportName, HashMap reportArgs) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext
				.getResponse();
		String reportsDirPath = "http://localhost:8080"
				+ externalContext.getRequestContextPath() + "/reports";
		
		String reportsDirPath = "webapps/akeh/ROOT/reports";
			
		boolean bReportStatus = false;

		if (reportName.isEmpty() || reportName == null) {
			return false;
		}
		if (reportArgs == null)
			reportArgs = new HashMap();
		String name, addr1, addr2, phone, email, time1, time2, name1, name2, title, website, city ;
		try {
			name = CodeList.getLabelFromCode("ORGNAME", 1);
			addr1 = CodeList.getLabelFromCode("ORGADDRESS", 1);
			addr2 = CodeList.getLabelFromCode("ORGCITY", 1);
			phone = CodeList.getLabelFromCode("ORGPHONE", 1);
			email = CodeList.getLabelFromCode("ORGEMAIL", 1);
			time1 = CodeList.getLabelFromCode("ORGTIME1", 1);
			time2 = CodeList.getLabelFromCode("ORGTIME2", 1);
			name1 = CodeList.getLabelFromCode("ORGNAME1", 1);
			name2 = CodeList.getLabelFromCode("ORGNAME2", 1);
			
			city=CodeList.getLabelFromCode("ORGCITY", 1);
			
			
			website = CodeList.getLabelFromCode("ORGWEBSITE", 1);
			title = CodeList.getLabelFromCode("ORGTITLE", 1);
			reportArgs.put("SUBREPORT_DIR", reportsDirPath);
			
			reportArgs.put("org_name", name);
			reportArgs.put("org_addressline1", addr1);
			reportArgs.put("org_addressline2", addr2);
			reportArgs.put("org_phone", phone);
			reportArgs.put("org_name1", name1);
			reportArgs.put("org_email", email);
			reportArgs.put("org_time", time1);
			reportArgs.put("org_time1", time2);
			reportArgs.put("org_name2", name2);
			reportArgs.put("org_city", city);
			
			

			


			InputStream reportStream = externalContext
					.getResourceAsStream("/reports/" + reportName);
			JasperRunManager.runReportToPdfStream(reportStream,
					response.getOutputStream(), reportArgs, connection);

			
			bReportStatus = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			HibernateUtil.closeSession();
			e.printStackTrace();
		}
		facesContext.responseComplete();
		//log.info("reports path = " + reportsDirPath);
		HibernateUtil.closeSession();
		return bReportStatus;
	}

	
	
	public static byte[] getPDFInputStream(String reportName, HashMap reportArgs){
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		
		try{
			
			UserContext userContext = (UserContext) facesContext.getApplication()
					.evaluateExpressionGet(facesContext, "#{UserContext}",
							UserContext.class);

			
			InputStream reportStream = externalContext.getResourceAsStream("/reports/" + reportName);
			JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream,reportArgs, userContext.getConnection());
			
			JasperExportManager exporter = new JasperExportManager();
                    
			return exporter.exportReportToPdf(jasperPrint);
			
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		

	}
	
	
*/}
