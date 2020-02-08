package com.tivo.download.dto;

import java.time.LocalDateTime;

public class EventBuilder {

	private String eventName;
	
	private String eventPayload;
	
	private LocalDateTime eventLogTime;
	
	public EventBuilder() {}
	
	public EventBuilder setEventName(final String eventName) {
		this.eventName = eventName;
		return this;
	}
	
	public EventBuilder setEventPayload(final String eventPayload) {
		this.eventPayload = eventPayload;
		return this;
	}
	
	public Event build() {
		final Event event = new Event();
		event.setEventName(eventName);
		event.setPayload(eventPayload);
		event.setEventTime(LocalDateTime.now());
		return event;
	}
	
}
