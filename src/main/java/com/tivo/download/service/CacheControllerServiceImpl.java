package com.tivo.download.service;

import java.io.File;
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
public class CacheControllerServiceImpl implements CacheControllerService{

	private static final Logger LOGGER = LoggerFactory.getLogger(CacheControllerServiceImpl.class);
	
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
				int count = new File(statusDto.getRequest().getFileDownloadDirectory()).listFiles().length;
				statusDto.setStatusMessage(String.valueOf(count).concat(" file(s) downloaded"));
			}
		}
	}

	public boolean isCacheUpdate() {
		return cacheUpdate;
	}

	@Override
	public boolean clearDownloadLogCache() {
		DownloadLogCache.getInstance().getCache().clear();
		return (DownloadLogCache.getInstance().getCache().size() ==0) ? true : false;
	}

	@Override
	public boolean clearDownloadStatusCache() {
		DownloadStatusCache.getInstance().getCache().clear();
		return (DownloadStatusCache.getInstance().getCache().size() == 0) ? true : false;
	}


	@Override
	public boolean deleteEntry(String taskId) {
		final DownloadStatusDto downloadStatusDto = DownloadStatusCache.getInstance().getCache().get(taskId);
		downloadStatusDto.setStatus(Status.DELETED);
		return true;
	}

	
	
}
