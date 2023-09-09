package com.service.ratings.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class RatingsDto {

	private long id;
	private long userId;
	private long hotelId;
	private String feedback;
	private int rating;

}
