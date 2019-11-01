package com.bona;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bona.domain.User;
import com.bona.mapper.UserMapper;
import com.bona.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiroApplicationTests {

	@Autowired
	private UserMapper userService;
	
	@Test
	public void contextLoads() {
		User user=userService.findByName("admin");
		System.out.println(user.toString());
	}

}
