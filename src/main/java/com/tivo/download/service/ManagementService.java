package com.tivo.download.service;

import java.util.List;

import com.tivo.download.dto.MediaFile;

public interface ManagementService {

	
	List<MediaFile> listMediaFilesInDownloadFolder(final String taskId);
	
	List<MediaFile> listMediaFilesInLibraryFolder();
	
}
