package com.tivo.download.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tivo.download.dto.MediaFile;

@Service
public class ManagementServiceImpl implements ManagementService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ManagementServiceImpl.class);

	@Override
	public Boolean deleteDownloadDirectory(String taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MediaFile> listMediaFiles(String taskId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}
