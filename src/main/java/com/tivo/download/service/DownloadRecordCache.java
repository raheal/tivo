package com.tivo.download.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tivo.download.dto.DownloadStatusDto;

public class DownloadRecordCache {

	private static final Logger LOGGER = LoggerFactory.getLogger(DownloadRecordCache.class);
	
	private final Map<String, DownloadStatusDto> cache = new LinkedHashMap<>();
	
	private static DownloadRecordCache instance;
	
	private DownloadRecordCache() {
		LOGGER.info("Download Record Cache is instantiated");
	}
	
	public static DownloadRecordCache getInstance() {
		if (instance == null) {
			instance = new DownloadRecordCache();
		}
		return instance;
	}

	public Map<String, DownloadStatusDto> getCache() {
		return cache;
	}

	
	
}
