package org.jstache.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringsTest{

    @Test
    public void simple(){
        assertEquals("simple", Strings.toCamelCase("simple"));
    }

    @Test
    public void twoWords(){
        assertEquals("twoWords", Strings.toCamelCase("two_words"));
    }

    @Test
    public void manyWords(){
        assertEquals("thisIsATestForManyWords", Strings.toCamelCase("this_is_a_test_for_many_words"));
    }
}

