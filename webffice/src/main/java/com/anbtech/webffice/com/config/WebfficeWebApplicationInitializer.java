package com.anbtech.webffice.com.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.multipart.support.MultipartFilter;

import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;


/**
 * @ClassName : WebfficeWebApplicationInitializer.java
 * @Description : 공통 컴포넌트 3.10 WebfficeWebApplicationInitializer 참조 작성
 *
 * @since  : 2023. 04. 20
 * @version : 1.0
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일              수정자               수정내용
 *  -------------  ------------   ---------------------
 * </pre>
 *
 */
public class WebfficeWebApplicationInitializer implements WebApplicationInitializer {

	private final Logger log = LoggerFactory.getLogger(WebfficeWebApplicationInitializer.class);

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		log.info("WebfficeWebApplicationInitializer START-============================================");
		log.debug("WebfficeWebApplicationInitializer START-============================================");
		System.out.print("WebfficeWebApplicationInitializer START-============================================");
		
        //-------------------------------------------------------------
        // Spring CharacterEncodingFilter 설정
        //-------------------------------------------------------------
        FilterRegistration.Dynamic characterEncoding = servletContext.addFilter("encodingFilter", new org.springframework.web.filter.CharacterEncodingFilter());
        characterEncoding.setInitParameter("encoding", "UTF-8");
        characterEncoding.setInitParameter("forceEncoding", "true");
        characterEncoding.addMappingForUrlPatterns(null, false, "*.do");

		// -------------------------------------------------------------
		// Spring Root Context 설정
		// -------------------------------------------------------------
		addRootContext(servletContext);

		// -------------------------------------------------------------
		// Webffice Web ServletContextListener 설정 - System property setting
		// -------------------------------------------------------------
		servletContext.addListener(new com.anbtech.webffice.com.config.WebfficeWebServletContextListener());

		log.info("WebfficeWebApplicationInitializer END-============================================");
	}

	/**
	 * @param servletContext
	 * Root Context를 등록한다.
	 */
	private void addRootContext(ServletContext servletContext) {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(WebfficeConfigApp.class);

		servletContext.addListener(new ContextLoaderListener(rootContext));
	}

}