package com.tivo.download.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.tomcat.util.buf.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tivo.download.dto.DownloadRequestDto;
import com.tivo.download.dto.Status;

public class DownloadExecutor implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(DownloadExecutor.class);
	
	private DownloadRequestDto request;
	
	private String workingDirectory;
	
	private String taskId;
	
	private String pythonInterpreterPath;
	
	private String downloadParentPath;
	
	public DownloadExecutor(final DownloadRequestDto request, final String workingDirectory, final String taskId, final String pythonInterpreterPath, final String downloadParentPath) {
		this.request = request;
		this.workingDirectory = workingDirectory;
		this.taskId = taskId;
		this.pythonInterpreterPath = pythonInterpreterPath;
		this.downloadParentPath = downloadParentPath;
	}
	
	@Override
	public void run() {
		downloadStreamData(request, workingDirectory, taskId, pythonInterpreterPath, downloadParentPath);
	}
	
	public void downloadStreamData(DownloadRequestDto request, String workingDirectory, String taskId, String pythonInterpreterPath, String downloadParentPath)  {
		LOGGER.info("[{}] Processing request : {}", taskId, request);
		try {
			final String fileDownloadPath = downloadParentPath + "/" + taskId;
			runProcessBuilder(Arrays.asList(new String[] {"cmd", "/c" , pythonInterpreterPath, "no-op.py", fileDownloadPath}), workingDirectory, taskId);
		} catch(IOException | InterruptedException e) {
			LOGGER.error(e.getMessage(), e);
			GeneralUtils.createDownloadStatusRecord(taskId, Status.ERROR, e.getMessage(), request);
		}
		return;
	}
	
	
	
	private void runProcessBuilder(final List<String> cmdArguments, final String directory, final String taskId) throws IOException, InterruptedException {
		LOGGER.info("[{}] Running command : [{}]", taskId, StringUtils.join(cmdArguments));
		final ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command(cmdArguments);
		processBuilder.directory(new File(directory));
		final Process process = processBuilder.start();
		final int exitValue = process.waitFor();
		if (exitValue == 1) {
			GeneralUtils.createDownloadStatusRecord(taskId, Status.ERROR, "Failed Execution", request);
		} else {
			GeneralUtils.createDownloadStatusRecord(taskId, Status.SUCCESS, "Successful Execution", request);
		}
	}
	
	
	
	
}
