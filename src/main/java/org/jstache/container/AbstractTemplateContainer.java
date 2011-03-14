package org.jstache.container;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import org.jstache.Template;

/**
 * This class implements a few methods from <tt>TemplateContainer</tt> in order
 * to make it easier to implement a storage-specific container.
 */
public abstract class AbstractTemplateContainer implements TemplateContainer{

	@Override
	public Template put(String key,String input){
		return put(key,new StringReader(input));
	}
	
	@Override
	public Template put(String key,File input) throws FileNotFoundException{
		return put(key,new FileReader(input));
	}
	
	@Override
	public Template put(String key,URL input) throws IOException{
		return put(key,new InputStreamReader(input.openStream()));
	}
}
