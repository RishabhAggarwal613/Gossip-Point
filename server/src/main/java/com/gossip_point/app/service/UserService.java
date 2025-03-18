package com.gossip_point.app.service;

import java.util.List;

import com.gossip_point.app.entity.User;
import com.gossip_point.app.exception.UserException;
import com.gossip_point.app.request.UpdateUserRequest;

public interface UserService {

	
		public User findUserById(Integer userId) throws UserException;
		
		public User findUserProfile(String jwt) throws UserException;
		 
		public User updateUser(Integer userId,UpdateUserRequest req) throws UserException;
		
		public List<User> searchUser(String query);

		

}
