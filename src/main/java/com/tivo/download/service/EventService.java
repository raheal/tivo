package com.tivo.download.service;

import com.tivo.download.dto.Event;
import com.tivo.download.exceptions.ServiceException;

public interface EventService {

	void logEvent(final Event event) throws ServiceException;
	
}
