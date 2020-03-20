package com.tivo.download.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tivo.download.dto.DownloadStatusDto;
import com.tivo.download.dto.Status;

@Component
public class StreamUpdateServiceImpl implements StreamUpdateService{

	private static final Logger LOGGER = LoggerFactory.getLogger(StreamUpdateServiceImpl.class);
	
	private ScheduledExecutorService scheduledExecutorService;
	
	private boolean streamUpdateOn;
	
	@Value("${cache.update}")
	private boolean cacheUpdate;

	
	@PostConstruct
	private void init() {
		if (isCacheUpdate()) {
			turnOnStreamUpdate();
		}
	}
	
	@PreDestroy
	private void destroy() {
		turnOffStreamUpdate();
	}
	
	@Override
	public void turnOnStreamUpdate() {
		if (!streamUpdateOn) {
			scheduledExecutorService = Executors.newScheduledThreadPool(1);
			scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					LOGGER.info("Running scheduler");
					try {
						updateFileStreamProgress();
					} catch (IOException e) {
						LOGGER.error(e.getMessage(), e);
					}
				}
			},0, 10, TimeUnit.SECONDS);
			streamUpdateOn = true;
		}
		
	}

	@Override
	public void turnOffStreamUpdate() {
		scheduledExecutorService.shutdown();
		if (scheduledExecutorService.isShutdown()) {
			streamUpdateOn = false;
			LOGGER.info("Scheduler is shutdown");
		}
	}

	@Override
	public boolean isStreamUpdateOn() {
		return streamUpdateOn;
	}
	
	
	private void updateFileStreamProgress() throws IOException{
		for (DownloadStatusDto statusDto : DownloadStatusCache.getInstance().getCache().values()) {
			if (statusDto.getStatus() == Status.IN_PROGRESS) {
				Long fileCount = Files.list(Paths.get(statusDto.getRequest().getFileDownloadDirectory())).count();
				statusDto.setStatusMessage(String.valueOf(fileCount).concat(" file(s) downloaded"));
			}
		}
	}

	public boolean isCacheUpdate() {
		return cacheUpdate;
	}

	
}
