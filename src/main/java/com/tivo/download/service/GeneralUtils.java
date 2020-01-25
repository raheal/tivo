package com.tivo.download.service;

import java.util.UUID;

import com.tivo.download.dto.DownloadRequestDto;
import com.tivo.download.dto.DownloadStatusDto;
import com.tivo.download.dto.Status;

public class GeneralUtils {

	public static final DownloadStatusDto createDownloadStatusRecord(String taskId, Status status, String statusMessage, DownloadRequestDto request) {
		final DownloadStatusDto downloadStatus = new DownloadStatusDto();
		downloadStatus.setId(taskId);
		downloadStatus.setRequest(request);
		downloadStatus.setStatus(status);
		downloadStatus.setStatusMessage(statusMessage);
		DownloadRecordCache.getInstance().getCache().put(UUID.randomUUID().toString(), downloadStatus);
		return downloadStatus;
	}
	
}
