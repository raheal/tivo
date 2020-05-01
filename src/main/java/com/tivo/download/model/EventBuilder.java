package com.tivo.download.model;

import java.time.LocalDateTime;

public class EventBuilder {

	private String eventName;
	
	private String eventPayload;
	
	private String serviceName;
	
	public EventBuilder() {}
	
	public EventBuilder setEventName(final String eventName) {
		this.eventName = eventName;
		return this;
	}
	
	public EventBuilder setEventPayload(final String eventPayload) {
		this.eventPayload = eventPayload;
		return this;
	}
	
	public EventBuilder setServiceName(final String serviceName) {
		this.serviceName = serviceName;
		return this;
	}
	
	public Event build() {
		final Event event = new Event();
		event.setEventName(eventName);
		event.setPayload(eventPayload);
		event.setServiceName(serviceName);
		event.setEventTime(LocalDateTime.now());
		return event;
	}
	
}
