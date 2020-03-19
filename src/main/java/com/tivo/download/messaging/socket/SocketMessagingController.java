package com.tivo.download.messaging.socket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class SocketMessagingController {

	@MessageMapping("/message")
	@SendTo("/topic/messages")
	public Message getMessage(final String message) {
		return new Message("Hello, " + HtmlUtils.htmlEscape(message) + "!");
	}
	
}
