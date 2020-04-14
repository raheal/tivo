package com.tivo.download.service;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tivo.download.dto.MediaFile;

@Service
public class ManagementServiceImpl implements ManagementService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ManagementServiceImpl.class);

	@Value("${download.parent.path}")
	private String downloadParentPath;

	@Value("${download.library.path}")
	private String downloadLibraryPath;
	
	@Override
	public List<MediaFile> listMediaFilesInDownloadFolder(String taskId) {
		return listMediaFiles(Paths.get(downloadParentPath + File.separator + taskId));
	}
	
	@Override
	public List<MediaFile> listMediaFilesInLibraryFolder() {
		return listMediaFiles(Paths.get(downloadLibraryPath));
	}
	
	
	
	private List<MediaFile> listMediaFiles(final Path directory) {
		final List<MediaFile> mediaFilesList = new ArrayList<>();
		try {
			final List<Path> directories = Files.list(directory).collect(Collectors.toList());
			for (final Path path : directories) {
				final String filename = path.getFileName().toString();
				final MediaFile mediaFile = new MediaFile();
				final LocalDateTime lastModified = LocalDateTime.ofInstant(Instant.ofEpochMilli(Files.getLastModifiedTime(path).toMillis()), TimeZone.getDefault().toZoneId());
				final FileChannel fileChannel = FileChannel.open(path);
				mediaFile.setFullFilePath(path.toAbsolutePath().toString());
				mediaFile.setName(filename);
				mediaFile.setSize(fileChannel.size());
				mediaFile.setFileExtension(filename.substring(filename.lastIndexOf('.'), filename.length()));
				mediaFile.setCreationDate(lastModified);
				mediaFile.setDuration("00:00:00.00");
				mediaFilesList.add(mediaFile);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return mediaFilesList;
	}
	
	
	
	
	
}
