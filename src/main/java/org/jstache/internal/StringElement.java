package org.jstache.internal;


/**
 * An element that represents a literal string value.
 */
public class StringElement implements Element{
	private final String value;

	public StringElement(String value){
		this.value=value;
	}

	public String getValue(){
		return value;
	}

	@Override
	public void render(StringBuilder buffer,ProviderStack stack){
		buffer.append(value);
	}

	@Override
	public String toString(){
		return value;
	}
}
