package com.gossip_point.app.service;

import java.util.List;

import com.gossip_point.app.entity.Chat;
import com.gossip_point.app.entity.User;
import com.gossip_point.app.exception.ChatException;
import com.gossip_point.app.exception.UserException;
import com.gossip_point.app.request.GroupChatRequest;

public interface ChatService {
	
	public Chat createChat(User regUser,Integer userId) throws UserException;
	
	public Chat findChatById(Integer chatId)throws ChatException;
	
	public List<Chat> findAllChatByUserId(Integer userId) throws UserException;
	
	public Chat createGroup(GroupChatRequest req,User reqUser) throws UserException;
	
	public Chat addUserToGroup(Integer userId,Integer chatId,User reqUser ) throws ChatException,UserException;
	
	public Chat renameGroup(Integer chatId,String groupName,User reqUser)throws ChatException,UserException;
	
	public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws ChatException, UserException;
	
	public void deleteChat(Integer chatId,Integer userId) throws ChatException,UserException;

	

	

	
}
