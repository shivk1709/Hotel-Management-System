package com.service.ratings.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.ratings.Dtos.RatingsDto;
import com.service.ratings.daos.RatingsDao;
import com.service.ratings.models.Ratings;
import com.service.ratings.services.RatingsService;

@Service
public class RatingsServiceimpl implements RatingsService {

	@Autowired
	private RatingsDao ratingdDao;
	
	@Override
	public RatingsDto addRatings(RatingsDto ratingDto) {
		// TODO Auto-generated method stub
		return ratingToRatingDto(this.ratingdDao.save(ratingDtoToRatings(ratingDto)));
	}

	@Override
	public List<RatingsDto> getRatings() {
		// TODO Auto-generated method stub
		List<Ratings> ratings = this.ratingdDao.findAll();
		return ratings.stream().map((rating) -> ratingToRatingDto(rating)).collect(Collectors.toList());
	}

	@Override
	public List<RatingsDto> getRatingsByUserId(long userId) {
		// TODO Auto-generated method stub
		List<Ratings> ratingsByUserId = this.ratingdDao.findByUserId(userId);
		return ratingsByUserId.stream().map((rating) -> ratingToRatingDto(rating)).collect(Collectors.toList());
	}

	@Override
	public List<RatingsDto> getRatingsByHotelId(long hotelId) {
		// TODO Auto-generated method stub
		List<Ratings> ratingsByHotelId = this.ratingdDao.findByHotelId(hotelId);
		return ratingsByHotelId.stream().map((rating) -> ratingToRatingDto(rating)).collect(Collectors.toList());
	}
	
	public RatingsDto ratingToRatingDto(Ratings ratings) {
		RatingsDto ratingsDto = new RatingsDto();
		ratingsDto.setId(ratings.getId());
		ratingsDto.setRating(ratings.getRating());
		ratingsDto.setFeedback(ratings.getFeedback());
		ratingsDto.setUserId(ratings.getUserId());
		ratingsDto.setHotelId(ratings.getHotelId());
		return ratingsDto;
	}
	
	public Ratings ratingDtoToRatings(RatingsDto ratingsDto) {
		Ratings ratings = new Ratings();
		ratings.setId(ratingsDto.getId());
		ratings.setUserId(ratingsDto.getUserId());
		ratings.setHotelId(ratingsDto.getHotelId());
		ratings.setFeedback(ratingsDto.getFeedback());
		ratings.setRating(ratingsDto.getRating());
		return ratings;
	}

}
