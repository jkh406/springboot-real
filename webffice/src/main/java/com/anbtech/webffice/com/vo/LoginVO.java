package com.anbtech.webffice.com.vo;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @Class Name : LoginVO.java
 * @Description : Login VO class
 * @Modification Information
 * @
 * @  수정일         수정자                   수정내용
 * @ -------    --------    ---------------------------
 *
 *  @since 2023.04.20
 *  @version 1.0
 *  @see
 *  
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO implements UserDetails{
	
	/** 아이디 */
	private String id;
	/** 이름 */
	private String name;
	/** 이메일주소 */
	private String email;
	/** 비밀번호 */
	private String password;
	/** 사용자구분 */
	private String userSe;
	/** 조직(부서)ID */
	private String dept_no;
	/** 조직(부서)명 */
	private String dept_nm;
	/** 로그인 후 이동할 페이지 */
	private String url;
	/** 고유아이디 */
	private String emp_no;
	/** 사용자 IP정보 */
	private String ip;
	/** 권한 */
    private String authority;
	/** 페이지 */
    private String page_url;
	/** 생성 날짜 */
    private String create_Date;
	/** 업데이트 날짜 */
    private String update_Date;
    
    public Map<String, Object> getUserDataMap() {
        Map<String, Object> userDataMap = new LinkedHashMap<>();
        userDataMap.put("user_ID", this.id);
        userDataMap.put("user_name", this.name);
        userDataMap.put("user_role", this.userSe);
        return userDataMap;
    }
	
    public Map<String, Object> getPageAuthority() {
        Map<String, Object> pageAuthorityMap = new LinkedHashMap<>();
        pageAuthorityMap.put("user_ID", this.id);
        pageAuthorityMap.put("authority", this.authority);
        pageAuthorityMap.put("page_url", this.page_url);
        return pageAuthorityMap;
    }
    
	/**
	 * id attribute 를 리턴한다.
	 * @return String
	 */
	public String getId() {
		return id;
	}
	/**
	 * id attribute 값을 설정한다.
	 * @param id String
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * name attribute 를 리턴한다.
	 * @return String
	 */
	public String getName() {
		return name;
	}
	/**
	 * name attribute 값을 설정한다.
	 * @param name String
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * email attribute 를 리턴한다.
	 * @return String
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * email attribute 값을 설정한다.
	 * @param email String
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * password attribute 를 리턴한다.
	 * @return String
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * password attribute 값을 설정한다.
	 * @param password String
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * userSe attribute 를 리턴한다.
	 * @return String
	 */
	public String getUserSe() {
		return userSe;
	}
	/**
	 * userSe attribute 값을 설정한다.
	 * @param userSe String
	 */
	public void setUserSe(String userSe) {
		this.userSe = userSe;
	}
	/**
	 * dept_no attribute 를 리턴한다.
	 * @return String
	 */
	public String getDept_no() {
		return dept_no;
	}
	/**
	 * orgnztId attribute 값을 설정한다.
	 * @param orgnztId String
	 */
	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}
	/**
	 * url attribute 를 리턴한다.
	 * @return String
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * url attribute 값을 설정한다.
	 * @param url String
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the dept_nm
	 */
	public String getDept_nm() {
		return dept_nm;
	}
	/**
	 * @param orgnztNm the orgnztNm to set
	 */
	public void setDept_nm(String dept_nm) {
		this.dept_nm = dept_nm;
	}

	/**
	 * uniqId attribute 를 리턴한다.
	 * @return String
	 */
	public String getEmp_no() {
		return emp_no;
	}
	/**
	 * uniqId attribute 값을 설정한다.
	 * @param uniqId String
	 */
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}

	/**
	 * ip attribute 를 리턴한다.
	 * @return String
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * ip attribute 값을 설정한다.
	 * @param ip String
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * create_Date attribute 를 리턴한다.
	 * @return String
	 */
	public String getCreate_Date() {
		return create_Date;
	}
	/**
	 * create_Date attribute 값을 설정한다.
	 * @param String
	 */
	public void setCreate_Date(String create_Date) {
		this.create_Date = create_Date;
	}

	/**
	 * update_Date attribute 를 리턴한다.
	 * @return String
	 */
	public String getUpdate_Date() {
		return update_Date;
	}
	/**
	 * update_Date attribute 값을 설정한다.
	 * @param String
	 */
	public void setUpdate_Date(String update_Date) {
		this.update_Date = update_Date;
	}
	
    @Override
    public String getUsername() {
        return this.name;
    }
	
	// 이하 코드는 security 를 위한 용도
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Collection<? extends GrantedAuthority> authorities;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isEnabled() {
        return true;
    }
	
}