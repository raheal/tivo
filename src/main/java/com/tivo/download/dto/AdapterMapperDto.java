package com.tivo.download.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdapterMapperDto {

	private AdapterMapperEntry defaultAdapter;
	
	private List<AdapterMapperEntry> customAdapters;
	
	
}
