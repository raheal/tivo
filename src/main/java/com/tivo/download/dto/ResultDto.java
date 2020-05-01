package com.tivo.download.dto;

import com.tivo.download.model.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDto {

	private Status status;
	
	private String description;
	
}
