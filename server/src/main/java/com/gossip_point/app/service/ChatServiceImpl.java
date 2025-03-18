package com.gossip_point.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gossip_point.app.entity.Chat;
import com.gossip_point.app.entity.User;
import com.gossip_point.app.exception.ChatException;
import com.gossip_point.app.exception.UserException;
import com.gossip_point.app.repository.ChatRepository;
import com.gossip_point.app.request.GroupChatRequest;

@Service
public class ChatServiceImpl implements ChatService {

	
	private ChatRepository chatRepository;
	private UserService userService;
	
	public ChatServiceImpl(ChatRepository chatRepository, UserService userService) {
		super();
		this.chatRepository = chatRepository;
		this.userService = userService;
	}

	@Override
	public Chat createChat(User reqUser, Integer userId) throws UserException {
	
		User user=userService.findUserById(userId);
		Chat isChatExist=chatRepository.findSingleChatByUserId(reqUser, user);
		if(isChatExist!=null) {
			return isChatExist;
		}
		Chat chat=new Chat();
		chat.setCreatedBy(reqUser);
		chat.getUsers().add(user);
		chat.getUsers().add(reqUser);
		chat.setGroup(false);
		
		return chat;
	}

	@Override
	public Chat findChatById(Integer chatId) throws ChatException {
		Optional<Chat> chat =chatRepository.findById(chatId);
		if(chat.isPresent()) {
			return chat.get();
		}
		throw new ChatException("Chat not found with Id"+chatId);
	}

	@Override
	public List<Chat> findAllChatByUserId(Integer userId) throws UserException {
		User user =userService.findUserById(userId);
		List<Chat> chats=chatRepository.findChatByUserId(user.getId());
		return null;
	}

	@Override
	public Chat createGroup(GroupChatRequest req, User reqUser) throws  UserException {
		Chat group=new Chat();
		group.setGroup(true);
		group.setChat_image(req.getChat_image());
		group.setChat_name(req.getChat_name());
		group.setCreatedBy(reqUser);
		group.getUsers().add(reqUser);
		for(Integer userId:req.getUserId()) {
			User user=userService.findUserById(userId);
			group.getUsers().add(user);
		}
		
		return group;
	}

	@Override
	public Chat addUserToGroup(Integer userId, Integer chatId,User reqUser) throws ChatException,UserException {
		Optional<Chat> opt=chatRepository.findById(chatId);
		User user =userService.findUserById(userId);
		
		if(opt.isPresent()) {
			Chat chat =opt.get();
			if(chat.getAdmins().contains(reqUser)) {
				chat.getUsers().add(user);
				return chatRepository.save(chat);
			}
			else {
				throw new UserException("you dont't have access admin section");
			}
			
		}
		
		throw new ChatException("chat not found exception");
	}

	@Override
	public Chat renameGroup(Integer chatId, String groupName, User reqUser) throws ChatException, UserException {
		Optional<Chat> opt=chatRepository.findById(chatId);
		if(opt.isPresent()) {
			Chat chat =opt.get();
			if(chat.getAdmins().contains(reqUser)) {
				chat.setChat_name(groupName);
				return chatRepository.save(chat);
			}
			throw new ChatException("You don't have Authority");
		}
		throw new ChatException("chat not found with id"+chatId);
	}

	@Override
	public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws ChatException, UserException {
	    
		Optional<Chat> opt=chatRepository.findById(chatId);
		User user = userService.findUserById(userId);
		
		if(opt.isPresent()) {
			Chat chat =opt.get();
			if(chat.getAdmins().contains(reqUser)) {
				chat.getUsers().remove(user);
				return chatRepository.save(chat);
			}
			else if(chat.getUsers().contains(reqUser)){

				if(user.getId().equals(reqUser.getId())) {
					chat.getUsers().remove(user);
					
					return chatRepository.save(chat);

				}
			
			}
			throw new UserException("you can't remove other user");	
		}	
			
		throw new ChatException("chat not found exception"+chatId);	
	}
		
		
	



	@Override
	public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
		Optional<Chat> opt=chatRepository.findById(chatId);
		if(opt.isPresent()) {
			Chat chat =opt.get();
			chatRepository.deleteById(chat.getId());
		}
	
	}

	

	
}
