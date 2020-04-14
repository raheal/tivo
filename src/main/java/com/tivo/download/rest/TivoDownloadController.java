package com.tivo.download.rest;

import java.io.File;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tivo.download.dto.DownloadRequestDto;
import com.tivo.download.dto.DownloadRequestStatusDto;
import com.tivo.download.dto.Event;
import com.tivo.download.dto.EventBuilder;
import com.tivo.download.dto.Status;
import com.tivo.download.service.DownloadService;
import com.tivo.download.service.EventService;
import com.tivo.download.service.GeneralUtils;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class TivoDownloadController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TivoDownloadController.class);
	
	@Autowired
	private DownloadService downloadService;
	
	@Autowired
	private EventService eventService;

	
	@Autowired
	private SimpMessagingTemplate template;
	
	private static final String EVENT_NAME = "event.service.tivo.download.request";
	
	@PostMapping("/download")
	public DownloadRequestStatusDto download(@RequestBody DownloadRequestDto request) {
		final String taskId = (request.isResumeDownload() ? request.getTaskId() : UUID.randomUUID().toString());
		final DownloadRequestStatusDto requestStatus = new DownloadRequestStatusDto();
		requestStatus.setRequest(request);
		requestStatus.setStatus(Status.STARTED);
		requestStatus.setTaskId(taskId);
		logDownloadRequest(eventService, taskId, request);
		downloadService.downloadStreamData(request, taskId);
		template.convertAndSend("/topic/messages", "Sending a message");
		return requestStatus;
	}
	

	
	private void logDownloadRequest(final EventService eventService, final String taskId, final DownloadRequestDto request) {
		GeneralUtils.createDownloadStatusRecord(taskId, Status.NOT_STARTED, "Download request submitted", request, "On Boarding", EVENT_NAME);
		try {
			final Event event = new EventBuilder().setEventName(EVENT_NAME).setEventPayload(new ObjectMapper().writeValueAsString(request)).build();
			eventService.logEvent(event);
		} catch (JsonProcessingException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	
	
}
