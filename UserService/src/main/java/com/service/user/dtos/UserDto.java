package com.service.user.dtos;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author shivk
 * User Data transfer Object to perform operations on User at different layers
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	private Long id;
	@NotNull
	@Size(min = 4, max = 12)
	private String name;
	@NotNull
	@Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Email should be Valid")
	private String email;
	@NotNull
	@Size(min = 5, message = "Password should be more than 5 Characters")
	private String password;
	@NotNull
	private String address;
	
	private ArrayList<RatingsDto> ratings;

}
