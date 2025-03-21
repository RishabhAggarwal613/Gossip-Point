package com.gossip_point.app.response;

public class ApiResponse {
 
private String  message;
private boolean status;
public ApiResponse(String message, boolean b) {
	super();
	this.message = message;
	this.status = b;
}
}
