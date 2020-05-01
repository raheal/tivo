package com.tivo.download.service.impl;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tivo.download.service.FileService;

@Service
public class FileServiceImpl implements FileService{

	private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);
	
	@Override
	public Boolean deleteFile(final File folderPath) {
		try {
			FileUtils.deleteDirectory(folderPath);
			return true;
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return false;
		}
	}

}
