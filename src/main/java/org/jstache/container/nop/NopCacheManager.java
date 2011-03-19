package org.jstache.container.nop;

import org.jstache.container.TemplateCacheManager;

import org.jstache.Template;

public class NopCacheManager<K> implements TemplateCacheManager<K>{

	@Override
	public Template get(K key){
		return null;
	}

	@Override
	public Template put(K key,Template template){
		return template;
	}

	@Override
	public Template remove(K key){
		return null;
	}

	@Override
	public boolean contains(K key){
		return false;
	}
}
