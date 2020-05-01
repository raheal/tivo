package com.tivo.download.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tivo.download.dto.DownloadConfigDto;
import com.tivo.download.dto.DownloadRequestDto;
import com.tivo.download.service.DownloadExecutor;
import com.tivo.download.service.DownloadService;

@Service
public class DownloadServiceImpl implements DownloadService{

	private static final Logger LOGGER = LoggerFactory.getLogger(DownloadServiceImpl.class);

	@Value("${script.working.directory}")
	private String scriptWorkingDirectory;
	
	@Value("${executor.pool.thread.count}")
	private Integer threadCount;
	
	@Value("${python.interpreter.path}")
	private String pythonInterpreterPath;
	
	@Value("${download.parent.path}")
	private String downloadParentPath;
	
	@Value("${download.library.path}")
	private String downloadLibraryPath;
	
	@Value("${adapter.mapper.file.path}")
	private String adapterMapperFilePath;
	
	private ExecutorService executorService;
	
	@PostConstruct
	public void initialize() {
		LOGGER.info("Running executor service");
		executorService = Executors.newFixedThreadPool(threadCount);
		
	}
	
	@PreDestroy
	public void destroy() {
		executorService.shutdown();
		if (executorService.isShutdown()) {
			LOGGER.info("Executor service has stopped");
		}
	}
	
	@Override
	public void downloadStreamData(DownloadRequestDto request, String taskId)  {
		executorService.submit(new DownloadExecutor(request, taskId, new DownloadConfigDto(downloadParentPath, pythonInterpreterPath, scriptWorkingDirectory, downloadLibraryPath, adapterMapperFilePath)));
	}

	@Override
	public void downloadBlobData(DownloadRequestDto request) {
		return;
	}
	
}
