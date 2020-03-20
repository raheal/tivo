package com.tivo.download.service;

public interface StreamUpdateService {

	void turnOnStreamUpdate();
	
	void turnOffStreamUpdate();
	
	boolean isStreamUpdateOn();
	
	
}
