package com.playmatecat.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.playmatecat.domains.dto.UserDTO;
import com.playmatecat.mapper.UserMapper;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"classpath*:/config/spring/application*.xml"})
public class TestMapper {
	
	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void test() {
		UserDTO userDTO = new UserDTO();
//		userDTO.setEmail("ccc");
//		userDTO.setPassword("ccc");
//		userDTO.setLoginedAt(new java.util.Date());
//		userDTO.setNickName("ccc");
//
//		//userMapper.addUser(UtilsJson.parseObj2Map(userDTO));
//		
//		UserDTO dto2 = userMapper.getUser();
		
		int a = 1;
	}
}
