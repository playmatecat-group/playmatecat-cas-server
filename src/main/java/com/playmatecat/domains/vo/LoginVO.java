package com.playmatecat.domains.vo;

import com.playmatecat.domains.dto.UserDto;

public class LoginVO {
    
    /** 登录的用户名/手机号/email **/
    private String principal;
	
	/** 用户请求的子系统原始lastUrl地址 **/
	private String url;
	
	/** 用户是否勾选记住我  **/
	private boolean rememberMe;

    private UserDto userDTO;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public UserDto getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDto userDTO) {
		this.userDTO = userDTO;
	}

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

  
}
