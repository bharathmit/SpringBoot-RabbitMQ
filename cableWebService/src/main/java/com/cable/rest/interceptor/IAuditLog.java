package com.cable.rest.interceptor;


public interface IAuditLog {
	
	public Long getEntityId();
	
	public String getLogDeatil();
}