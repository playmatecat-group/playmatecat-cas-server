package com.playmatecat.cas;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 实现jdbc realm
 * @author root
 *
 */
public class CASRealm extends AuthorizingRealm {
	
	private final static Logger logger = Logger.getLogger(CASRealm.class);
	
	/**
	 * 获得授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		String username = (String) principals.fromRealm(getName()).iterator().next();
		
		if (username != null) {
			// 查询用户授权信息
//			Collection<String> pers = businessManager.queryPermissions(username);
//			if (pers != null && !pers.isEmpty()) {
//				SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//				for (String each : pers)
//					info.addStringPermissions(each);
//
//				return info;
//			}
		}
		return null;
	}
	
	/**
	 * 获取认证信息,也就是验证用户名密码
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		// 通过表单接收的用户名
		String username = upToken.getUsername();
		String password = new String(upToken.getPassword());
		
		//验证用户名不可为空
		if (username == null || StringUtils.isBlank(username)) {
			try {
				throw new AuthenticationException("username must not empty");
			} catch (Exception e) {
				logger.error(e.getMessage());
				return null;
			}
		}
		//验证密码不可为空
		if (password == null || StringUtils.isBlank(password)) {
			try {
				throw new AuthenticationException("username must not empty");
			} catch (Exception e) {
				logger.error(e.getMessage());
				return null;
			}
		}
		
		
		//进行DB用户名密码验证
		if("abc".equals(username) && "123".equals(password)) {
			return new SimpleAuthenticationInfo(username,password, getName());
		}
		
		//成功则返回令牌对象
//		return new SimpleAuthenticationInfo(account.getLoginName(),
//		account.getPassword(), getName());


		return null;
	}



}
