package com.tivo.download.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdapterMapperEntry {

	private String name;
	
	private String script;
	
	private String urlPattern;
	
	private Boolean isStream;
	
}
