package org.jstache.container;

import org.jstache.provider.Provider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import org.jstache.Template;
import org.jstache.container.nop.NopCacheManager;
import org.jstache.container.nop.NopLoader;

/**
 * A <tt>TemplateContainer</tt> provides an interface for maintaining a cache of
 * parsed templates. The storage mechanism for the cache is determined by the
 * implementations of this class.
 */
public final class TemplateContainer<K>{
	private final TemplateCacheManager<K> cache;
	private final TemplateLoader<K> loader;
	private final String begin;
	private final String end;

	private TemplateContainer(TemplateCacheManager<K> cache,TemplateLoader<K> loader,String begin,String end){
		this.cache = cache;
		this.loader = loader;
		this.begin = begin;
		this.end = end;
	}

	/**
	 * Gets the template with the given key from the container.<br>
	 * If the template is cached, it will return the cached instance. If the
	 * template is not cached, and the container is configured with a
	 * {@code TemplateLoader}, the template is loaded, parsed, put into cache,
	 * and then finally returned. If the template cannot be found, <tt>null</tt>
	 * is returned.
	 *
	 * @param id The object that identifies the template in the container.
	 * @return The template, or null if it cannot be found.
	 */
	public Template get(K key){
		return cache.get(key);
	}

	public Template load(K key,boolean store){
		Template template = parse(loader.load(key));
		if(store){
			cache.put(key,template);
		}
		return template;
	}

	/**
	 * Parses the template from the given <tt>Reader</tt> with the containers
	 * template configuration. The template is then cached in the container.
	 *
	 * @param key The key that identifies the template in the container.
	 * @param input A <tt>Reader</tt> that represents the template.
	 * @return The template identified by the key.
	 */
	public Template put(K key,InputStream source){
		return cache.put(key,parse(source));
	}

	/**
	 * Parses the template from the given <tt>String</tt> with the containers
	 * template configuration. The template is then put into the container and
	 * returned.
	 *
	 * @param key The key that identifies the template in the container.
	 * @param input A <tt>String</tt> that represents the template.
	 * @return The template identified by the key.
	 */
	public Template put(K key,String source){
		return cache.put(key,parse(source));
	}

	/**
	 * Parses the template from the given <tt>File</tt> with the containers
	 * template configuration. The template is then put into the container and
	 * returned.
	 *
	 * @param key The key that identifies the template in the container.
	 * @param input A <tt>File</tt> that represents the template.
	 * @return The template identified by the key.
	 */
	public Template put(K key,File source) throws FileNotFoundException{
		return cache.put(key,parse(source));
	}

	/**
	 * Parses the template from the given <tt>URL</tt> with the containers
	 * template configuration. The template is then put into the container and
	 * returned.
	 *
	 * @param key The key that identifies the template in the container.
	 * @param input A <tt>URL</tt> that represents the template.
	 * @return The template identified by the key.
	 */
	public Template put(K key,URL source) throws IOException{
		return cache.put(key,parse(source));
	}

	public Template parse(InputStream source){
		return Template.with(begin,end).parse(source);
	}

	public Template parse(String source){
		return Template.with(begin,end).parse(source);
	}

	public Template parse(File source) throws FileNotFoundException{
		return Template.with(begin,end).parse(source);
	}

	public Template parse(URL source) throws IOException{
		return Template.with(begin,end).parse(source);
	}

	public String render(K key,Provider presenter){
		Template template = cache.get(key);
		checkNull(template);
		return template.render(presenter);
	}

	public String render(K key,Map<String,?> data){
		Template template = cache.get(key);
		checkNull(template);
		return template.render(data);
	}

	public <B> String render(K key,B bean){
		Template template = cache.get(key);
		checkNull(template);
		return template.render(bean);
	}

	private void checkNull(Template template){
		if(template == null){
			throw new TemplateNotFoundException();
		}
	}

	public TemplateCacheManager<K> getCacheManager(){
		return cache;
	}

	public TemplateLoader<K> getLoader(){
		return loader;
	}

	public static final class Builder<K>{
		private TemplateCacheManager<K> cache;
		private TemplateLoader<K> loader;

		public Builder(){}

		public TemplateContainer<K> build(){
			if(cache == null){
				cache = new NopCacheManager<K>();
			}
			if(loader == null){
				loader = new NopLoader<K>();
			}
			return new TemplateContainer<K>(cache,loader,"{{","}}");
		}

		public Builder<K> cacheWith(TemplateCacheManager<K> cache){
			this.cache = cache;
			return this;
		}

		public Builder<K> loadWith(TemplateLoader<K> loader){
			this.loader = loader;
			return this;
		}
	}
}
