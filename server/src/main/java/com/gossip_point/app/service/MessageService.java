package com.gossip_point.app.service;

import java.util.List;

import com.gossip_point.app.entity.Message;
import com.gossip_point.app.entity.User;
import com.gossip_point.app.exception.ChatException;
import com.gossip_point.app.exception.MessageException;
import com.gossip_point.app.exception.UserException;
import com.gossip_point.app.request.SendMessageRequest;

public interface MessageService {

	public Message sendMessage(SendMessageRequest req) throws UserException,ChatException;
	
	public List<Message> getChatMessages(Integer chatId,User reqUser)throws ChatException, UserException;
	
	public Message findMessageById(Integer messageId) throws MessageException;
	
	public void deleteMessage(Integer messageId,User reqUser) throws MessageException, UserException; 
}
