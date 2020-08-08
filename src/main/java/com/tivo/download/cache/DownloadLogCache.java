package com.tivo.download.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tivo.download.dto.DownloadStatusDto;

public class DownloadLogCache {

	private static final Logger LOGGER = LoggerFactory.getLogger(DownloadLogCache.class);
	
	private final Map<String, DownloadStatusDto> logCache = new LinkedHashMap<>();
	
	private static DownloadLogCache instance;
	
	private DownloadLogCache() {
		LOGGER.info("Download Record Cache is instantiated");
	}
	
	public static DownloadLogCache getInstance() {
		if (instance == null) {
			instance = new DownloadLogCache();
		}
		return instance;
	}

	public Map<String, DownloadStatusDto> getCache() {
		return logCache;
	}

	
	
}
