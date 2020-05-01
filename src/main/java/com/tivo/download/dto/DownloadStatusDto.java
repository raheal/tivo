package com.tivo.download.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tivo.download.model.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties("event")
public class DownloadStatusDto {

	private DownloadRequestDto request;
	
	private String id;

	private String stage;
	
	private Status status;
	
	private String statusMessage;
	
	private LocalDateTime timestamp;
	
	private String event;
	
}
