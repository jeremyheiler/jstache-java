package org.jstache;

import org.junit.Test;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

public class ParserBuilderTest{

    @Test
    public void testParserIsCached(){
        ParserBuilder builder = new ParserBuilder();
        Parser p1 = builder.build();
        Parser p2 = builder.build();
        assertSame(p1, p2);
    }

    @Test
    public void testParserIsRebuilt(){
        ParserBuilder builder = new ParserBuilder();
        Parser p1 = builder.build();
        Parser p2 = builder.with("${", "}").build();
        assertNotSame(p1, p2);
    }
}

