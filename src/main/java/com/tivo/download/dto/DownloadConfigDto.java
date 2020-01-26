package com.tivo.download.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DownloadConfigDto {

	private String downloadParentPath;
	
	private String pythonInterpreterPath;
	
	private String scriptDirectory;
	
}
