package com.tivo.download.rest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tivo.download.dto.DownloadRequestDto;
import com.tivo.download.dto.DownloadRequestStatusDto;
import com.tivo.download.dto.Status;
import com.tivo.download.service.DownloadService;
import com.tivo.download.service.GeneralUtils;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class TivoDownloadController {

	@Autowired
	private DownloadService downloadService;
	
	@PostMapping("/download")
	public DownloadRequestStatusDto download(@RequestBody DownloadRequestDto request) {
		final String taskId = UUID.randomUUID().toString();
		final DownloadRequestStatusDto requestStatus = new DownloadRequestStatusDto();
		requestStatus.setRequest(request);
		requestStatus.setStatus(Status.STARTED);
		requestStatus.setTaskId(taskId);
		GeneralUtils.createDownloadStatusRecord(taskId, Status.NOT_STARTED, "Download request submitted", request, "On Boarding");
		downloadService.downloadStreamData(request, taskId);
		return requestStatus;
	}
	
}
