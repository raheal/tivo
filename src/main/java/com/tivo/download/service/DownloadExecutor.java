package com.tivo.download.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tivo.download.dto.DownloadConfigDto;
import com.tivo.download.dto.DownloadRequestDto;
import com.tivo.download.exceptions.ServiceException;
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
		try {
			downloadStreamData(request,  taskId, downloadConfigDto);
		} catch (ServiceException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	public void downloadStreamData(DownloadRequestDto request, String taskId, DownloadConfigDto downloadLoadConfig) throws ServiceException{
		request.setFileDownloadDirectory( downloadConfigDto.getDownloadParentPath() + "/" + taskId);
		LOGGER.info("[{}] Processing request : {}", taskId, request);
		final Processor downloadStreamProcessor = new DownloadStreamProcessor();
		final Processor fileStreamProcessor = new FileStreamProcessor();
		final Processor fileAggregationProcessor = new FileAggregationProcessor();
		final Processor cleanupProcessor = new CleanupProcessor();
		
		switch(request.getProcessingPlan()) {
			case STRAIGHT_THROUGH:
				downloadStreamProcessor.setNextProcessor(fileStreamProcessor);
				fileStreamProcessor.setNextProcessor(fileAggregationProcessor);
				fileAggregationProcessor.setNextProcessor(cleanupProcessor);
				downloadStreamProcessor.process(request, downloadLoadConfig, taskId);	
				break;
			case DOWNLOAD_ONLY:
				downloadStreamProcessor.process(request, downloadLoadConfig, taskId);
				break;
			case PROCESS_ONLY:
				fileStreamProcessor.process(request, downloadLoadConfig, taskId);
				break;
			case AGGREGATE_ONLY:
				fileAggregationProcessor.process(request, downloadLoadConfig, taskId);
				break;
			default:
				throw new ServiceException ("Unknown Processing plan : [" + request.getProcessingPlan() + "]");
		}
		
	}
		
}
