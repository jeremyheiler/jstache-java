package org.jstache.container;

import java.util.HashMap;
import java.util.Map;
import org.jstache.Template;

public class HashMapCacheManager<K> implements TemplateCacheManager<K>{
	private final Map<K,Template> templates = new HashMap<K,Template>();

	@Override
	public Template get(K key){
		return templates.get(key);
	}

	@Override
	public Template put(K key,Template template){
		templates.put(key,template);
		return template;
	}

	@Override
	public Template remove(K key){
		return templates.remove(key);
	}

	@Override
	public boolean contains(K key){
		return templates.get(key) != null;
	}
}
