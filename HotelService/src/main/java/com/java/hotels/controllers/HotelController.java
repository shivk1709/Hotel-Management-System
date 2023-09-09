package com.java.hotels.controllers;

import java.util.List;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.java.hotels.dtos.HotelDto;
import com.java.hotels.services.HotelService;

@RestController
public class HotelController {
	
	@Autowired
	private HotelService hotelService;
	
	@PostMapping("/add-hotel")
	public ResponseEntity<HotelDto> addhotel(@RequestBody HotelDto hotelDto){
		return new ResponseEntity<HotelDto>(hotelService.addHotel(hotelDto), HttpStatus.OK);
	}
	@GetMapping("get-hotel-by-id/{id}")
	public ResponseEntity<HotelDto> findHotelById(@PathVariable long id){
		return new ResponseEntity<HotelDto>(this.hotelService.getHotelById(id), HttpStatus.FOUND);
	}
	@GetMapping("all-hotels")
	public ResponseEntity<List<HotelDto>> allHotelDetails(){
		return new ResponseEntity<List<HotelDto>>(this.hotelService.getAllHotelDetails(), HttpStatus.FOUND);
	}
	@PutMapping("update-hotel-by-id/{id}")
	public ResponseEntity<HotelDto> updateHotelById(@RequestBody HotelDto hotelDto,@PathVariable long id){
		return new ResponseEntity<HotelDto>(this.hotelService.updateHotelDetailsById(hotelDto, id), HttpStatus.OK);
	}
	@DeleteMapping("delete-hotel-by-id/{id}")
	public ResponseEntity<String> deleteHotelById(@PathVariable long id){
		return new ResponseEntity<String>(this.hotelService.deleteHotelById(id), HttpStatus.OK);
	}

}
