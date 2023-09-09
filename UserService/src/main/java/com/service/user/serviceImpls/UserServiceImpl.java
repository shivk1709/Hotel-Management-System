package com.service.user.serviceImpls;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.service.user.Daos.UserDao;
import com.service.user.dtos.RatingsDto;
import com.service.user.dtos.UserDto;
import com.service.user.exceptions.ResourceNotFoundException;
import com.service.user.models.User;
import com.service.user.services.UserServiceInterface;

/**
 * @author shivk
 * Implementation Class of UserServiceInterface
 */

@Service
public class UserServiceImpl implements UserServiceInterface {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	/**
	 * @param userDto
	 * @return user which is recently added
	 */
	@Override
	public UserDto addUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user = this.userDao.save(userDtoTouser(userDto));
		return userToUserDto(user);
	}

	/**
	 * @param id
	 * @return user having specific id 
	 */
	@Override
	public UserDto getUserById(long id) {
		// TODO Auto-generated method stub
		User findUserById = this.userDao.findById(id).
				orElseThrow(()-> new ResourceNotFoundException("User not found for the id", Long.valueOf(id)));
//		http://localhost:8083/ratings-by-userId/3
		ArrayList<RatingsDto> ratings = restTemplate.getForObject("http://localhost:8083/ratings-by-userId/3", ArrayList.class);
		logger.info("{}", ratings);
		UserDto userDto = userToUserDto(findUserById);
		userDto.setRatings(ratings);
		return userDto;
	}
	
	/**
	 * @return list of all the users details
	 */
	@Override
	public List<UserDto> getAllUser() {
		// TODO Auto-generated method stub
		List<User> allUsers = this.userDao.findAll();
		return allUsers.stream().map(a -> userToUserDto(a)).collect(Collectors.toList());
	}

	/**
	 * @param id
	 * @param userDto
	 * @return user after updating a specific user by id
	 */
	@Override
	public UserDto updateUser(long id, UserDto userDto) {
		// TODO Auto-generated method stub
		User user = this.userDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for the id", Long.valueOf(id)));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAddress(userDto.getAddress());
		return userToUserDto(userDao.save(user));
	}

	/**
	 * @return the message after deleting user by id
	 */
	@Override
	public void deleteUserById(long id) {
		// TODO Auto-generated method stub
		userDao.deleteById(id);

	}

	/**
	 * 
	 * @param user
	 * @return this method will map the user into userDto
	 */
	public UserDto userToUserDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		userDto.setAddress(user.getAddress());
		return userDto;
	}

	/**
	 * 
	 * @param userData
	 * @return this method will map the userDto to user
	 */
	public User userDtoTouser(UserDto userData) {
		User user = new User();
		user.setId(userData.getId());
		user.setName(userData.getName());
		user.setEmail(userData.getEmail());
		user.setPassword(userData.getPassword());
		user.setAddress(userData.getAddress());
		return user;
	}

}
