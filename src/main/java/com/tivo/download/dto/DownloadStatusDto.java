package com.tivo.download.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DownloadStatusDto {

	private DownloadRequestDto request;
	
	private String id;

	private String stage;
	
	private Status status;
	
	private String statusMessage;
	
	private LocalDateTime timestamp;
	
}
