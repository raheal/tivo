package com.tivo.download.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tivo.download.dto.DownloadStatusDto;

public class DownloadStatusCache {

	private static final Logger LOGGER = LoggerFactory.getLogger(DownloadStatusCache.class);
	
	private final Map<String, DownloadStatusDto> logCache = new LinkedHashMap<>();
	
	private static DownloadStatusCache instance;
	
	private DownloadStatusCache() {
		LOGGER.info("Download Record Cache is instantiated");
	}
	
	public static DownloadStatusCache getInstance() {
		if (instance == null) {
			instance = new DownloadStatusCache();
		}
		return instance;
	}

	public Map<String, DownloadStatusDto> getCache() {
		return logCache;
	}

	
	
	
	
}
