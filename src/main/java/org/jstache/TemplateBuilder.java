package org.jstache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

public final class TemplateBuilder{
	private volatile TemplateParser parser;
	private Charset encoding;
	private String begin;
	private String end;
	private Mode mode;

	public TemplateBuilder(){}

	public TemplateBuilder with(String begin,String end){
		parser = null;
		this.begin = begin;
		this.end = end;
		return this;
	}

	public TemplateBuilder encodedAs(Charset encoding){
		parser = null;
		this.encoding = encoding;
		return this;
	}

	public TemplateBuilder inMode(Mode mode){
		parser = null;
		this.mode = mode;
		return this;
	}

	public Template parse(InputStream source){
		return buildParser().parse(source);
	}

	public Template parse(String source){
		return buildParser().parse(source);
	}

	public Template parse(File source) throws FileNotFoundException{
		return buildParser().parse(source);
	}

	public Template parse(URL source) throws IOException{
		return buildParser().parse(source);
	}

	public TemplateParser buildParser(){
		if(parser == null){
			parser = new TemplateParser(encoding,begin,end,mode);
		}
		return parser;
	}
}
