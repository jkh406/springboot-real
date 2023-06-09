package com.anbtech.webffice.com.jwt;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request,
			jakarta.servlet.http.HttpServletResponse response, AccessDeniedException accessDeniedException)
			throws IOException, jakarta.servlet.ServletException {
		System.out.print("Error Response = " + HttpServletResponse.SC_FORBIDDEN);
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
		
	}
}
