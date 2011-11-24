package org.jstache;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VariableTest{
    private final Hello bean = new Hello();

    @Test
    public void testString(){
        Template t = Template.parse("Hello {{name}}!");
        assertEquals("Hello Sophie!", t.render(bean));
    }

    @Test
    public void testToString(){
        Template t = Template.parse("{{hello}} world!");
        assertEquals("Hello world!", t.render(bean));
    }

    public static class Hello{

        public String getName(){
            return "Sophie";
        }

        public Hello getHello(){
            return this;
        }

        @Override
        public String toString(){
            return "Hello";
        }
    }
}

