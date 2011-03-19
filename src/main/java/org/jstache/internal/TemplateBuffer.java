package org.jstache.internal;


public class TemplateBuffer{
	private final StringBuilder buf = new StringBuilder();
	private final String begin;
	private final String end;

	public TemplateBuffer(String begin,String end){
		this.begin = begin;
		this.end = end;
	}

	public TemplateBuffer append(String value){
		buf.append(value);
		return this;
	}

	public TemplateBuffer appendBegin(){
		buf.append(begin);
		return this;
	}

	public TemplateBuffer appendEnd(){
		buf.append(end);
		return this;
	}

	@Override
	public String toString(){
		return buf.toString();
	}
}
