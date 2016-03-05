package com.cable.app.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j;

import org.springframework.util.StringUtils;




@Log4j
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        try {
        	
        	log.info("Entered into Login Filter");
        	
        	/*HttpServletRequest req = (HttpServletRequest) request;
        	HttpServletResponse res = (HttpServletResponse) response;
        	
        	LoginResponseDto loginBean = (LoginResponseDto) ((HttpServletRequest) request).getSession()
    				.getAttribute("UserObject");
        	 String path = req.getRequestURI().substring(req.getContextPath().length());
        	 
        	 if (path.contains("/pages/")) {
        		 String contextPath = ((HttpServletRequest)request).getContextPath();
	        	if (!StringUtils.isEmpty(loginBean)) {
	        		 if (!StringUtils.isEmpty(loginBean.getUserDetailDto())) {
	        			 chain.doFilter(request, response);
	                 } else {	   
	                	 res.sendRedirect(contextPath+"/login.xhtml");
	                 }
	    		} else {
	    			res.sendRedirect(contextPath+"/login.xhtml");
	    		}
	        	
        	 }
        	 else{
        		 chain.doFilter(request, response);
        	 }*/
        	 
        	
        	chain.doFilter(request, response);
        	 
        } catch (Throwable e) {
            log.error("Login Filtter Error", e);
        }
      
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}