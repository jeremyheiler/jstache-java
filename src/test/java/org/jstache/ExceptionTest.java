package org.jstache;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class ExceptionTest{

    @Test(expected=ParseException.class)
    public void testNoArgPE(){
        ParseException pe = new ParseException();
        assertTrue(pe instanceof RuntimeException);
        throw pe;
    }

    @Test
    public void testMessagePE(){
        assertEquals("message", new ParseException("message").getMessage());
    }

    @Test
    public void testMessageCausePE(){
        UnsupportedOperationException uoe = new UnsupportedOperationException();
        ParseException pe = new ParseException("message", uoe);
        assertEquals("message", pe.getMessage());
        assertSame(uoe, pe.getCause());
    }

    @Test
    public void testCausePE(){
        UnsupportedOperationException uoe = new UnsupportedOperationException();
        ParseException pe = new ParseException(uoe);
        assertSame(uoe, pe.getCause());
    }

    @Test(expected=RenderException.class)
    public void testNoArgRE(){
        RenderException re = new RenderException();
        assertTrue(re instanceof RuntimeException);
        throw re;
    }

    @Test
    public void testMessageRE(){
        assertEquals("message", new RenderException("message").getMessage());
    }

    @Test
    public void testMessageCauseRE(){
        UnsupportedOperationException uoe = new UnsupportedOperationException();
        RenderException re = new RenderException("message", uoe);
        assertEquals("message", re.getMessage());
        assertSame(uoe, re.getCause());
    }

    @Test
    public void testCauseRE(){
        UnsupportedOperationException uoe = new UnsupportedOperationException();
        RenderException re = new RenderException(uoe);
        assertSame(uoe, re.getCause());
    }
}

