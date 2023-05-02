package com.anbtech.webffice.auth.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.anbtech.webffice.com.vo.LoginVO;

@Mapper
public interface WebfficeLoginMapper {
    void join(LoginVO loginVO);  
    Optional<LoginVO> findUser(String user_Id);
    Optional<LoginVO> findUserId(String user_Id);
    List<LoginVO> pageList(String user_Id);

}
