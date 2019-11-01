package com.bona.service;

import com.bona.domain.User;

public interface UserService {
	public User findByName(String name);
	User findById(Integer id);

}
