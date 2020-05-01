package com.tivo.download.service;

import com.tivo.download.exceptions.ServiceException;
import com.tivo.download.model.Event;

public interface EventService {

	void logEvent(final Event event) throws ServiceException;
	
}
