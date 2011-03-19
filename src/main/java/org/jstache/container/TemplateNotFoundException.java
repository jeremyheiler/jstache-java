package org.jstache.container;

public class TemplateNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public TemplateNotFoundException(){
		super();
	}

	public TemplateNotFoundException(String message){
		super(message);
	}

	public TemplateNotFoundException(String message,Throwable throwable){
		super(message,throwable);
	}
}
