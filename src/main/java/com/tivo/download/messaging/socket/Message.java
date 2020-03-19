package com.tivo.download.messaging.socket;

public class Message {

	private String message;
	
	public Message(final String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
