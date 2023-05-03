package com.anbtech.webffice.auth.service;

import java.util.List;

import com.anbtech.webffice.com.vo.DefaultVO;
import com.anbtech.webffice.com.vo.LoginVO;

public interface  WebfficeLoginService {

	/**
	 * 일반 로그인을 처리한다
	 * @return LoginVO
	 *
	 * @param vo    LoginVO
	 * @exception Exception Exception
	 */
	public LoginVO actionLogin(LoginVO vo) throws Exception;

	/**
	 * 아이디를 찾는다.
	 * @return LoginVO
	 *
	 * @param vo    LoginVO
	 * @exception Exception Exception
	 */
	public LoginVO searchId(LoginVO vo) throws Exception;

	/**
	 * 비밀번호를 찾는다.
	 * @return boolean
	 *
	 * @param vo    LoginVO
	 * @exception Exception Exception
	 */
	public boolean searchPassword(LoginVO vo) throws Exception;
	
	/**
	 * page 권한.
	 * @return List<LoginVO>
	 *
	 * @param vo    LoginVO
	 * @exception Exception Exception
	 */
	public List<LoginVO> pageList(String id) throws Exception;
	
	/**
	 * token 생성.
	 * @return DefaultVO
	 *
	 * @param vo    DefaultVO
	 * @exception Exception Exception
	 */
	public DefaultVO tokenGenerator(String id) throws Exception;
}