package com.tivo.download.service;

public interface CacheControllerService {

	void turnOnStreamUpdate();
	
	void turnOffStreamUpdate();
	
	boolean isStreamUpdateOn();
	
	boolean clearDownloadLogCache();
	
	boolean clearDownloadStatusCache();
	
	boolean deleteEntry(final String taskId);
	
}
