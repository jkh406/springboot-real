package com.anbtech.webffice.com.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import com.anbtech.webffice.auth.service.WebfficeLoginService;
import com.anbtech.webffice.com.util.WebfficeUserDetailsHelper;
import com.anbtech.webffice.com.vo.LoginVO;

public class MyAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    WebfficeLoginService webfficeLoginService;
    
	@Override
	public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
		// 인증 정보 가져오기
		Authentication authenticate = SecurityContextHolder.getContext().getAuthentication();
		AuthorizationDecision decision = new AuthorizationDecision(false);
		
		// 인증된 사용자가 없으면 접근 거부
		if(authenticate == null) {
			return decision = new AuthorizationDecision(false);
		}

		LoginVO loginVO = (LoginVO) WebfficeUserDetailsHelper.getAuthenticatedUser();
		
		// 인증된 사용자의 권한 정보 가져오기 
		List<LoginVO> userDtoList = webfficeLoginService.pageList(loginVO.getId()); // 사용자 이름으로 유저 정보를 디비에서 가져옴
		
		List<Map<String, Object>> pageAuthorityMap = new ArrayList<>();

		for(LoginVO userDto : userDtoList) {
		    Map<String, Object> resultMap = new HashMap<>();
		    resultMap.put("user_ID", userDto.getId());
		    resultMap.put("authority", userDto.getPageAuthority());
		    resultMap.put("page_url", userDto.getUrl());
		    pageAuthorityMap.add(resultMap);
		}
		
		// 요청한 URL의 접근 권한 가져오기
		String requestedUrl = object.getRequest().getRequestURI();
		String requestedMethod = object.getRequest().getMethod();
		
	    // 권한이 있는 경우 접근 허용
	    for(Map<String, Object> pageAuthority : pageAuthorityMap) {
	        String pageUrl = (String) pageAuthority.get("page_url");
    		System.out.print("requestedUrl = " + requestedUrl);
    		System.out.print("pageUrl = " + pageUrl);
	        if(requestedUrl.contains(pageUrl)) {
	    		System.out.print("=============AuthorizationDecision Success=============");
	            return new AuthorizationDecision(true);
	        }
	    }
		
		// 권한이 없으면 접근 거부
	    System.out.print("=============AuthorizationDecision False=============");
		return decision = new AuthorizationDecision(false);
	}
}
