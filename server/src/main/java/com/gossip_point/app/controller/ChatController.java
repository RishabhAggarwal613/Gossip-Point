package com.gossip_point.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gossip_point.app.entity.Chat;
import com.gossip_point.app.entity.User;
import com.gossip_point.app.exception.ChatException;
import com.gossip_point.app.exception.UserException;
import com.gossip_point.app.request.GroupChatRequest;
import com.gossip_point.app.request.SingleChatRequest;
import com.gossip_point.app.response.ApiResponse;
import com.gossip_point.app.service.ChatService;
import com.gossip_point.app.service.UserService;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

	private ChatService chatService;
	private UserService userService;
	public ChatController(ChatService chatService, UserService userService) {
		super();
		this.chatService = chatService;
		this.userService = userService;
	}
	
	@PostMapping("/single")
	public ResponseEntity<Chat> createChatHandler(@RequestBody SingleChatRequest singleChatRequest,@RequestHeader("Authorization")String jwt) throws UserException{
 		
		User reqUser=userService.findUserProfile(jwt);
		Chat chat=chatService.createChat(reqUser, singleChatRequest.getUserId());
		return new ResponseEntity<Chat>(chat,HttpStatus.OK);
	}
	
	@PostMapping("/group")
	public ResponseEntity<Chat> createGroupHandler(@RequestBody GroupChatRequest groupChatRequest,@RequestHeader("Authorization")String jwt) throws UserException{
 		
		User reqUser=userService.findUserProfile(jwt);
		Chat chat=chatService.createGroup(groupChatRequest,reqUser);
		return new ResponseEntity<Chat>(chat,HttpStatus.OK);
	}
	
	@GetMapping("/{chatId}")
	public ResponseEntity<Chat> findChatByIdHandler(@PathVariable Integer chatId ,@RequestHeader("Authorization")String jwt) throws UserException, ChatException{
		
		Chat chat=chatService.findChatById(chatId);
		return new ResponseEntity<Chat>(chat,HttpStatus.OK);
	}
	@GetMapping("/user")
	public ResponseEntity<List<Chat>> findAllChatByUserIdHandler(@RequestHeader("Authorization")String jwt) throws UserException{
 		
		User reqUser=userService.findUserProfile(jwt);
		List<Chat> chat=chatService.findAllChatByUserId(reqUser.getId());
		return new ResponseEntity<List<Chat>>(chat,HttpStatus.OK);
	}
	
	@PutMapping("/{chatId}/add/{userId}")
	public ResponseEntity<Chat> addUserToGroupHandler(@PathVariable Integer chatId,@PathVariable Integer userId, @RequestHeader("Authorization")String jwt) throws UserException, ChatException{
 		
		User reqUser=userService.findUserProfile(jwt);
		Chat chat=chatService.addUserToGroup(chatId,userId,reqUser);
		return new ResponseEntity<>(chat,HttpStatus.OK);
	}
	@PutMapping("/{chatId}/remove/{userId}")
	public ResponseEntity<Chat> removeUserFromGroupHandler(@PathVariable Integer chatId,@PathVariable Integer userId, @RequestHeader("Authorization")String jwt) throws UserException, ChatException{
 		
		User reqUser=userService.findUserProfile(jwt);
		Chat chat=chatService.removeFromGroup(chatId,userId,reqUser);
		return new ResponseEntity<>(chat,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{chatId}")
	public ResponseEntity<ApiResponse> deleteChatHandler(@PathVariable Integer chatId, @RequestHeader("Authorization")String jwt) throws UserException, ChatException{
 		
		User reqUser=userService.findUserProfile(jwt);
		chatService.deleteChat(chatId,reqUser.getId());
		ApiResponse res=new ApiResponse("Chat is deleted Successfully", true);
		return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);
	}
	
}
