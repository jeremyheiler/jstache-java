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
     */
    public ParseException(String message, Throwable cause){
        super(message, cause);
    }

    /**
     *
     */
    public ParseException(Throwable cause){
        super(cause);
    }
}

