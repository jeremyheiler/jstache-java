package org.jstache;

/**
 *
 */
public class RenderException extends RuntimeException{
	private static final long serialVersionUID=1L;

	/**
	 *
	 */
	public RenderException(){
		super();
	}

	/**
	 *
	 * @param message
	 */
	public RenderException(String message){
		super(message);
	}

	/**
	 *
	 * @param message
	 * @param throwable
	 */
	public RenderException(String message,Throwable throwable){
		super(message,throwable);
	}
}
