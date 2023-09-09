package com.java.hotels.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.hotels.models.Hotel;

public interface HotelDao extends JpaRepository<Hotel, Long> {

}
