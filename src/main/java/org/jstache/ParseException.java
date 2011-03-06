package org.jstache;

/**
 *
 */
public class ParseException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public ParseException(){
		super();
	}

	/**
	 *
	 * @param message
	 */
	public ParseException(String message){
		super(message);
	}

	/**
	 *
	 * @param message
	 * @param throwable
	 */
	public ParseException(String message,Throwable throwable){
		super(message,throwable);
	}
}
