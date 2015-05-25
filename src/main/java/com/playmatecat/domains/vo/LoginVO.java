package com.playmatecat.domains.vo;

import com.playmatecat.domains.dto.UserDTO;

public class LoginVO {
	
	/** 用户请求的子系统原始lastUrl地址 **/
	private String url;
	
	/** 用户是否勾选记住我  **/
	private boolean rememberMe;

    private UserDTO userDTO;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

  
}
