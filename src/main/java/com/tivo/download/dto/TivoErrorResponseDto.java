package com.tivo.download.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TivoErrorResponseDto {

	private String id;
	
	private String service;
	
	private Integer responseCode;
	
	private String message;
	
	private String errorMessage;
	
	private LocalDateTime timestamp;
	
}
