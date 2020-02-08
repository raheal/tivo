package com.tivo.download.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Event {

	private String eventName;
	
	private String payload;
	
	private LocalDateTime eventTime;
	
}
