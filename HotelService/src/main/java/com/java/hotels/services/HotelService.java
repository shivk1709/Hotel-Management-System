package com.java.hotels.services;

import java.util.List;

import com.java.hotels.dtos.HotelDto;

public interface HotelService {
	
	HotelDto addHotel(HotelDto hotelDto);
	HotelDto getHotelById(long id);
	List<HotelDto> getAllHotelDetails();
	HotelDto updateHotelDetailsById(HotelDto hotelDto, long id);
	String deleteHotelById(Long id);

}
