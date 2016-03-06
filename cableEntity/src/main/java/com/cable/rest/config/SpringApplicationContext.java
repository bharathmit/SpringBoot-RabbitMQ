package com.cable.rest.config;

import java.lang.reflect.Method;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.cable.rest.exception.ExceptionResolver;
import com.cable.rest.exception.RestException;

@Component
@Log4j
public class SpringApplicationContext implements ApplicationContextAware {
	
	private static ApplicationContext CONTEXT;

	public void setApplicationContext(final ApplicationContext context)
			throws BeansException {
		CONTEXT = context;
	}

	public static <T> T getBean(Class<T> clazz) {
		return CONTEXT.getBean(clazz);
	}
	
	public static Object getAppResponse(Object request, final String serviceMethodName, final String serviceName){
		Object response = null;
		try{
			Object service = CONTEXT.getBean(serviceName);
			Method method = service.getClass().getMethod(serviceMethodName,request.getClass());
			response = method.invoke(service, request);
		}
		catch(Exception e){

        	if(e.getCause() instanceof RestException){
        		response=ExceptionResolver.businessRule(e.getCause());
        	}
        	else{
        		log.error("Service Exception", e);
        		response=ExceptionResolver.invalidDataAccess(e);
        	}
        
		}
		
		if(StringUtils.isEmpty(response)){
        	response=ExceptionResolver.nullPointer();
        }
		
		return response;
		
	}
	
}
