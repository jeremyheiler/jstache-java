package org.jstache.provider;

/**
 * Implementations of this interface provide values to a template when rendering it.
 */
public interface Provider{

	/**
	 * Returns the value identified by the given key. The template will
	 * determine how to render the object based on the type of tag the key
	 * represents.
	 *
	 * @param key The name of the tag from the template.
	 * @return The object to be rendered.
	 */
	public Object get(String key);
}
