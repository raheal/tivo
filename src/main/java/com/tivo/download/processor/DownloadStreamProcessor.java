package com.tivo.download.processor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tivo.download.dto.DownloadConfigDto;
import com.tivo.download.dto.DownloadRequestDto;
import com.tivo.download.dto.Status;
import com.tivo.download.service.GeneralUtils;

public class DownloadStreamProcessor implements Processor{

	private static final Logger LOGGER = LoggerFactory.getLogger(DownloadStreamProcessor.class);
	
	private Processor processor;
	
	private static final String PROCESSOR_NAME = "Downloading data";
	
	private static final String EVENT_NAME = "event.service.tivo.download.stream";
	
	@Override
	public void process(DownloadRequestDto request, DownloadConfigDto downloadConfigDto, String taskId) {
		LOGGER.info("[{}] Run the DownloadStreamProcessor", taskId);
		GeneralUtils.createDownloadStatusRecord(taskId, Status.IN_PROGRESS, GeneralUtils.BLANK_LITERAL, request, PROCESSOR_NAME, EVENT_NAME);
		try {
			final Path fileDownloadPath = Paths.get(request.getFileDownloadDirectory());
			if (!Files.exists(fileDownloadPath)) {
				Files.createDirectories(fileDownloadPath);				
			}
			final Integer result = GeneralUtils.runProcessBuilder(Arrays.asList(new String[] {"cmd", "/c" , downloadConfigDto.getPythonInterpreterPath(), "TivoGenericAdapter.py", "\"" + request.getUrl() + "\"", request.getFileDownloadDirectory(), String.valueOf(request.getStartFileNumber()), String.valueOf(request.getEndFileNumber()), String.valueOf(request.getIsStream())}), downloadConfigDto.getScriptDirectory(), taskId, request);
			if (result == 0) {
				GeneralUtils.createDownloadStatusRecord(taskId, Status.SUCCESS, GeneralUtils.BLANK_LITERAL, request, PROCESSOR_NAME, EVENT_NAME);
				if (processor != null) {
					processor.process(request, downloadConfigDto, taskId);
				}
			} else {
				GeneralUtils.createDownloadStatusRecord(taskId, Status.ERROR, "Error code = 1", request, PROCESSOR_NAME, EVENT_NAME);
			}
		} catch(IOException | InterruptedException e) {
			LOGGER.error(e.getMessage(), e);
			GeneralUtils.createDownloadStatusRecord(taskId, Status.ERROR, e.getMessage(), request, PROCESSOR_NAME, EVENT_NAME);
		}	
	}

	@Override
	public void setNextProcessor(Processor processor) {
		this.processor = processor;
	}

}
