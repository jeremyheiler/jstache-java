package org.jstache.container;

import org.jstache.Template;

public interface TemplateCacheManager<K>{

	public Template get(K key);

	public Template put(K key,Template template);

	public Template remove(K key);

	public boolean contains(K key);
}
