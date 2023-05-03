package com.anbtech.webffice.auth.service.impl;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.anbtech.webffice.auth.service.WebfficeLoginService;
import com.anbtech.webffice.com.jwt.JwtTokenProvider;
import com.anbtech.webffice.com.util.WebfficeFileScrty;
import com.anbtech.webffice.com.util.WebfficeNumberUtil;
import com.anbtech.webffice.com.util.WebfficeStringUtil;
import com.anbtech.webffice.com.vo.DefaultVO;
import com.anbtech.webffice.com.vo.LoginVO;

import lombok.RequiredArgsConstructor;

@Service("WebfficeLoginService")
@RequiredArgsConstructor
public class WebfficeLoginServiceImpl extends EgovAbstractServiceImpl implements WebfficeLoginService{
    // 암호화 위한 엔코더
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입 시 저장시간을 넣어줄 DateTime형
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
    Date time = new Date();
    String localTime = format.format(time);

	@Resource(name = "loginDAO")
	private LoginDAO loginDAO;

	/**
	 * 일반 로그인을 처리한다
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
	@Override
	public LoginVO actionLogin(LoginVO vo) throws Exception {

		// 1. 입력한 비밀번호를 암호화한다.
		String enpassword = WebfficeFileScrty.encryptPassword(vo.getPassword(), vo.getId());
		vo.setPassword(enpassword);

		// 2. 아이디와 암호화된 비밀번호가 DB와 일치하는지 확인한다.
		LoginVO loginVO = loginDAO.actionLogin(vo);

		// 3. 결과를 리턴한다.
		if (loginVO != null && !loginVO.getId().equals("") && !loginVO.getPassword().equals("")) {
			return loginVO;
		} else {
			loginVO = new LoginVO();
		}

		return loginVO;
	}


	/**
	 * 아이디를 찾는다.
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
	@Override
	public LoginVO searchId(LoginVO vo) throws Exception {

		// 1. 이름, 이메일주소가 DB와 일치하는 사용자 ID를 조회한다.
		LoginVO loginVO = loginDAO.searchId(vo);

		// 2. 결과를 리턴한다.
		if (loginVO != null && !loginVO.getId().equals("")) {
			return loginVO;
		} else {
			loginVO = new LoginVO();
		}

		return loginVO;
	}

	/**
	 * 비밀번호를 찾는다.
	 * @param vo LoginVO
	 * @return boolean
	 * @exception Exception
	 */
	@Override
	public boolean searchPassword(LoginVO vo) throws Exception {

		boolean result = true;

		// 1. 아이디, 이름, 이메일주소, 비밀번호 힌트, 비밀번호 정답이 DB와 일치하는 사용자 Password를 조회한다.
		LoginVO loginVO = loginDAO.searchPassword(vo);
		if (loginVO == null || loginVO.getPassword() == null || loginVO.getPassword().equals("")) {
			return false;
		}

		// 2. 임시 비밀번호를 생성한다.(영+영+숫+영+영+숫=6자리)
		String newpassword = "";
		for (int i = 1; i <= 6; i++) {
			// 영자
			if (i % 3 != 0) {
				newpassword += WebfficeStringUtil.getRandomStr('a', 'z');
				// 숫자
			} else {
				newpassword += WebfficeNumberUtil.getRandomNum(0, 9);
			}
		}

		// 3. 임시 비밀번호를 암호화하여 DB에 저장한다.
		LoginVO pwVO = new LoginVO();
		String enpassword = WebfficeFileScrty.encryptPassword(newpassword, vo.getId());
		pwVO.setId(vo.getId());
		pwVO.setPassword(enpassword);
		pwVO.setUserSe(vo.getId());
		loginDAO.updatePassword(pwVO);

		return result;
	}
	
//    
	
    /**
     * 권한이 있는 page가져오는 함수 
     */
    public List<LoginVO> pageList(String user_Id) throws Exception {

        List<LoginVO> loginVO = loginDAO.pageList(user_Id);
        
        return  loginVO;
    }
    
    /**
     * Token 생성
     */
    public DefaultVO tokenGenerator(String user_Id) throws Exception {
    	LoginVO loginVO = loginDAO.findUser(user_Id);

        return DefaultVO.builder()
        .accessToken("Bearer" + jwtTokenProvider.createAcessToken(loginVO.getId(), Collections.singletonList(loginVO.getUserSe())))
        .refreshToken("Bearer" + jwtTokenProvider.createRefreshToken(loginVO.getId(), Collections.singletonList(loginVO.getUserSe())))
        .build();
    }
}

