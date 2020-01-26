package com.tivo.download.processor;

import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tivo.download.dto.DownloadConfigDto;
import com.tivo.download.dto.DownloadRequestDto;
import com.tivo.download.dto.Status;
import com.tivo.download.service.GeneralUtils;

public class FileAggregationProcessor implements Processor {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileAggregationProcessor.class);
	
	private Processor processor;
	
	private static final String PROCESSOR_NAME = "Aggregate data";
	
	@Override
	public void process(DownloadRequestDto request, DownloadConfigDto downloadConfigDto, String taskId) {
		LOGGER.info("[{}] Run the FileAggregationProcessor : {}", taskId);
		try {
			final String fileDownloadPath = downloadConfigDto.getDownloadParentPath() + "/" + taskId;
			final Integer result = GeneralUtils.runProcessBuilder(Arrays.asList(new String[] {"cmd", "/c" , downloadConfigDto.getPythonInterpreterPath(), "no-op.py", downloadConfigDto.getDownloadParentPath()}), downloadConfigDto.getScriptDirectory(), taskId, request);
			if (result == 0) {
				GeneralUtils.createDownloadStatusRecord(taskId, Status.SUCCESS, GeneralUtils.BLANK_LITERAL, request, PROCESSOR_NAME);
				if (processor != null) {
					processor.process(request, downloadConfigDto, taskId);
				}
			} else {
				GeneralUtils.createDownloadStatusRecord(taskId, Status.ERROR, "Error code = 1", request, PROCESSOR_NAME);
			}
		} catch(IOException | InterruptedException e) {
			LOGGER.error(e.getMessage(), e);
			GeneralUtils.createDownloadStatusRecord(taskId, Status.ERROR, e.getMessage(), request, PROCESSOR_NAME);
		}

	}

	@Override
	public void setNextProcessor(Processor processor) {
		this.processor = processor;

	}

}
