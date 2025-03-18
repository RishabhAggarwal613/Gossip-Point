package com.gossip_point.app.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gossip_point.app.entity.User;
import com.gossip_point.app.exception.UserException;

import com.gossip_point.app.request.UpdateUserRequest;
import com.gossip_point.app.response.ApiResponse;
import com.gossip_point.app.service.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {

	private UserService userService;
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
 
	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String token) throws UserException{
	
		User user=userService.findUserProfile(token);
		return new ResponseEntity<User> (user,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{query}")
	public ResponseEntity<List<User>> searchUserHandler(@PathVariable String query){
		
		List<User> user =userService.searchUser(query);
		
		return new ResponseEntity<List<User>>(user,HttpStatus.OK);
			
		}

	@PutMapping("/update")
	public ResponseEntity<ApiResponse>updateUserHandler(@RequestBody UpdateUserRequest req,@RequestHeader("Authorization") String token) throws UserException {
		 
		User user=userService.findUserProfile(token);
		userService.updateUser(user.getId(),req);
		ApiResponse res=new ApiResponse("user Updated successfully",true);
		return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
		
	}
	
	
}
