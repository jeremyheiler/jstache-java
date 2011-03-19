package org.jstache.internal;

import java.util.List;
import org.jstache.Element;
import org.jstache.Presenter;

public final class TemplateRenderer{
	private final List<Element> elements;
	private final Presenter presenter;

	public TemplateRenderer(List<Element> elements,Presenter presenter){
		this.elements = elements;
		this.presenter = presenter;
	}

	public String execute(){
		final StringBuilder buf=new StringBuilder();
		for(Element token:elements)
			buf.append(token.toString(presenter));
		return buf.toString();
	}
}
