package org.jstache.internal;


public abstract class NamedElement implements Element{
	protected final String name;

	public NamedElement(String name){
		this.name=name;
	}

	public String getName(){
		return name;
	}

	@Override
	public String toString(){
		return name;
	}
}
