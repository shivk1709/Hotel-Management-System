package com.java.hotels.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class HotelDto {
	
	private long id;
	@NotNull
	@Size(min = 4, message = "**Name should be more than 4 characters")
	private String name;
	@NotNull
	@Pattern(regexp = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",message = "**Website URL Should be Proper.")
	private String website;
	@NotNull
	@Size(min = 5, message = "**Please give complete Location.")
	private String location;
	@NotNull
	@Size(min = 4, message = "**Description too short." )
	@Size(max = 100, message = "**Description too long.")
	private String about;

}
