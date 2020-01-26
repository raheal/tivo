package com.tivo.download.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.apache.tomcat.util.buf.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tivo.download.dto.DownloadRequestDto;
import com.tivo.download.dto.DownloadStatusDto;
import com.tivo.download.dto.Status;

public class GeneralUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeneralUtils.class);
	
	public static final String BLANK_LITERAL = "";
	
	
	public static final DownloadStatusDto createDownloadStatusRecord(String taskId, Status status, String statusMessage, DownloadRequestDto request, String stage) {
		final DownloadStatusDto downloadStatus = new DownloadStatusDto();
		downloadStatus.setId(taskId);
		downloadStatus.setRequest(request);
		downloadStatus.setStatus(status);
		downloadStatus.setStatusMessage(statusMessage);
		downloadStatus.setStage(stage);
		downloadStatus.setTimestamp(LocalDateTime.now());
		DownloadRecordCache.getInstance().getCache().put(UUID.randomUUID().toString(), downloadStatus);
		return downloadStatus;
	}
	
	
	public static Integer runProcessBuilder(final List<String> cmdArguments, final String directory, final String taskId, final DownloadRequestDto request) throws IOException, InterruptedException {
		LOGGER.info("[{}] Running command : [{}]", taskId, StringUtils.join(cmdArguments));
		final ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command(cmdArguments);
		processBuilder.directory(new File(directory));
		final Process process = processBuilder.start();
		final int exitValue = process.waitFor();
		return exitValue;
	}
	
	
	
}
