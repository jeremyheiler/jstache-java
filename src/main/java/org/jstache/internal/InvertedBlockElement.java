package org.jstache.internal;


public class InvertedBlockElement extends BlockElement{

	public InvertedBlockElement(String name){
		super(name);
	}

	@Override
	public void render(StringBuilder buffer,ProviderStack stack){
		Object value=stack.peek().get(name);

		if(value==null || (value instanceof Boolean && !((Boolean)value).booleanValue()) || value==Integer.valueOf(0)){
			renderElements(buffer,stack);
		}
	}
}
