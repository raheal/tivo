package com.tivo.download.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DownloadRequestStatusDto {

	private DownloadRequestDto request;
	
	private Status status;
	
	private String taskId;
	
}
