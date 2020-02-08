package com.tivo.download.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tivo.download.dto.Event;

@Service
public class EventServiceImpl implements EventService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);
	
	@Override
	public void logEvent(Event event) {
		// send message to kafka topic and log in the audit service
		LOGGER.info("Logging event : {}", event);
	}

}
