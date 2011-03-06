package org.jstache.internal;

import org.jstache.Element;
import org.jstache.Presenter;

/**
 * An element that represents a variable in the template. A variable consists of
 * a key that will be replaced with the value mappped to that key.
 */
public class VariableElement extends KeyedElement implements Element{

	/**
	 * Creates an element representing a variable in the template.
	 * @param key The key identifying the element.
	 */
	public VariableElement(String key){
		super(key);
	}

	/**
	 * Returns the value mapped to this element's key.
	 */
	@Override
	public String toString(Presenter presenter){
		return presenter.get(key).toString();
	}
}
