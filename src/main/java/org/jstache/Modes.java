package org.jstache;

/**
 *
 */
public class Modes{
    private Modes(){}

    /**
     *
     */
    public static final Mode DEFAULT = new Mode(){

        /**
         * Returns the string untouched.
         */
        @Override
        public String escape(String input){
            return input;
        }
    };

    /**
     *
     */
    public static final Mode HTML = new Mode(){

        /**
         *
         */
        @Override
        public String escape(String input){
            // TODO actually escape the html
            return input;
        }
    };
}

