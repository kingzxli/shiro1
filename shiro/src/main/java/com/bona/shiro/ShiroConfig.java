package com.bona.shiro;
 
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class ShiroConfig {
	
	
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
		//设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		//添加Shiro内置过滤器，可以实现权限相关的拦截器
		/**
		 * anon:无需认证(登录)可以访问
		 * authc:必须认证才可以访问
		 * user:如果使用rememberMe的功能可以直接访问
		 * perms:该资源必须得到资源权限才可以访问
		 * role:该资源必须得到角色权限才可以访问
		 */
		
		Map<String,String> filterMap=new LinkedHashMap<String,String>();
		
		filterMap.put("/test", "anon"); //放行
		filterMap.put("/login", "anon"); //放行
		
		//授权过滤器  拦截后自动跳转到未授权页面
		filterMap.put("/add", "perms[user:add]");
		filterMap.put("/update", "perms[user:update]");
		
		filterMap.put("/*", "authc");  //通配符拦截
		
		//修改调整的登录页面  默认为login
		shiroFilterFactoryBean.setLoginUrl("/tologin");   
		
		//设置未授权提示页面
		shiroFilterFactoryBean.setUnauthorizedUrl("/unAuth");
		
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
		
		return shiroFilterFactoryBean;
		
	}
	
	@Bean(name="securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm")UserRealm userRealm) {
		DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
		//关联realm
		securityManager.setRealm(userRealm);
		return securityManager;
		
	}
	
	
	
	/**
	 * 创建Realm,传入到getDefaultWebSecurityManager方法
	 */
	@Bean(name="userRealm")
	public UserRealm getRealm() {
		return new UserRealm();
	}
	
	/**
	 * 配置ShiroDialect,用于thymeleaf和shiro标签配合使用
	 */
	@Bean
	public ShiroDialect getShiroDialect() {
		return new ShiroDialect();
	}
	
	
	
	
	
	
	
	
	
}
