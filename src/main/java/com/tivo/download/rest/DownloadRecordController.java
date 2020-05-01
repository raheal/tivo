package com.tivo.download.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tivo.download.cache.DownloadLogCache;
import com.tivo.download.cache.DownloadStatusCache;
import com.tivo.download.dto.DownloadStatusDto;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@RequestMapping("/api/v1/download")
@RestController
@CrossOrigin
public class DownloadRecordController {

	@Autowired
	private MeterRegistry meterRegistry;
	
	@GetMapping("/log")
	public Map<String, DownloadStatusDto> getDownloadLogs() {
		final Counter counter = Counter
				  .builder("counter_query_log")
				  .description("Counts the number of times the download log endpoint is invoked")
				  .tags("counter_type", "full_integer")
				  .register(meterRegistry);
		
		counter.increment();
		return DownloadLogCache.getInstance().getCache();
	}
	
	@GetMapping("/status/all")
	public Map<String, DownloadStatusDto> getDownloadStatuses() {
		final Counter counter = this.meterRegistry.counter("counter_query_status_dashboard");
		counter.increment();
		return DownloadStatusCache.getInstance().getCache();
	}
	
}
