package com.playmatecat.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playmatecat.domains.dto.UserDto;
import com.playmatecat.mapper.UserMapper;

@Service("userService")
public class UserService {
    
    @Autowired
    private UserMapper userMapper;
	
    /**
     * 获得登录用户信息
     * @param principal 登录的用户名/手机号/email
     * @param password 登录密码
     */
    public UserDto getUserInfo(String principal, String password) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("username", principal);
        params.put("password", password);
        return userMapper.getUser(params);
    }
}
