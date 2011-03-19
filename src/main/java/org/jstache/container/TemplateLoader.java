package org.jstache.container;

import java.io.InputStream;

public interface TemplateLoader<K>{

	public InputStream load(K key);
}
