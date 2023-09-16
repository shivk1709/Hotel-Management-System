package com.service.user.services;

import java.util.List;

import com.service.user.dtos.UserDto;

/**
 * @author shivk
 * UserService Interface
 */

public interface UserServiceInterface {
	
	UserDto addUser(UserDto userDto);
	UserDto getUserById(long id);
	List<UserDto> getAllUser();
	UserDto updateUser(long id, UserDto userDto);
	void deleteUserById(long id);
	UserDto getUserByIdUsingFeignClient(long id);

}
