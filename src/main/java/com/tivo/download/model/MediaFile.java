package com.tivo.download.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaFile {

	private String fullFilePath;
	
	private String name;
	
	private Long size;
	
	private LocalDateTime creationDate;
	
	private String fileExtension;
	
	private String duration;
		
}
