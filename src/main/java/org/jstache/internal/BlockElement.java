package org.jstache.internal;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.jstache.provider.BeanProvider;
import org.jstache.provider.IterableProvider;
import org.jstache.provider.LiteralProvider;

/**
 * An element that represents a block or section of a template. A block consists
 * of a key, which is mapped to a value that determines if the block is to be
 * displayed zero, one, or many times.
 *
 * A block will not be displayed if its mapped value is false, null, 0, an empty
 * array, or an empty collection. A block will be displayed once if its mapped
 * value is true, 1, any non-Iterable object, or any Iterable object (including
 * arrays) with only one element. Otherwise, an object that is Iterable will
 * display the block for each item in the iteration.
 *
 * An inverted block will be displayed if its mapped value is false, null, 0, or
 * an empty array or Iterable object. Otherwise the block will not be displayed.
 */
public class BlockElement extends NamedElement implements Element,Iterable<Element>{
	protected final List<Element> elements=new LinkedList<Element>();

	public BlockElement(String name){
		super(name);
	}

	@Override
	public void render(StringBuilder buffer,ProviderStack stack){
		Object value=stack.peek().get(name);

		if(value==null || value==Integer.valueOf(0)){
			return;
		}

		if(value instanceof Boolean){
			if(((Boolean)value).booleanValue()){
				renderElements(buffer,stack);
			}
			else{
				return;
			}
		}
		else if(value instanceof Iterable){
			for(Object object:(Iterable<?>)value){
				if(object instanceof Iterable){
					stack.push(new IterableProvider((Iterable<?>)object));
				}
				else if(object instanceof String || object instanceof Number || object instanceof Boolean || object instanceof Character){
					stack.push(new LiteralProvider(object));
				}
				else{
					stack.push(new BeanProvider<Object>(object));
				}
				renderElements(buffer,stack);
				stack.pop();
			}
		}
		else if(value instanceof Integer){
			for(int i=1; i<=(Integer)value; ++i){
				stack.push(new LiteralProvider(i));
				renderElements(buffer,stack);
				stack.pop();
			}
		}
		else{
			renderElements(buffer,stack);
		}
	}

	protected void renderElements(StringBuilder buffer,ProviderStack stack){
		for(Element element:elements){
			element.render(buffer,stack);
		}
	}

	public void add(Element element){
		elements.add(element);
	}

	public List<Element> getElements(){
		return elements;
	}

	@Override
	public Iterator<Element> iterator(){
		return elements.iterator();
	}
}
