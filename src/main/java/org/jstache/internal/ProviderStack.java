package org.jstache.internal;

import org.jstache.provider.Provider;

import java.util.LinkedList;
import java.util.List;

public class ProviderStack{
	private final List<Provider> stack=new LinkedList<Provider>();

	public Provider peek(){
		return stack.get(0);
	}

	public void push(Provider provider){
		stack.add(0,provider);
	}

	public Provider pop(){
		return stack.remove(0);
	}
}
