package com.tivo.download.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tivo.download.service.CacheControllerService;
import com.tivo.download.service.DownloadLogCache;
import com.tivo.download.service.DownloadStatusCache;

@RequestMapping("/api/v1/cache")
@RestController
public class CacheController {

	@Autowired
	private CacheControllerService cacheControllerService;
	
	@PostMapping("/clear/status")
	public Boolean clearDownloadStatusCache() {
		return cacheControllerService.clearDownloadStatusCache();
	}
	
	@PostMapping("/clear/log")
	public Boolean clearDownloadLogCache() {
		return cacheControllerService.clearDownloadLogCache();
	}
	
	@PostMapping("/scheduler/{switch}")
	public void switchOnScheduledCacheUpdate(@PathVariable("switch") final Boolean schedulerSwitch) {
		if (schedulerSwitch) {
			cacheControllerService.turnOnStreamUpdate();
		} else {
			cacheControllerService.turnOffStreamUpdate();
		}
	}
	
	@GetMapping("/scheduler/switch")
	public Boolean isScheduledCacheUpdateOn() {
		return cacheControllerService.isStreamUpdateOn();
	}
	
	
}
