package com.playmatecat.mapper;

import java.util.Map;

import com.playmatecat.domains.dto.UserDto;

public interface UserMapper {
	/**
	 * 添加一条用户记录
	 * @param params
	 * @return
	 */
	public int addUser(Map<String,Object> params);
	
	/**
	 * 获得用户信息
	 * @param params
	 * @return
	 */
	public UserDto getUser(Map<String,Object> params);
}
