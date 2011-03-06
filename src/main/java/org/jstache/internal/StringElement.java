package org.jstache.internal;

import org.jstache.Element;
import org.jstache.Presenter;

/**
 * An element that wraps a String.
 */
public class StringElement implements Element{
	private final String value;

	/**
	 * Creates an element that wraps the given string.
	 * @param value The string to wrap.
	 */
	public StringElement(String value){
		this.value=value;
	}

	/**
	 * Returns the string that represents the element.
	 * @return The string that represents the element.
	 */
	public String getValue(){
		return value;
	}

	/**
	 * Returns the value of the element. A presenter is not necesary for this
	 * method to function properly, and exists only to comply with the Element
	 * interface.
	 * @param peresenter
	 */
	@Override
	public String toString(Presenter presenter){
		return value;
	}
}
