package org.jstache;

import org.junit.Test;

public class InvalidTemplateTest{

    @Test(expected=ParseException.class)
    public void testMalformedKey(){
        Template.parse("Hi {{na!me}}");
    }

    //@Test(expected=ParseException.class)
    public void testMalformedOpen(){
        Template.parse("hi{{there");
    }
}

