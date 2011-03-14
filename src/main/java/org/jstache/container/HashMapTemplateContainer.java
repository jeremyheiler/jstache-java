package org.jstache.container;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import org.jstache.Template;

public class HashMapTemplateContainer extends AbstractTemplateContainer implements TemplateContainer{
	private final Map<String,Template> templates = new HashMap<String,Template>();
	private final String begin;
	private final String end;
	
	public HashMapTemplateContainer(String begin,String end){
		this.begin = begin;
		this.end = end;
	}

	@Override
	public Template get(String key){
		return templates.get(key);
	}
	
	@Override
	public Template put(String key,Reader input){
		Template template = Template.with(begin,end).parse(input);
		templates.put(key,template);
		return template;
	}
}
