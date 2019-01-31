package com.inslab.stringer;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

/**
 * Application Lifecycle Listener implementation class StringerLoaderListener
 *
 */
public class StringerLoaderListener extends ContextLoaderListener {

	private static final Logger logger = LoggerFactory.getLogger(StringerLoaderListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
		
		logger.info("contextDestroyed");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);

		logger.info("contextInitialized - " + getCurrentWebApplicationContext().getServletContext().getContextPath());
		/*
		 *  thalassa-aqueduct - codealley proxy 정보 초기와
		 *  
		 *  정보가 유지되기 위해 frontend / backend 키값이 존재하는지 우선 확인 
		 *  
		 *  cfg 파일없을 경우등 장애복구 정책 필요..
		 */
	}
}
