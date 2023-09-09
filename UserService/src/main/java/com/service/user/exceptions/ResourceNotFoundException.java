package com.service.user.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
	
	private String error;
	private Long id;
	
	public ResourceNotFoundException(String error, Long id) {
		super(String.format("%s : %s", error,id));
		this.error = error;
		this.id = id;
	}
	
	
	
	

}
