package org.jstache.internal;

import org.jstache.Element;

/**
 * Extend this class to allow an element to be identified by a key.
 */
public abstract class KeyedElement implements Element{
	protected final String key;

	/**
	 * Used for sub classes to instante the key of the element.
	 * @param key The key identifying the element.
	 */
	public KeyedElement(String key){
		this.key=key;
	}

	/**
	 * Returns the key identifying the element.
	 * @return The key identifying the element.
	 */
	public String getKey(){
		return key;
	}
}
