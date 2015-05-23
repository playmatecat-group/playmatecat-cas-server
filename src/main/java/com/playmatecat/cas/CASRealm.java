package com.playmatecat.cas;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.DomainPermission;
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
		//等价
		principals.getPrimaryPrincipal();
		
		//这里实际getPrimaryPrincipal里存的应该是userId,然后到数据库里读取用户的角色、权限
		//子系统应该判定的是权限,然后调用某个http接口询问CAS是否有这个权限,CAS在controller里调用subject.isPermitted(权限)--->调用本方法doGetAuthorizationInfo
		//由于remember me或者自己设置的cookies效果(临时,关闭会话则失效,建议15分钟,和remember me双重记忆比较好),可以直接得到用户的subject进行鉴权
		//鉴权完毕后,将结果返回子系统,子系统写入缓存(定时刷新,或者通过共享缓存信息在更新的时候清空某个用户的缓存)
		
		//仔细思考了一下,或许可以把casRealm和childSysRealm都写入commons,这样统一调用,childSysRealm自己走cache和nio调用cas nio server
		
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
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermission("?module:?permission");
		info.addRole("?role:?childRole");
		
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
//		if("abc".equals(username) && "123".equals(password)) {
//			return new SimpleAuthenticationInfo(username,password, getName());
//		}
		
		//建议存userId用于鉴权
		if(!username.equals("")) {
			return new SimpleAuthenticationInfo(username,password, getName());
		}
		
		
		//成功则返回令牌对象
//		return new SimpleAuthenticationInfo(account.getLoginName(),
//		account.getPassword(), getName());


		return null;
	}



}
