package org.jstache.container.nop;

import java.io.InputStream;
import org.jstache.container.TemplateLoader;

public class NopLoader<K> implements TemplateLoader<K>{

	@Override
	public InputStream load(K key){
		return null;
	}
}
