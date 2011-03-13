package org.jstache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;

public final class TemplateBuilder{
	private final String begin;
	private final String end;
	
	public TemplateBuilder(String begin,String end){
		this.begin = begin;
		this.end = end;
	}

	public Template parse(Reader template){
		return new Template(template,begin,end);
	}

	public Template parse(String template){
		return new Template(new StringReader(template),begin,end);
	}

	public Template parse(File template) throws FileNotFoundException{
		return new Template(new FileReader(template),begin,end);
	}
}
