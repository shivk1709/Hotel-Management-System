package com.java.hotels.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handlerMethodOResourceNotoundException(ResourceNotFoundException ex){
		HashMap<String, Object> map = new HashMap<>();
		map.put("message", ex.getMessage());
		map.put("Success", false);
		map.put("Status", HttpStatus.NOT_FOUND);
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_FOUND);
		
	}
}
