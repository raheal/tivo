package com.tivo.download.model;

import org.apache.catalina.authenticator.Constants;
import org.apache.tomcat.util.bcel.classfile.Constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProcessResult {

	private Integer returnCode;
	
	private String message;
	
	
}
