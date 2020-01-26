package com.tivo.download.processor;

import com.tivo.download.dto.DownloadConfigDto;
import com.tivo.download.dto.DownloadRequestDto;

public interface Processor {

	void process(final DownloadRequestDto request, final DownloadConfigDto config, final String taskId);
	
	void setNextProcessor(final Processor processor);
	
}
