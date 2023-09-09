package com.java.hotels.exceptions;


public class ResourceNotFoundException extends RuntimeException {

	private String errorMessage;
	private long id;
	
	public ResourceNotFoundException(String errorMessage, long id) {
		super(String.format("%s:%s", errorMessage, id));
		this.errorMessage = errorMessage;
		this.id = id;
	}
	
	
}
