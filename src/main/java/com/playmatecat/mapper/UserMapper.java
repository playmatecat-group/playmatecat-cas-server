package com.playmatecat.mapper;

import java.util.Map;

import com.playmatecat.domains.dto.UserDTO;

public interface UserMapper {
	/**
	 * 添加一条用户记录
	 * @param params
	 * @return
	 */
	public int addUser(Map<String,Object> params);
	
	public UserDTO getUser();
}
