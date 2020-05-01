package com.tivo.download.rest;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tivo.download.dto.TivoErrorResponseDto;
import com.tivo.download.exceptions.ServiceException;

@RestControllerAdvice
public class TivoControllerAdvice {

	@Value("${spring.application.name}") 
	private String serviceName;

	private static final Logger LOGGER = LoggerFactory.getLogger(TivoControllerAdvice.class);
	
	@ExceptionHandler(value = {ServiceException.class})
	public ResponseEntity<TivoErrorResponseDto> customiseErrorResponse(final ServiceException serviceException) {
		LOGGER.error(serviceException.getMessage(), serviceException);
		final TivoErrorResponseDto dto = new TivoErrorResponseDto(UUID.randomUUID().toString(), serviceName, HttpStatus.INTERNAL_SERVER_ERROR.value(), "The service cannot process the request at this point in time", serviceException.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
