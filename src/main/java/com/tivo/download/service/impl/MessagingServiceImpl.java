package com.tivo.download.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.tivo.download.messaging.kafka.MessageHandler;
import com.tivo.download.service.MessagingService;

@Service
@Profile("eventProfile")
public class MessagingServiceImpl implements MessagingService{

	@Value("${kafka.topic}")
	private String topic;
	
	@Autowired
	private MessageHandler messageHandler;
	
	@Override
	public void publishPingMessage(final String message) {
		messageHandler.send(topic, message);
	}

}
