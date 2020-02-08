package com.tivo.download.rest;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tivo.download.dto.DownloadStatusDto;
import com.tivo.download.service.DownloadLogCache;
import com.tivo.download.service.DownloadStatusCache;

@RequestMapping("/api/v1/download")
@RestController
@CrossOrigin
public class DownloadRecordController {

	@GetMapping("/log")
	public Map<String, DownloadStatusDto> getDownloadLogs() {
		return DownloadLogCache.getInstance().getCache();
	}
	
	@GetMapping("/status/all")
	public Map<String, DownloadStatusDto> getDownloadStatuses() {
		return DownloadStatusCache.getInstance().getCache();
	}
	
}
