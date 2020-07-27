package com.tivo.download.messaging.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.tivo.download.service.MessagingService;

@Service
@Profile("default")
public class NoopMessageServiceImpl implements MessagingService{

	private static final Logger LOGGER = LoggerFactory.getLogger(NoopMessageServiceImpl.class);
	
	@Override
	public void publishPingMessage(String message) {
		LOGGER.info("Logging event from {}", NoopMessageServiceImpl.class.getName());
	}

}
