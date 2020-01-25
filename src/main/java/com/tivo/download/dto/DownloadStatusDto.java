package com.tivo.download.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DownloadStatusDto {

	private DownloadRequestDto request;
	
	private String id;

	private Status status;
	
	private String statusMessage;
	
}
