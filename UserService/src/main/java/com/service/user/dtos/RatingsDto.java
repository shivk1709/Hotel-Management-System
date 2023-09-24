package com.service.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RatingsDto {
	
	private long id;
	private long userId;
	private long hotelId;
	private int rating;
	private String feedback;
	private HotelDto hotel;

}
