package com.service.ratings.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.service.ratings.Dtos.RatingsDto;
import com.service.ratings.services.RatingsService;

@RestController
public class RatingsController {

	@Autowired
	private RatingsService ratingsService;

	@PostMapping("/add-rating")
	public ResponseEntity<RatingsDto> addRatings(@RequestBody RatingsDto ratingsDto) {
		return new ResponseEntity<RatingsDto>(this.ratingsService.addRatings(ratingsDto), HttpStatus.OK);
	}

	@GetMapping("/ratings")
	public ResponseEntity<List<RatingsDto>> allRatings() {
		return new ResponseEntity<List<RatingsDto>>(this.ratingsService.getRatings(), HttpStatus.OK);
	}

	@GetMapping("/ratings-by-userId/{id}")
	public ResponseEntity<List<RatingsDto>> ratingsByUserId(@PathVariable(name = "id") long userId) {
		return new ResponseEntity<List<RatingsDto>>(this.ratingsService.getRatingsByUserId(userId), HttpStatus.OK);
	}
	@GetMapping("/ratings-by-hotelId/{id}")
	public ResponseEntity<List<RatingsDto>> ratingsByHotelId(@PathVariable(name = "id") long hotelId){
		return new ResponseEntity<List<RatingsDto>>(this.ratingsService.getRatingsByHotelId(hotelId), HttpStatus.OK);
	}

}
