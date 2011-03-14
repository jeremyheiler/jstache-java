package org.jstache.container;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import org.jstache.Template;

/**
 * A <tt>TemplateContainer</tt> provides an interface for maintaining a cache of
 * parsed templates. The storage mechanism for the cache is determined by the
 * implementations of this class.
 */
public interface TemplateContainer{
	
	/**
	 * Gets the template identified by the given key from the container. If the
	 * container does not contain the key, the implementer must return null.
	 * 
	 * @param key The key that identifies the template in the container.
	 * @return The template identified by the key, or null if it doesn't exist.
	 */
	public Template get(String key);
	
	/**
	 * Parses the template from the given <tt>Reader</tt> with the containers
	 * template configuration. The template is then put into the container and
	 * returned.
	 * 
	 * @param key The key that identifies the template in the container.
	 * @param input A <tt>Reader</tt> that represents the template.
	 * @return The template identified by the key.
	 */
	public Template put(String key,Reader input);
	
	/**
	 * Parses the template from the given <tt>String</tt> with the containers
	 * template configuration. The template is then put into the container and
	 * returned.
	 * 
	 * @param key The key that identifies the template in the container.
	 * @param input A <tt>String</tt> that represents the template.
	 * @return The template identified by the key.
	 */
	public Template put(String key,String input);
	
	/**
	 * Parses the template from the given <tt>File</tt> with the containers
	 * template configuration. The template is then put into the container and
	 * returned.
	 * 
	 * @param key The key that identifies the template in the container.
	 * @param input A <tt>File</tt> that represents the template.
	 * @return The template identified by the key.
	 */
	public Template put(String key,File input) throws FileNotFoundException;
	
	/**
	 * Parses the template from the given <tt>URL</tt> with the containers
	 * template configuration. The template is then put into the container and
	 * returned.
	 * 
	 * @param key The key that identifies the template in the container.
	 * @param input A <tt>URL</tt> that represents the template.
	 * @return The template identified by the key.
	 */
	public Template put(String key,URL input) throws IOException;
}
