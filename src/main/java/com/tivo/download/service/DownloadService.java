package com.tivo.download.service;

import com.tivo.download.dto.DownloadRequestDto;
import com.tivo.download.dto.ResultDto;

public interface DownloadService {

	void downloadStreamData(DownloadRequestDto request, String taskId);
	
	void downloadBlobData(DownloadRequestDto request);
	
	
}
