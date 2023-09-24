package com.service.ratings.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.service.ratings.Dtos.RatingsDto;
import com.service.ratings.daos.RatingsDao;
import com.service.ratings.models.Ratings;
import com.service.ratings.serviceImpl.RatingsServiceimpl;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTestImpl {
	@Mock
	private RatingsDao ratingsDao;
	@InjectMocks
	private RatingsServiceimpl ratingsService;
	
	private Ratings rating1;
	private Ratings rating2;
	
	private RatingsDto ratingDto1;
	private RatingsDto ratingDto2;
	
	@BeforeEach
	public void init() {
		rating1 = Ratings.builder()
				.id(1L)
				.userId(1L)
				.hotelId(1L)
				.rating(8)
				.feedback("Nice Ratings")
				.build();
		
		rating2 = Ratings.builder()
				.id(2L)
				.userId(1L)
				.hotelId(2L)
				.rating(9)
				.feedback("Very Nice Ratings")
				.build();
		
		ratingDto1 = RatingsDto.builder()
				.id(1L)
				.userId(1L)
				.hotelId(1L)
				.rating(8)
				.feedback("Nice Rating")
				.build();
		
		ratingDto2 = RatingsDto.builder()
				.id(2L)
				.userId(1L)
				.hotelId(1L)
				.rating(9)
				.feedback("Very Nice Ratings")
				.build();
	}
	
	@Test
	public void testToSaveTheRatings() {
		when(ratingsDao.save(any(Ratings.class))).thenReturn(rating1);
		RatingsDto ratingsDto = ratingsService.addRatings(ratingDto1);
		
		assertNotNull(ratingsDto);
		assertThat(ratingsDto.getFeedback()).isEqualTo("Nice Ratings");
		
	}
	
	@Test
	public void testToFetchAllRatings() {
		List<Ratings> listOfRatings = new ArrayList<>();
		listOfRatings.add(rating1);
		listOfRatings.add(rating2);
		
		List<RatingsDto> listOfRatingsDto = new ArrayList<>();
		listOfRatingsDto.add(ratingDto1);
		listOfRatingsDto.add(ratingDto2);
		
		when(ratingsDao.findAll()).thenReturn(listOfRatings);
		List<RatingsDto> listOfRatingsDtos = ratingsService.getRatings();
		assertNotNull(listOfRatingsDtos);
		assertEquals(listOfRatingsDtos.size(), 2);
	}
	
	@Test
	public void testToFindRatingByUserId() {
		List<Ratings> listOfRatings = new ArrayList<>();
		listOfRatings.add(rating1);
		listOfRatings.add(rating2);
		
		when(ratingsDao.findByUserId(anyLong())).thenReturn(listOfRatings);
		List<RatingsDto> listOfRatingsDtos = ratingsService.getRatingsByUserId(1L);
		assertNotNull(listOfRatingsDtos);
		assertEquals(listOfRatingsDtos.size(), 2);
	}
	
	@Test
	public void testOfFindRatingsByHotelId() {
		List<Ratings> listOfRatings = new ArrayList<>();
		listOfRatings.add(rating1);
		listOfRatings.add(rating2);
		
		when(ratingsDao.findByHotelId(anyLong())).thenReturn(listOfRatings);
		List<RatingsDto> listOfRatingsDtos = ratingsService.getRatingsByHotelId(1L);
		assertNotNull(listOfRatingsDtos);
		assertEquals(listOfRatingsDtos.size(), 2);
	}
	
	

}
