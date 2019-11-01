package com.bona.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import com.bona.domain.User;
import com.bona.service.UserService;

public class UserRealm extends AuthorizingRealm{
	
	@Autowired
	private UserService userService;

	/**
	 *执行授权逻辑
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("执行授权逻辑");
		//给资源进行授权
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		//info.addStringPermission("user:add");
		Subject subject=SecurityUtils.getSubject();
		User user=(User)subject.getPrincipal();  //获取SimpleAuthenticationInfo里面的参数user
		User dbUser=userService.findById(user.getId());
		
		info.addStringPermission(dbUser.getParms());
		return info;
	}

	/**
	 * 执行认证逻辑
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		System.out.println("执行认证逻辑");
		
		//编写shiro判断逻辑，判断用户名和密码
		UsernamePasswordToken token = (UsernamePasswordToken)arg0;
		User user=userService.findByName(token.getUsername());
		if(user==null) {
			return null;  //底层抛出UnknowAccountException  用户不存在
		}
		
		
		//判断密码
		return new SimpleAuthenticationInfo(user, user.getPassword(), ""); //参数1.返回login的数据，2.数据库的密码，3.shiro的名
				
	}

}
