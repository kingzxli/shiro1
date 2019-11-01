package com.bona.mapper;

import com.bona.domain.User;

public interface UserMapper {
	User findByName(String name);
	User findById(Integer id);

}
