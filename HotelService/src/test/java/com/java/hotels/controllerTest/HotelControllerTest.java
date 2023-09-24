package com.java.hotels.controllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.hotels.controllers.HotelController;
import com.java.hotels.dtos.HotelDto;
import com.java.hotels.services.HotelService;

@WebMvcTest
public class HotelControllerTest {
	
	@MockBean
	private HotelService hotelService;
	@InjectMocks
	private HotelController hotelController;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	private HotelDto hotelDto1;
	private HotelDto hotelDto2;


	
	@BeforeEach
	void init() {
		
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
	public void testToGetTheHotelById() throws Exception {
		when(hotelService.getHotelById(anyLong())).thenReturn(hotelDto1);
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/get-hotel-by-id/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Gulshan Dhaba"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.website").value( "http://gulshanDhaba.com"));
		
		verify(hotelService).getHotelById(1L);
	}
	
	@Test
	public void testToGetListOfHotels() throws Exception{
		List<HotelDto> hotels = new ArrayList<>();
		hotels.add(hotelDto1);
		hotels.add(hotelDto2);
		when(hotelService.getAllHotelDetails()).thenReturn(hotels);
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/all-hotels")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(hotels.size()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("Gulshan Dhaba"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].website").value("http://gulshanDhaba.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value("Jain Dhaba"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[1].website").value("http://jain-dhaba.com"));
		verify(hotelService).getAllHotelDetails();
	}
	
	@Test
	public void testToAddHotelById() throws Exception {
		when(hotelService.addHotel(any(HotelDto.class))).thenReturn(hotelDto1);
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/add-hotel")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(hotelDto1)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Gulshan Dhaba"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.website").value( "http://gulshanDhaba.com"));
		
	}
	
	@Test
	public void testToUpdateTheHotelUsingId() throws Exception {
		when(hotelService.updateHotelDetailsById(any(HotelDto.class), anyLong())).thenReturn(hotelDto2);
		
		this.mockMvc.perform(MockMvcRequestBuilders.put("/update-hotel-by-id/{id}", 2L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(hotelDto2)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jain Dhaba"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.website").value("http://jain-dhaba.com"));
	}
	
	@Test
	public void testToDeletehotelById() throws Exception{
		when(hotelService.deleteHotelById(anyLong())).thenReturn("Deleted Successfully");
		
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/delete-hotel-by-id/{id}", 1L))
		.andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("Deleted Successfully"));
		
		verify(hotelService).deleteHotelById(1L);

	}


}
