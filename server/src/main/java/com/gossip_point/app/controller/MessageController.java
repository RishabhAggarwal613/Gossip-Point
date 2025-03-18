package com.gossip_point.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gossip_point.app.entity.Message;
import com.gossip_point.app.entity.User;
import com.gossip_point.app.exception.ChatException;
import com.gossip_point.app.exception.MessageException;
import com.gossip_point.app.exception.UserException;
import com.gossip_point.app.request.SendMessageRequest;
import com.gossip_point.app.response.ApiResponse;
import com.gossip_point.app.service.MessageService;
import com.gossip_point.app.service.UserService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

	private MessageService messageService;
	private UserService userService;
	public MessageController(MessageService messageService, UserService userService) {
		super();
		this.messageService = messageService;
		this.userService = userService;
	}
	@PostMapping("/create")
	public ResponseEntity<Message> sendMessageHandler(@RequestBody SendMessageRequest req,@RequestHeader("Authorization") String jwt) throws UserException, ChatException{
		
		User user=userService.findUserProfile(jwt);
		req.setUserId(user.getId());
		Message message =messageService.sendMessage(req);
		
		return new ResponseEntity<Message>(message,HttpStatus.OK);
		
	}
	@GetMapping("/chat/{chatId}")
	public ResponseEntity<List<Message>> getChatMessageHandler(@PathVariable Integer chatId,@RequestHeader("Authorization") String jwt) throws UserException, ChatException{

		
		User user=userService.findUserProfile(jwt);
		
		List<Message> messages =messageService.getChatMessages(chatId,user);
		
		return new ResponseEntity<>(messages,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{messageId}")
	public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable Integer messageId,@RequestHeader("Authorization") String jwt) throws UserException, ChatException, MessageException{

		
		User user=userService.findUserProfile(jwt);
		
		messageService.deleteMessage(messageId,user);
		ApiResponse res=new ApiResponse("Message deleted Successfully",false);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
		
	}
}
