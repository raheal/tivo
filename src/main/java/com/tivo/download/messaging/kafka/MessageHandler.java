package com.tivo.download.messaging.kafka;

public interface MessageHandler {

	void send(final String topic, final Long key, final String value);
	
	void send(final String topic, final String value);
	
}
