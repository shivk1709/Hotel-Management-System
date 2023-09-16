package com.service.user.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.service.user.dtos.HotelDto;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelService {
	
	@GetMapping("/get-hotel-by-id/{id}")
	HotelDto getHotelById(@PathVariable("id") long hotelId); 

}
