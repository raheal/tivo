package com.tivo.download.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tivo.download.dto.Event;
import com.tivo.download.exceptions.ServiceException;

@Service
public class EventServiceImpl implements EventService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);
	
	@Value("${event.logging.service.endpoint}")
	private String eventLoggingEndpoint;
	
	@Value("${event.logging.enabled}")
	private boolean enableEventLogging;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	
	@Override
	public void logEvent(Event event) throws ServiceException {
		if (enableEventLogging) {
			LOGGER.info("Logging event : {}", event);
			ResponseEntity<Boolean> response = restTemplate.postForEntity(eventLoggingEndpoint + "/api/v1/log/event", event, Boolean.class);
			if(response.getStatusCode() != HttpStatus.OK) {
				throw new ServiceException("Cannot log event to Audit Service. Error code : " + response.getStatusCode());
			}
		}
	}

}
