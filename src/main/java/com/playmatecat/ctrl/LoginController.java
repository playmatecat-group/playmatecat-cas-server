package com.playmatecat.ctrl;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.subject.support.DefaultWebSubjectContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.playmatecat.domains.vo.LoginVO;
import com.playmatecat.shiro.rememberMe.LightCookieRememberMeManager;
import com.playmatecat.utils.spring.UtilsSpringContext;

@Controller
@RequestMapping("")
public class LoginController {
	
	private final static Logger logger = Logger.getLogger(LoginController.class);

	@RequestMapping("/login")
	public String loginView( @ModelAttribute LoginVO loginVO, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("show login page...");
		
		return "/login-module/login";
	}
	
	/**
	 * 计算项目的跳转到子项目的地址
	 * @deprecated 现在由子项目自己给出计算结果
	 * @return
	 */
	@SuppressWarnings("unused")
    private void calcBaseUrl(LoginVO loginVO) {
	    /*
         * @step 根据原请求地址,计算出子项目登入地址.
         * eg:www.playmate.com/playmatecate-web/user?id=123
         * ->www.playmate.com/playmatecate-web
         * ->www.playmate.com/playmatecate-web/cas-login
         */
        //原请求地址
        String lastUrl = loginVO.getUrl();
        if(StringUtils.isNoneBlank(lastUrl)) {
            //排除第一个单斜杠,((?!(/)).)+ 任意字符除了单斜杠
            String regex = "^.+//((?!(/)).)+/((?!(/)).)+";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(lastUrl);
            String baseUrl = StringUtils.EMPTY;
            while(m.find()) {
                //获得子项目干净的根网址
                baseUrl = m.group(0);
                break;
            }
            
            //拼出子项目登入服务地址
            String service =  baseUrl + "/cas-login";
//            loginVO.setService(service);
        }
	}
	
	@RequestMapping(value = "/login-params",method = RequestMethod.POST)
	public String loginParams(@Valid @ModelAttribute LoginVO loginVO, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		String username = "" + RandomUtils.nextInt(0, 1000);
		String password = "" + RandomUtils.nextInt(0, 1000);
		logger.debug("do post login-params...");
		
		//获得用户subject
		Subject subject = SecurityUtils.getSubject();

		//创建登陆信息token
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		//跨域记住我
		token.setRememberMe(true);
		
		try {
		    //@see CASRealm#doGetAuthenticationInfo(AuthenticationToken)
			subject.login(token);
		} catch (Exception e) {
			logger.error(MessageFormat.format("登陆失败.username={0},password={1}", username, password));
		}
		
		
		//处理用户没勾选记住我,那么记住我的有效时间仅为当前对话期间
		if(!loginVO.isRememberMe()) {
		    LightCookieRememberMeManager rememberMeManager 
                = (LightCookieRememberMeManager) UtilsSpringContext.getBean("rememberMeManager");
            Cookie shiroCookie = rememberMeManager.getCookie();
            shiroCookie.setMaxAge(-1);
		}

		/*//其他cookies 跨域设置范例:
		Cookie cookies = new Cookie("test", "dg8vf");
		cookies.setDomain("playmatecat.com");
		cookies.setMaxAge(-1);
		cookies.setPath("/");
		response.addCookie(cookies);*/

		
		//准备重定向到之前用户最后访问地址
		String redirectUrl = loginVO.getUrl();
		
		String rtn = "redirect:" + redirectUrl;
		
		if(StringUtils.isBlank(loginVO.getUrl())) {
			//若不存在跳转地址,则跳到单点登录成功页
			rtn = "index";
		}
		
		//测试鉴权1
		subject.isPermitted("test:test");
		
//		//测试鉴权2,依然执行
//		subject.isPermitted("test:test");
		
		
		System.out.println(subject.isRemembered());
		
		return rtn;
	}
	
	
	@RequestMapping("/test")
	public String test( @ModelAttribute LoginVO loginVO, Model model) {
		Subject subject = SecurityUtils.getSubject();
		subject.getSession().setAttribute("test", "haha");
		System.out.println(subject.isRemembered());
		return "index";
	}
	

}
