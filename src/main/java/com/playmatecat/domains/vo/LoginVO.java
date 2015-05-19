package com.playmatecat.domains.vo;

import com.playmatecat.domains.dto.UserDTO;

public class LoginVO {
	
	/**用户请求的子系统原始lastUrl地址**/
	private String url;
	
	/**子系统cas服务地址**/
	private String service;
	
	private UserDTO userDTO;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}


	
}
