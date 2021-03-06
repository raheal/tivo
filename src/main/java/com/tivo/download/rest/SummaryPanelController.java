package com.tivo.download.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tivo.download.dto.MediaFile;
import com.tivo.download.service.ManagementService;

import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("/api/v1/summary")
public class SummaryPanelController {

	@Autowired
	private ManagementService managementService;
	
	@GetMapping("/library")
	public List<MediaFile> getMediaFileDataInLibrary() {
		return managementService.listMediaFilesInLibraryFolder();
	}
	
	@GetMapping("/download/{taskId}")
	public List<MediaFile> getMediaFileDataForGivenDownloadDirectory(@PathVariable("taskId") String taskId) {
		return managementService.listMediaFilesInDownloadFolder(taskId);
	}
	
}
