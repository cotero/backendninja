package com.udemy.backendninja.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.udemy.backendninja.repository.LogRepository;

@Component("requestTimeInterceptor")
public class RequestTimeInterceptor extends HandlerInterceptorAdapter{

	private static final Log LOG = LogFactory.getLog(RequestTimeInterceptor.class);
	
	@Autowired
	@Qualifier("logRepository")
	private LogRepository logRepository;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setAttribute("startTime", System.currentTimeMillis());
		return Boolean.TRUE;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long startTime = (long) request.getAttribute("startTime");
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		String userName = "";
		if(auth!=null && auth.isAuthenticated()) {
			userName = auth.getName();
		}
		
		String url = request.getRequestURL().toString();
		com.udemy.backendninja.entity.Log log = new com.udemy.backendninja.entity.Log(new Date(), auth.getDetails().toString(), userName, url);
		
		logRepository.save(log);
		
		LOG.info("---------------------------------------------");
		LOG.info("-- REQUEST URL: " + url);
		LOG.info("-- REQUEST TOTAL TIME: " + (System.currentTimeMillis() - startTime));
		LOG.info("---------------------------------------------");
		
		
		
	}

	
	
}
