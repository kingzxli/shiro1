package com.bona.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bona.service.UserService;

@Controller
public class UserController {
	

	
	@RequestMapping("/test")
	public String testThymeleaf(Model model) {
		model.addAttribute("name", "程序员");
		return "test";
		
	}

	@RequestMapping("/add")
	public String add() {
		return "user/add";
		
	}
	
	@RequestMapping("/update")
	public String update() {
		return "user/update";
		
	}
	
	@RequestMapping("/tologin")
	public String tologin() {
		return "login";
		
	}
	
	@RequestMapping("/unAuth")
	public String unAuth() {
		return "unAuth";
		
	}
	
	@RequestMapping("/login")
	public String login(String name,String password,Model model) {
		/**
		 * 使用shiro编写认证操作
		 */
		//1.获取Subject
		Subject subject=SecurityUtils.getSubject();
		//2.封装数据
		UsernamePasswordToken token=new UsernamePasswordToken(name, password);
		//3.执行登录方法
		try {
			subject.login(token);
			//登录成功			
			return "redirect:/test";
		} catch (UnknownAccountException e) {
			//e.printStackTrace();
			//登录失败
			model.addAttribute("msg","用户名不存在");
			return "login";
		}catch (IncorrectCredentialsException e) {
			//e.printStackTrace();
			//登录失败
			model.addAttribute("msg","密码错误");
			return "login";
		}

	}
	
}
