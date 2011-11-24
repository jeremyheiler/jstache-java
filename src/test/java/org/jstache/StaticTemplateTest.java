package org.jstache;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StaticTemplateTest{
    private final static Object NULL = new Object();

    @Test
    public void testEmptyTemplate(){
        assertEquals("", Template.parse("").render(NULL));
    }

    @Test
    public void testNonEmptyTemplate(){
        assertEquals("hello", Template.parse("hello").render(NULL));
    }

    @Test
    public void testTemplatesWithStrayBraces(){
        assertEquals("hi{there", Template.parse("hi{there").render(NULL));
        assertEquals("hi}there", Template.parse("hi}there").render(NULL));
        assertEquals("hi{the}e", Template.parse("hi{the}e").render(NULL));
    }
}

