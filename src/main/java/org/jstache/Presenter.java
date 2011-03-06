package org.jstache;

/**
 * A presenter is used to abstract the way data is given to render a template.
 */
public interface Presenter{

	/**
	 *
	 * @param key
	 * @return
	 */
	public Object get(String key);
}
