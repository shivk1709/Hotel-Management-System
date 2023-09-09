package com.service.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RatingsDto {
	
	private long id;
	private long userId;
	private long hotelId;
	private int ratings;
	private String feedback;

}
