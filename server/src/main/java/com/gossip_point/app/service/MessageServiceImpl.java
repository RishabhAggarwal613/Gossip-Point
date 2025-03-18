package com.gossip_point.app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gossip_point.app.entity.Chat;
import com.gossip_point.app.entity.Message;
import com.gossip_point.app.entity.User;
import com.gossip_point.app.exception.ChatException;
import com.gossip_point.app.exception.MessageException;
import com.gossip_point.app.exception.UserException;
import com.gossip_point.app.repository.MessageRepository;
import com.gossip_point.app.request.SendMessageRequest;

@Service 
public class MessageServiceImpl implements MessageService {

	private MessageRepository messageRepository;
	private UserService userService;
	private ChatService chatService;
	

	public MessageServiceImpl(MessageRepository messageRepository, UserService userService, ChatService chatService) {
		super();
		this.messageRepository = messageRepository;
		this.userService = userService;
		this.chatService = chatService;
	}

	@Override
	public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {
		User user=userService.findUserById(req.getUserId());
		Chat chat=chatService.findChatById(req.getChatId());
		
		Message message=new Message();
		message.setChat(chat);
		message.setUser(user);
		message.setContent(req.getContent());
		message.setTimeStamp(LocalDateTime.now());
		
		return message;
	}

	@Override
	public List<Message> getChatMessages(Integer chatId,User reqUser) throws ChatException, UserException {
		Chat chat =chatService.findChatById(chatId);
		if(!chat.getUsers().contains(reqUser)) {
			throw new UserException("you can't access private messages"+chatId);
		}
		List<Message> messages=messageRepository.findByChatId(chat.getId());
		
		return messages;
	}

	@Override
	public Message findMessageById(Integer messageId) throws MessageException {
		Optional<Message> opt=messageRepository.findById(messageId);
	
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new MessageException("Message not found by id"+messageId);
	}

	@Override
	public void deleteMessage(Integer messageId,User reqUser) throws MessageException, UserException {
		
		Message message=findMessageById(messageId);
		if(message.getUser().getId().equals(reqUser.getId())) {
			messageRepository.deleteById(messageId);
		}
		throw new UserException("You can't delete this message");
	}

	
}
