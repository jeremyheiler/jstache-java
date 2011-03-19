package org.jstache.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jstache.Element;
import org.jstache.Presenter;

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
public class BlockElement extends KeyedElement implements Element,Iterable<Element>{
	private List<Element> elements=new ArrayList<Element>();
	private final boolean inverted;

	/**
	 * Creates an element representing a non-inverted template block.
	 * @param key The key identifying the element.
	 */
	public BlockElement(String key){
		this(key,false);
	}

	/**
	 * Creates and element representing a block or section of a template with
	 * the given invertedness.
	 * @param key The key identifying the element.
	 */
	public BlockElement(String key,boolean inverted){
		super(key);
		this.inverted=inverted;
	}

	/**
	 * Returns the value mapped to this element's key.
	 */
	@Override
	public String toString(Presenter presenter){
		Object value = presenter.get(key);
		boolean visible = (value != null && value != Boolean.FALSE && value != new Integer(0));

		if(value instanceof Iterable){
			StringBuilder buf = new StringBuilder();
			for(Object thing : (Iterable<?>)value){
				buf.append(new TemplateRenderer(elements,new BeanPresenter(thing)).execute());
			}
			return buf.toString();
		}

		return (visible ^ inverted) ? new TemplateRenderer(elements,presenter).execute() : "";
	}

	public void add(Element element){
		elements.add(element);
	}

	/**
	 * Returns the list of {@link Element}s in the block.
	 * @return The list of {@link Element}s in the block.
	 */
	public List<Element> getElements(){
		return elements;
	}

	/**
	 *
	 */
	@Override
	public Iterator<Element> iterator(){
		return elements.iterator();
	}

	@Override
	public void unparse(TemplateBuffer buf){
		buf.appendBegin().append(inverted?"^":"#").append(key).appendEnd();
		for(Element element : elements){
			element.unparse(buf);
		}
		buf.appendBegin().append("/").append(key).appendEnd();
	}
}
