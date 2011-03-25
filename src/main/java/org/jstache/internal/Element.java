package org.jstache.internal;


/**
 * An element represents a portion of a template.
 */
public interface Element{

	public void render(StringBuilder buffer,ProviderStack stack);
}
