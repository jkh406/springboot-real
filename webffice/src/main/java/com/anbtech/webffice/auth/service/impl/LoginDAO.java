package com.anbtech.webffice.auth.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import com.anbtech.webffice.com.vo.LoginVO;

/**
 * 일반 로그인을 처리하는 비즈니스 구현 클래스
 * @since 2023.04.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *
 *  </pre>
 */
@Mapper("loginDAO")
public interface  LoginDAO {

	public LoginVO actionLogin(LoginVO vo);
	public LoginVO searchId(LoginVO vo);
	public LoginVO searchPassword(LoginVO vo);
	public void updatePassword(LoginVO vo);
	public List<LoginVO> pageList(String user_Id);
//	/**
//	 * 일반 로그인을 처리한다
//	 * @param vo LoginVO
//	 * @return LoginVO
//	 * @exception Exception
//	 */
//	public LoginVO actionLogin(LoginVO vo) throws Exception {
//		return (LoginVO) selectOne("loginDAO.actionLogin", vo);
//	}
//
//	/**
//	 * 아이디를 찾는다.
//	 * @param vo LoginVO
//	 * @return LoginVO
//	 * @exception Exception
//	 */
//	public LoginVO searchId(LoginVO vo) throws Exception {
//		return (LoginVO) selectOne("loginDAO.searchId", vo);
//	}
//
//	/**
//	 * 비밀번호를 찾는다.
//	 * @param vo LoginVO
//	 * @return LoginVO
//	 * @exception Exception
//	 */
//	public LoginVO searchPassword(LoginVO vo) throws Exception {
//		return (LoginVO) selectOne("loginDAO.searchPassword", vo);
//	}
//
//	/**
//	 * 변경된 비밀번호를 저장한다.
//	 * @param vo LoginVO
//	 * @exception Exception
//	 */
//	public void updatePassword(LoginVO vo) throws Exception {
//		update("loginDAO.updatePassword", vo);
//	}
//
//	/**
//	 * 페이지 권한.
//	 * @param user_Id LoginVO
//	 * @return 
//	 * @exception Exception
//	 */
//	public List<LoginVO> pageList(String user_Id) {
//	    return selectList("loginDAO.pageList", user_Id);
//	}

}