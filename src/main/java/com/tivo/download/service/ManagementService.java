package com.tivo.download.service;

import java.util.List;

import com.tivo.download.dto.MediaFile;

public interface ManagementService {

	Boolean deleteDownloadDirectory(final String taskId);
	
	List<MediaFile> listMediaFiles(final String taskId);
	
}
