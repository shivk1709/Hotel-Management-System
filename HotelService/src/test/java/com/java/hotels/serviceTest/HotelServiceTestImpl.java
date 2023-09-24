package com.java.hotels.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import com.java.hotels.daos.HotelDao;
import com.java.hotels.dtos.HotelDto;
import com.java.hotels.models.Hotel;
import com.java.hotels.serviceImpl.HotelServiceImpl;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTestImpl {
	
	@Mock
	private HotelDao hotelDao;
	
	@InjectMocks
	private HotelServiceImpl hotelService;
	
	private Hotel hotel1;
	private Hotel hotel2;
	
	private HotelDto hotelDto1;
	private HotelDto hotelDto2;
	
	@BeforeEach
	void init() {
		hotel1 = Hotel.builder()
				.id(1L)
				.name("Gulshan Dhaba")
				.website("http://gulshanDhaba.com")
				.location("Mathura, Uttar Pradesh")
				.about("Family Restaurant")
				.build();
		
		hotel2 = Hotel.builder()
				.id(2L)
				.name("Jain Dhaba")
				.website("http://jain-dhaba.com")
				.location("Kanpur, Uttar Pradesh")
				.about("Veg Restaurant")
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
		
	}
	
	@Test
	@DisplayName("Save the Hotel")
	public void saveHotel() {
		when(hotelDao.save(any(Hotel.class))).thenReturn(hotel1);
		
		HotelDto hotelDto = hotelService.addHotel(hotelDto1);
		assertNotNull(hotelDto);
		assertThat(hotelDto.getName()).isEqualTo("Gulshan Dhaba");
	}
	
	@Test
	@DisplayName("Should return the Hotels list")
	public void  testToFetchAllHotelDetails() {
		
		List<Hotel> hotels = new ArrayList<>();
		hotels.add(hotel1);
		hotels.add(hotel2);
		
		List<HotelDto> hotelDtos = new ArrayList<>();
		hotelDtos.add(hotelDto1);
		hotelDtos.add(hotelDto2);
		
		when(hotelDao.findAll()).thenReturn(hotels);
		List<HotelDto> listOfHotelDtos = this.hotelService.getAllHotelDetails();
		assertNotNull(listOfHotelDtos);
		assertThat(listOfHotelDtos.size()).isEqualTo(2);
	}
	
	@Test
	@DisplayName("Test to find hotel by Id")
	public void testToFindHotelById() {
		when(hotelDao.findById(anyLong())).thenReturn(Optional.of(hotel1));
		HotelDto existingHotel = hotelService.getHotelById(1L);
		
		assertNotNull(existingHotel);
		assertThat(existingHotel.getId()).isEqualTo(1L);
	}
	
	@Test
	@DisplayName("Test to Update Hotel using Id")
	public void testToUpdatehotelById() {
		when(hotelDao.findById(anyLong())).thenReturn(Optional.of(hotel2));
		when(hotelDao.save(any(Hotel.class))).thenReturn(hotel2);
		hotelDto1.setName("Agrawal Sweets");
		hotelDto1.setLocation("Aligarh, UP");
		hotelDto1.setWebsite("http://agrawal-sweets.com");
		hotelDto1.setAbout("Best Family Restaurant");
		
		HotelDto hotelDto = hotelService.updateHotelDetailsById(hotelDto1, 1L);
		assertNotNull(hotelDto);
		assertEquals("Agrawal Sweets", hotelDto.getName());
	}
	
	@Test
	@DisplayName("Test to delete hotel by Id")
	public void testToDeleteHotelById() {
		when(hotelDao.findById(anyLong())).thenReturn(Optional.of(hotel1));
		doNothing().when(hotelDao).delete(any(Hotel.class));
		
		hotelService.deleteHotelById(1L);
		verify(hotelDao, times(1)).delete(hotel1);
	}

}
