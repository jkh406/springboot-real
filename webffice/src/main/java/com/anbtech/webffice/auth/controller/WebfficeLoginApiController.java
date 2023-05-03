package com.anbtech.webffice.auth.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anbtech.webffice.auth.service.WebfficeLoginService;
import com.anbtech.webffice.com.DTO.response.BaseResponse;
import com.anbtech.webffice.com.DTO.response.MapDataResponse;
import com.anbtech.webffice.com.exception.LoginFailedException;
import com.anbtech.webffice.com.jwt.WebfficeJwtTokenUtil;
import com.anbtech.webffice.com.service.ResponseService;
import com.anbtech.webffice.com.vo.LoginVO;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 일반 로그인을 처리하는 컨트롤러 클래스
 * @since 2023.04.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *  수정일        수정자      수정내용
 *  -------    --------   ---------------------------
 *
 *  </pre>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class WebfficeLoginApiController {

    @Autowired
    WebfficeLoginService webfficeLoginService;
    
    @Autowired
    ResponseService responseService;

	/** JWT */
	@Autowired
    private WebfficeJwtTokenUtil jwtTokenUtil;
	
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginVO loginVO, HttpServletRequest request) throws Exception {
        log.info("login start !!!");
        try {
    		LoginVO loginResultVO = webfficeLoginService.actionLogin(loginVO);

			String jwtToken = jwtTokenUtil.generateToken(loginVO);
			
			String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			
	    	request.getSession().setAttribute("LoginVO", loginResultVO);
			
            Map<String, Object> userDataMap = loginResultVO.getUserDataMap();
            
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("Token", jwtToken);
            data.put("User", userDataMap);
            
            ResponseCookie responseCookie = ResponseCookie.from(HttpHeaders.SET_COOKIE, jwtToken)
                    .path("/")
                    .maxAge(14 * 24 * 60 * 60) // 14일
                    .httpOnly(true)
                    .build();

            MapDataResponse<Object> response = responseService.getMapDataResponse(true, loginVO.getId(), data);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, responseCookie.toString());
            return ResponseEntity.ok().headers(headers).body(response);

        } catch (LoginFailedException exception) {
            log.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping(value="/logout")
    public ResponseEntity<BaseResponse> logout(@CookieValue(value = HttpHeaders.SET_COOKIE) Cookie refreshCookie) {
        try {
            ResponseCookie responseCookie =
                    ResponseCookie.from(HttpHeaders.SET_COOKIE, "")
                            .path("/")
                            .httpOnly(true)
                            .secure(true)
                            .maxAge(0)
                            .build();
            BaseResponse response = responseService.getBaseResponse(true, "로그아웃 성공");
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .body(response);
        } catch (LoginFailedException exception) {
            log.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    

//    @PostMapping("/join")
//    public ResponseEntity<?> join(@RequestBody LoginVO loginVO) {
//        try {
//        	webfficeLoginService.join(loginVO);
//            DefaultVO token = webfficeLoginService.tokenGenerator(loginVO.getId());
//            ResponseCookie responseCookie = ResponseCookie.from(HttpHeaders.SET_COOKIE, token.getRefreshToken())
//                    .path("/")
//                    .maxAge(14 * 24 * 60 * 60) // 14일
//                    .httpOnly(true)
//                    .build();
//            SingleDataResponse<String> response = responseService.getSingleDataResponse(true, loginVO.getId(),
//                    token.getAccessToken());
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
//                    .body(response);
//        } catch (DuplicatedUsernameException e) {
//            BaseResponse response = responseService.getBaseResponse(false, e.getMessage());
//            return ResponseEntity.badRequest().body(response);
//        }
//    }

    

//    /**
//     * @param idDTO userId 전송을 위한 DTO
//     * @return userId가 있다면 success값을 true, 없다면 false를 리턴.
//     */
//    @GetMapping("/users/{user_Id}")
//    public ResponseEntity<BaseResponse> isHaveUser(@RequestParam String user_Id) {
//        try {
//            boolean isHaveUser = webfficeLoginService.haveUser(user_Id);
//            String message = isHaveUser ? "회원가입된 유저입니다." : "회원가입 안된 유저입니다.";
//            SingleDataResponse<Boolean> response = responseService.getSingleDataResponse(true, message, isHaveUser);
//            return ResponseEntity.ok(response);
//
//        } catch (UserNotFoundException exception) {
//            log.debug(exception.getMessage());
//            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());
//            return ResponseEntity.badRequest().body(response);
//        }
//    }
}