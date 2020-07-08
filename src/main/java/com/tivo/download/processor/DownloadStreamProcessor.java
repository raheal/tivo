package com.tivo.download.processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tivo.download.dto.DownloadConfigDto;
import com.tivo.download.dto.DownloadRequestDto;
import com.tivo.download.exceptions.ServiceException;
import com.tivo.download.model.AdapterDetail;
import com.tivo.download.model.AdapterMapper;
import com.tivo.download.model.AdapterMapperEntry;
import com.tivo.download.model.ProcessResult;
import com.tivo.download.model.Status;
import com.tivo.download.utils.GeneralUtils;

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
			final AdapterMapper adapterMapperDto = new ObjectMapper().readValue(new File(downloadConfigDto.getAdapterMapperFilePath()), AdapterMapper.class);
			final AdapterDetail adapterDetail = getAdapter(adapterMapperDto, request.getUrl());
			// here, override the isStream property using the adapter's value rather than using the request's value
			if (adapterDetail.getIsStream() != null && !adapterDetail.getIsStream()) {
				request.setIsStream(false);
			}
			final Path fileDownloadPath = Paths.get(request.getFileDownloadDirectory());
			if (!Files.exists(fileDownloadPath)) {
				Files.createDirectories(fileDownloadPath);				
			}
			final ProcessResult result = GeneralUtils.runProcessBuilder(createProcessCommand(downloadConfigDto, request, adapterDetail), downloadConfigDto.getScriptDirectory(), taskId, request);
			if (result.getReturnCode() == 0) {
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
	
	private List<String> createProcessCommand(final DownloadConfigDto downloadConfigDto, final DownloadRequestDto request, final AdapterDetail adapterDetail) {
		if (!request.getIsStream()) {
			return Arrays.asList(new String[] {"cmd", "/c", downloadConfigDto.getPythonInterpreterPath(), adapterDetail.getAdapterScript(), "\"" + request.getUrl() + "\"", request.getFileDownloadDirectory(), request.getOutputFileName()});
		}
		if (request.isResumeDownload()) {
			prepareStartEndMarkersForResumingDownload(request);
		}
		return Arrays.asList(new String[] {"cmd", "/c" , downloadConfigDto.getPythonInterpreterPath(), adapterDetail.getAdapterScript(), "\"" + request.getUrl() + "\"", request.getFileDownloadDirectory(), String.valueOf(request.getStartFileNumber()), String.valueOf(request.getEndFileNumber()), String.valueOf(request.getIsStream())});
	}
	
	private AdapterDetail getAdapter(final AdapterMapper adapterMapper, final String downloadUrl) throws ServiceException{
		if (adapterMapper == null) {
			throw new ServiceException("No adapters are provided in the mapper file");
		}
		if (adapterMapper.getCustomAdapters().isEmpty()) {
			return new AdapterDetail(adapterMapper.getDefaultAdapter());
		}
		for (final AdapterMapperEntry adapter : adapterMapper.getCustomAdapters()) {
			if (downloadUrl.contains(adapter.getUrlPattern())) {
				return new AdapterDetail(adapter);
			}
		}
		return new AdapterDetail(adapterMapper.getDefaultAdapter());
	}
	
	
	/**
	 * If the system is resuming a stream download, find the latest file downloaded (numerically named), and set the start and
	 * end marks for resuming the download
	 * @param request
	 */
	
	private void prepareStartEndMarkersForResumingDownload(final DownloadRequestDto request) {
		final Integer numberOfFiles = new File(request.getFileDownloadDirectory()).listFiles().length;
		if(numberOfFiles > 1) {
			request.setStartFileNumber(numberOfFiles - 1);
		}
	}

}
