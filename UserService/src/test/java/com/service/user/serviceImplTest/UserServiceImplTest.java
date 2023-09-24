package com.service.user.serviceImplTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.service.user.Daos.UserDao;
import com.service.user.dtos.HotelDto;
import com.service.user.dtos.RatingsDto;
import com.service.user.dtos.UserDto;
import com.service.user.models.User;
import com.service.user.serviceImpls.UserServiceImpl;
import com.service.user.services.HotelService;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
	@Mock
	private UserDao userDao;
	@Mock
	private RestTemplate restTemplate;
	@Mock
	private HotelService hotelService;

	@InjectMocks
	private UserServiceImpl userService;
	
	
	private User user1;
	private User user2;
	
	private UserDto userDto1;
	private UserDto userDto2;
	private HotelDto hotelDto1;
	private HotelDto hotelDto2;
	private RatingsDto ratingDto1;
	private RatingsDto ratingDto2;
	private RatingsDto[] ratings;
	private List<RatingsDto> ratingsOfHotels=new ArrayList<>();
	
	@BeforeEach
	void init() {
		user1 = User.builder()
				.id(1L)
				.name("Fake User 1")
				.email("fake1@gmail.com")
				.password("1234")
				.address("Fake Address 1")
				.build();
		
		user2 = User.builder()
				.id(2L)
				.name("Fake User 2")
				.email("fake2@gmail.com")
				.password("1234")
				.address("Fake Adress 2")
				.build();
		
		userDto1 = UserDto.builder()
				.id(1L)
				.name("Fake UserDto 1")
				.email("fake1@gmail.com")
				.password("1234")
				.address("Fake Adress of UserDto 1")
				.ratings(ratingsOfHotels)
				.build();
		
		userDto2 = UserDto.builder()
				.id(2L)
				.name("Fake UserDto 2")
				.email("fake2@gmail.com")
				.password("1234")
				.ratings(ratingsOfHotels)
				.address("Fake Adress of UserDto 2")
				.build();
		
		hotelDto1 = HotelDto.builder()
				.id(1L)
				.name("Gulshan Dhaba")
				.website("http://gulshanDhaba.com")
				.location("Mathura, Uttar Pradesh")
				.about("Family Restaurant")
				.build();
		
		hotelDto2 = HotelDto.builder()
				.id(2L)
				.name("Jain Dhaba")
				.website("http://jain-dhaba.com")
				.location("Kanpur, Uttar Pradesh")
				.about("Veg Restaurant")
				.build();
		
		ratingDto1 = RatingsDto.builder()
				.id(1L)
				.userId(1L)
				.hotelId(1L)
				.rating(8)
				.feedback("Nice Rating")
				.hotel(hotelDto1)
				.build();
		
		ratingDto2 = RatingsDto.builder()
				.id(2L)
				.userId(1L)
				.hotelId(1L)
				.rating(9)
				.hotel(hotelDto2)
				.feedback("Very Nice Ratings")
				.build();
		
		ratingsOfHotels.add(ratingDto1);
		ratingsOfHotels.add(ratingDto2);
				
		ratings = new RatingsDto[10];
		ratings[0] = ratingDto1;
		ratings[1]= ratingDto2;
		
       	}
	
	@Test
	@DisplayName("Function to Get the User information along with Hotel and Rating using RestTemplate")
	public void testToGetUserById() {
		
		long userId = 1L;
		when(userDao.findById(anyLong())).thenReturn(Optional.of(user1));
		
		when(restTemplate.getForObject(
                "http://RATING-SERVICE/ratings-by-userId/" + userId,
                RatingsDto[].class))
                .thenReturn(ratings);
		
		ResponseEntity<HotelDto> hotelResponse = new ResponseEntity<>(hotelDto1, HttpStatus.OK);
		when(restTemplate.getForEntity(
                "http://HOTEL-SERVICE/get-hotel-by-id/1",
                HotelDto.class))
                .thenReturn(hotelResponse);
		
		UserDto userDto = userService.getUserById(userId);
		assertNotNull(userDto);
		assertEquals(2, userDto.getRatings().size());
	    assertEquals(1L, userDto.getRatings().get(0).getHotel().getId());

	}
	
	@Test
	@DisplayName("Function to Get the User information along with Hotel and Rating using Fiegn Client")
	public void testToGetUserByIdUsingFeignClient() {
		
		long userId = 1L;
		when(userDao.findById(anyLong())).thenReturn(Optional.of(user1));
		
		when(restTemplate.getForObject(
                "http://RATING-SERVICE/ratings-by-userId/" + userId,
                RatingsDto[].class))
                .thenReturn(ratings);
		
		when(hotelService.getHotelById(anyLong())).thenReturn(hotelDto1);
		
		UserDto userDto = userService.getUserByIdUsingFeignClient(userId);
		assertEquals(2, userDto.getRatings().size());
		assertNotNull(userDto);
	    assertEquals(1L, userDto.getRatings().get(0).getHotel().getId());

	}
	@Test
	@DisplayName("Test to get All the users")
	public void testToGetAllUsers() {
		List<User> listOfUsers = new ArrayList<>();
		listOfUsers.add(user1);
		listOfUsers.add(user2);
		when(userDao.findAll()).thenReturn(listOfUsers);
		
		List<UserDto> userDtos = userService.getAllUser();
		assertEquals(2, userDtos.size());
		assertEquals("Fake User 1", userDtos.get(0).getName());
	}
	
	@Test
	@DisplayName("Test to save the User")
	public void testToSaveUser() {
		when(userDao.save(any(User.class))).thenReturn(user2);
		UserDto userDto = userService.addUser(userDto2);
		
		assertEquals("Fake User 2", userDto.getName());
	}
	
	@Test
	@DisplayName("Test to update the user")
	public void testToUpdateTheUser() {
		when(userDao.findById(anyLong())).thenReturn(Optional.of(user1));
		when(userDao.save(any(User.class))).thenReturn(user1);
		userDto1.setName("New User");
		UserDto updatedUser = userService.updateUser(1L, userDto1);
		
		assertEquals("New User", updatedUser.getName());
	}

}
