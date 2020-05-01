package com.tivo.download.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdapterMapper {
	
	private AdapterMapperEntry defaultAdapter;
	
	private List<AdapterMapperEntry> customAdapters;
	
}
