package com.tivo.download.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tivo.download.service.MessagingService;

@RequestMapping("/api/v1/system")
@RestController
public class SystemController {

	@Autowired
	private MessagingService messagingService;
	
	@GetMapping("/ping")
	public Boolean ping() {
		return true;
	}
	
	@GetMapping("/message-ping")
	public Boolean messagePing() {
		messagingService.publishPingMessage("TEST_MESSAGE");
		return true;
	}
}
