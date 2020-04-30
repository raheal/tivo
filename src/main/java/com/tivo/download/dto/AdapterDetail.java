package com.tivo.download.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdapterDetail {

	private String adapterScript;
	
	private Boolean isStream;
	
	public AdapterDetail(final AdapterMapperEntry adapterMapper) {
		this.adapterScript =  adapterMapper.getScript();
		this.isStream = adapterMapper.getIsStream();
	}

}
