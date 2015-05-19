package com.playmatecat.domains.vo;

public class LoginVO {
	
	/**用户请求的子系统原始lastUrl地址**/
	private String url;
	
	/**子系统cas服务地址**/
	private String service;

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
	
}
