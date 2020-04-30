package com.tivo.download.processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tivo.download.dto.AdapterMapperDto;
import com.tivo.download.dto.AdapterMapperEntry;
import com.tivo.download.dto.DownloadConfigDto;
import com.tivo.download.dto.DownloadRequestDto;
import com.tivo.download.dto.Status;
import com.tivo.download.exceptions.ServiceException;
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
			final AdapterMapperDto adapterMapperDto = new ObjectMapper().readValue(new File(downloadConfigDto.getAdapterMapperFilePath()), AdapterMapperDto.class);
			final String adapterScript = getAdapter(adapterMapperDto, request.getUrl());
			final Path fileDownloadPath = Paths.get(request.getFileDownloadDirectory());
			if (!Files.exists(fileDownloadPath)) {
				Files.createDirectories(fileDownloadPath);				
			}
			final Integer result = GeneralUtils.runProcessBuilder(Arrays.asList(new String[] {"cmd", "/c" , downloadConfigDto.getPythonInterpreterPath(), adapterScript, "\"" + request.getUrl() + "\"", request.getFileDownloadDirectory(), String.valueOf(request.getStartFileNumber()), String.valueOf(request.getEndFileNumber()), String.valueOf(request.getIsStream())}), downloadConfigDto.getScriptDirectory(), taskId, request);
			if (result == 0) {
				GeneralUtils.createDownloadStatusRecord(taskId, Status.SUCCESS, GeneralUtils.BLANK_LITERAL, request, PROCESSOR_NAME, EVENT_NAME);
				if (processor != null) {
					processor.process(request, downloadConfigDto, taskId);
				}
			} else {
				GeneralUtils.createDownloadStatusRecord(taskId, Status.ERROR, "Error code = 1", request, PROCESSOR_NAME, EVENT_NAME);
			}
		} catch(IOException | InterruptedException | ServiceException e) {
			LOGGER.error(e.getMessage(), e);
			GeneralUtils.createDownloadStatusRecord(taskId, Status.ERROR, e.getMessage(), request, PROCESSOR_NAME, EVENT_NAME);
		}	
	}

	@Override
	public void setNextProcessor(Processor processor) {
		this.processor = processor;
	}
	
	private String getAdapter(final AdapterMapperDto adapterMapper, final String downloadUrl) throws ServiceException{
		if (adapterMapper == null) {
			throw new ServiceException("No adapters are provided in the mapper file");
		}
		if (adapterMapper.getCustomAdapters().isEmpty()) {
			return adapterMapper.getDefaultAdapter().getScript();
		}
		for (final AdapterMapperEntry adapter : adapterMapper.getCustomAdapters()) {
			if (downloadUrl.contains(adapter.getUrlPattern())) {
				return adapter.getScript();
			}
		}
		return adapterMapper.getDefaultAdapter().getScript();
	}

}
