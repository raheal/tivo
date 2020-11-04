package com.tivo.download.dto;

import com.tivo.download.model.ProcessingPlan;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DownloadRequestDto {

	private String url;
	
	private String outputFileName;
	
	private Integer startFileNumber;
	
	private Integer endFileNumber;
	
	private Boolean isStream;
	
	private String mediaMetadata;
	
	private String fileDownloadDirectory;
	
	private String taskId;
	
	private boolean resumeDownload;
	
	private ProcessingPlan processingPlan;
	
	private boolean autoRestart;
	
	
}
