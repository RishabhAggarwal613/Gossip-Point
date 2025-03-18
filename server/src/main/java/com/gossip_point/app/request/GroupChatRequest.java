package com.gossip_point.app.request;

import java.util.List;

public class GroupChatRequest {

	private List<Integer> userId;
	private String chat_name;
	private String chat_image;
	
	
	public GroupChatRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public GroupChatRequest(List<Integer> userId, String chat_name, String chat_image) {
		super();
		this.userId = userId;
		this.chat_name = chat_name;
		this.chat_image = chat_image;
	}

	public List<Integer> getUserId() {
		return userId;
	}
	public void setUserId(List<Integer> userId) {
		this.userId = userId;
	}
	public String getChat_name() {
		return chat_name;
	}
	public void setChat_name(String chat_name) {
		this.chat_name = chat_name;
	}
	public String getChat_image() {
		return chat_image;
	}
	public void setChat_image(String chat_image) {
		this.chat_image = chat_image;
	}
	
	
}
