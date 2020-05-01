package com.tivo.download.processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tivo.download.dto.DownloadConfigDto;
import com.tivo.download.dto.DownloadRequestDto;
import com.tivo.download.model.Status;
import com.tivo.download.utils.GeneralUtils;

public class CleanupProcessor implements Processor{

	private static final Logger LOGGER = LoggerFactory.getLogger(CleanupProcessor.class);
	
	private Processor processor;
	
	private static final String PROCESSOR_NAME = "Finalising Data";
	
	private static final String EVENT_NAME = "event.service.tivo.download.finalise";
	
	@Override
	public void process(DownloadRequestDto request, DownloadConfigDto config, String taskId) {	
		GeneralUtils.createDownloadStatusRecord(taskId, Status.IN_PROGRESS, GeneralUtils.BLANK_LITERAL, request, PROCESSOR_NAME, EVENT_NAME);
		//move the final file to the library folder
		final Path file = Paths.get(request.getFileDownloadDirectory() + File.separator + request.getOutputFileName() + ".mp4");
		final Path targetFile = Paths.get(config.getLibraryPath() + File.separator + request.getOutputFileName() + ".mp4");
		final Path targetFolder = Paths.get(config.getLibraryPath());
		// delete the folder containing the stream files
		try {
			Files.move(file, targetFile);
			if (Files.exists(targetFile)) {
				LOGGER.info("Deleting temporary files");
				FileUtils.forceDelete(new File(request.getFileDownloadDirectory()));
				GeneralUtils.createDownloadStatusRecord(taskId, Status.SUCCESS, GeneralUtils.BLANK_LITERAL, request, PROCESSOR_NAME, EVENT_NAME);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			GeneralUtils.createDownloadStatusRecord(taskId, Status.ERROR, e.getMessage(), request, PROCESSOR_NAME, EVENT_NAME);
		}
		
		if (processor != null) {
			processor.process(request, config, taskId);
		}
	}

	@Override
	public void setNextProcessor(Processor processor) {
		this.processor = processor;
	}
}
