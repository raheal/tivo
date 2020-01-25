package com.tivo.download.rest;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tivo.download.dto.DownloadStatusDto;
import com.tivo.download.service.DownloadRecordCache;

@RequestMapping("/api/v1")
@RestController
public class DownloadRecordController {

	@GetMapping("/record")
	public Map<String, DownloadStatusDto> getDownloadRecords() {
		return DownloadRecordCache.getInstance().getCache();
	}
	
}
