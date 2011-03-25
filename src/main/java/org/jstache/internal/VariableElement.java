package org.jstache.internal;


public class VariableElement extends NamedElement implements Element{

	public VariableElement(String name){
		super(name);
	}

	@Override
	public void render(StringBuilder buffer,ProviderStack stack){
		Object value=stack.peek().get(name);
		buffer.append(value==null?"":value);
	}
}
