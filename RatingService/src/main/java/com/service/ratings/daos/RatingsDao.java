package com.service.ratings.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.ratings.models.Ratings;

public interface RatingsDao extends JpaRepository<Ratings, Long> {

	List<Ratings> findByUserId(long userId);
	List<Ratings> findByHotelId(long hotelId);
}
