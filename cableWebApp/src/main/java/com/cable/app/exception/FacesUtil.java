package com.cable.app.exception;

import java.io.Serializable;
import java.text.MessageFormat;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

import javax.faces.context.FacesContext;



public class FacesUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static void facesMessage(Severity serverity, String msg) {
		FacesMessage message = new FacesMessage();
		message.setSeverity(serverity);
		message.setSummary(msg);
		message.setDetail("");
		getFacesContext().addMessage(null, message);
	}
	
	
	
	public static FacesMessage getFacesMessage(Severity serverity, String msg) {
		FacesMessage message = new FacesMessage();
		message.setSeverity(serverity);
		message.setSummary(msg);
		return message;
	}
	
	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
	public static void info(String msg) {
		facesMessage(FacesMessage.SEVERITY_INFO, msg);

	}

	public static void info(String msg, Object... params) {
		facesMessage(FacesMessage.SEVERITY_INFO,
				MessageFormat.format(msg, params));

	}

	public static void warn(String msg) {
		facesMessage(FacesMessage.SEVERITY_WARN, msg);

	}

	public static void warn(String msg, Object... params) {
		facesMessage(FacesMessage.SEVERITY_WARN,
				MessageFormat.format(msg, params));
	}

	public static void error(String msg) {
		facesMessage(FacesMessage.SEVERITY_ERROR, msg);

	}

	public static void error(String msg, Object... params) {
		facesMessage(FacesMessage.SEVERITY_ERROR,
				MessageFormat.format(msg, params));
	}

	public static void fatal(String msg) {
		facesMessage(FacesMessage.SEVERITY_FATAL, msg);
	}

	public static void fatal(String msg, Object... params) {
		facesMessage(FacesMessage.SEVERITY_FATAL,
				MessageFormat.format(msg, params));
	}
	
	

	

}
