package org.jstache;

import org.jstache.internal.TemplateBuffer;

/**
 *
 */
public interface Element{

	/**
	 *
	 * @param presenter
	 * @return The string representation of the element.
	 */
	public String toString(Presenter presenter);

	/**
	 *
	 * @param begin
	 * @param end
	 * @return
	 */
	public void unparse(TemplateBuffer buf);
}
