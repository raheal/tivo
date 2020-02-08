package com.tivo.download.dto;

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
	
	
}
