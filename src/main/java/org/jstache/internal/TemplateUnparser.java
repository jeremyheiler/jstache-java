package org.jstache.internal;

import java.util.List;
import org.jstache.Element;

public class TemplateUnparser{
	private final TemplateBuffer buf;
	private final List<Element> elements;

	public TemplateUnparser(List<Element> elements,String begin,String end){
		buf = new TemplateBuffer(begin,end);
		this.elements = elements;
	}

	public String unparse(){
		for(Element element : elements)
			element.unparse(buf);
		return buf.toString();
	}
}
