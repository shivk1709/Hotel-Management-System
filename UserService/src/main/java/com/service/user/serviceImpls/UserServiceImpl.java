package com.service.user.serviceImpls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.service.user.Daos.UserDao;
import com.service.user.dtos.HotelDto;
import com.service.user.dtos.RatingsDto;
import com.service.user.dtos.UserDto;
import com.service.user.exceptions.ResourceNotFoundException;
import com.service.user.models.User;
import com.service.user.services.HotelService;
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
	
	@Autowired
	private HotelService hotelService;
	
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
		RatingsDto[] ratings = restTemplate.getForObject("http://RATING-SERVICE/ratings-by-userId/"+findUserById.getId(), RatingsDto[].class);
		logger.info("{}", ratings);
		List<RatingsDto> ratingsByUser = Arrays.stream(ratings).toList();
		List<RatingsDto> ratingList = ratingsByUser.stream().map(rating -> {
			ResponseEntity<HotelDto> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/get-hotel-by-id/"+rating.getHotelId(), HotelDto.class);
			HotelDto hotelDto = forEntity.getBody();
			logger.info("Status code{}", forEntity.getStatusCode());
			rating.setHotel(hotelDto);
			return rating;
		}).collect(Collectors.toList());
		UserDto userDto = userToUserDto(findUserById);
		userDto.setRatings(ratingList);
//		userDto.setRatings(ratings);
		return userDto;
	}
	
	@Override
	public UserDto getUserByIdUsingFeignClient(long id) {
		// TODO Auto-generated method stub
		User findUserById = this.userDao.findById(id).
				orElseThrow(()-> new ResourceNotFoundException("User not found for the id", Long.valueOf(id)));
//		http://localhost:8083/ratings-by-userId/3
		RatingsDto[] ratings = restTemplate.getForObject("http://RATING-SERVICE/ratings-by-userId/"+findUserById.getId(), RatingsDto[].class);
		List<RatingsDto> ratingsByUser = Arrays.stream(ratings).toList();
		List<RatingsDto> ratingList = ratingsByUser.stream().map(rating -> {
			logger.info("{}", rating);
			HotelDto hotelDto = hotelService.getHotelById(rating.getHotelId());
//			ResponseEntity<HotelDto> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/get-hotel-by-id/"+rating.getHotelId(), HotelDto.class);
//			HotelDto hotelDto = forEntity.getBody();
//			logger.info("Status code{}", forEntity.getStatusCode());
			rating.setHotel(hotelDto);
			return rating;
		}).collect(Collectors.toList());
		UserDto userDto = userToUserDto(findUserById);
		userDto.setRatings(ratingList);
//		userDto.setRatings(ratings);
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
