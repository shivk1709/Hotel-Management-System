package com.service.user.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.service.user.dtos.UserDto;
import com.service.user.services.UserServiceInterface;

import jakarta.validation.Valid;
import lombok.val;

/**
 * @author shivk
 * User Controller of User service
 */

@RestController
public class UserController {

	@Autowired
	private UserServiceInterface userServiceInterface;
	
	/**
	 * 
	 * @param userDto
	 * @return userDto after adding the user.
	 */
	@PostMapping("/add-user")
	public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto){
		return new ResponseEntity<UserDto>(userServiceInterface.addUser(userDto), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param id
	 * @return user detail of the specific id
	 */
	@GetMapping("/find-user-By-Id/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable long id){
		return new ResponseEntity<UserDto>(userServiceInterface.getUserById(id), HttpStatus.OK);
	}
	
	@GetMapping("/getUser-by-id-feign/{id}")
	public ResponseEntity<UserDto> getUserByIdUsingFeignClient(@PathVariable long id){
		return new ResponseEntity<UserDto>(userServiceInterface.getUserByIdUsingFeignClient(id), HttpStatus.OK);
	}
	/**
	 * 
	 * @return list of all the user with all the fields
	 */
	@GetMapping("find-all-users")
	public ResponseEntity<List<UserDto>> allusersDetails(){
		return new ResponseEntity<List<UserDto>>(userServiceInterface.getAllUser(), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param userDto
	 * @param id
	 * @return user after updating the specific user
	 */
	@PutMapping("update-by-id/{id}")
	public ResponseEntity<UserDto> updateUserById(@Valid @RequestBody UserDto userDto, @PathVariable long id){
		return new ResponseEntity<UserDto>(userServiceInterface.updateUser(id, userDto), HttpStatus.OK);
	}
	/**
	 * 
	 * @param id
	 * @return message after performing the operation
	 */
	@DeleteMapping("delete-user-by-id/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable long id){
		return new ResponseEntity<String>("User Deleted Successfully", HttpStatus.OK);
	}
}
