package com.tivo.download.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.tomcat.util.buf.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tivo.download.dto.DownloadConfigDto;
import com.tivo.download.dto.DownloadRequestDto;
import com.tivo.download.dto.Status;
import com.tivo.download.processor.CleanupProcessor;
import com.tivo.download.processor.DownloadStreamProcessor;
import com.tivo.download.processor.FileAggregationProcessor;
import com.tivo.download.processor.FileStreamProcessor;
import com.tivo.download.processor.Processor;

public class DownloadExecutor implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(DownloadExecutor.class);
	
	private DownloadRequestDto request;
	
	private String taskId;
	
	private DownloadConfigDto downloadConfigDto;
	
	public DownloadExecutor(final DownloadRequestDto request, final String taskId, final DownloadConfigDto downloadConfigDto) {
		this.request = request;
		this.taskId = taskId;
		this.downloadConfigDto = downloadConfigDto;
	}
	
	@Override
	public void run() {
		downloadStreamData(request,  taskId, downloadConfigDto);
	}
	
	public void downloadStreamData(DownloadRequestDto request, String taskId, DownloadConfigDto downloadLoadConfig)  {
		
		// set the download path to the request for further reference in the chain of responsibity pattern.
		request.setFileDownloadDirectory( downloadConfigDto.getDownloadParentPath() + "/" + taskId);
		
		LOGGER.info("[{}] Processing request : {}", taskId, request);
		final Processor downloadStreamProcessor = new DownloadStreamProcessor();
		final Processor fileStreamProcessor = new FileStreamProcessor();
		final Processor fileAggregationProcessor = new FileAggregationProcessor();
		final Processor cleanupProcessor = new CleanupProcessor();
		downloadStreamProcessor.setNextProcessor(fileStreamProcessor);
		fileStreamProcessor.setNextProcessor(fileAggregationProcessor);
		fileAggregationProcessor.setNextProcessor(cleanupProcessor);
		downloadStreamProcessor.process(request, downloadLoadConfig, taskId);	
		
	}
		
}
