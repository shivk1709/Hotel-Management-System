package com.service.ratings.Dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class RatingsDto {

	private long id;
	@NotNull
	private long userId;
	@NotNull
	private long hotelId;
	@NotNull
	@Size(min = 4, max = 100, message = "**Please write properly")
	private String feedback;
	@NotNull
	@Min(value = 0, message = "**Please use positive integers")
	@Max(value = 10, message = "**Please rate out of 10")
	private int rating;
	
}
