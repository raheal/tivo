package com.tivo.download.rest;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tivo.download.service.CacheControllerService;
import com.tivo.download.service.DownloadLogCache;
import com.tivo.download.service.DownloadStatusCache;
import com.tivo.download.service.FileService;

@RequestMapping("/api/v1/settings")
@RestController
@CrossOrigin
public class SettingsController {

	@Autowired
	private CacheControllerService cacheControllerService;
	
	@Autowired
	private FileService fileService;
	
	@PostMapping("cache/clear/status")
	public Boolean clearDownloadStatusCache() {
		return cacheControllerService.clearDownloadStatusCache();
	}
	
	@PostMapping("cache/clear/log")
	public Boolean clearDownloadLogCache() {
		return cacheControllerService.clearDownloadLogCache();
	}
	
	@PostMapping("cache/scheduler/{switch}")
	public void switchOnScheduledCacheUpdate(@PathVariable("switch") final Boolean schedulerSwitch) {
		if (schedulerSwitch) {
			cacheControllerService.turnOnStreamUpdate();
		} else {
			cacheControllerService.turnOffStreamUpdate();
		}
	}
	
	@GetMapping("cache/scheduler/switch")
	public Boolean isScheduledCacheUpdateOn() {
		return cacheControllerService.isStreamUpdateOn();
	}
	
	@PutMapping("/delete/{taskId}")
	public Boolean delete(@PathVariable("taskId") String taskId, @Value("${download.parent.path}") String downloadPath) {
		final File directory = new File(downloadPath + File.separator + taskId);
		if (fileService.deleteFile(directory)) {
			return cacheControllerService.deleteEntry(taskId);
		}
		return false;
	}
	
	
}
