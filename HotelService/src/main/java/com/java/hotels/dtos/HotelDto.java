package com.java.hotels.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class HotelDto {
	
	private long id;
	private String name;
	private String website;
	private String location;
	private String about;

}
