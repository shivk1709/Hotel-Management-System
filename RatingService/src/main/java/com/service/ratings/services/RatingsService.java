package com.service.ratings.services;

import java.util.List;

import com.service.ratings.Dtos.RatingsDto;

public interface RatingsService {
	
	RatingsDto addRatings(RatingsDto ratingDto);
	List<RatingsDto> getRatings();
	List<RatingsDto> getRatingsByUserId(long userId);
	List<RatingsDto> getRatingsByHotelId(long hotelId);
}
