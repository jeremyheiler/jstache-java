package org.jstache.provider;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QueryStringProviderTest{

    @Test
    public void simpleKeyValue(){
        String query = "name=Sophie";
        Provider provider = new QueryStringProvider(query);
        assertEquals("Sophie", provider.get("name"));
    }

    @Test
    public void simpleEncoded(){
        String query = "greeting=hi%20there";
        Provider provider = new QueryStringProvider(query);
        assertEquals("hi there", provider.get("greeting"));
    }

    @Test
    public void array() throws UnsupportedEncodingException{
        String query = "name=Sophie&name=Riley";
        Provider provider = new QueryStringProvider(query);
        @SuppressWarnings("unchecked")
        List<String> names = (List<String>) provider.get("name");
        assertEquals(names.size(), 2);
        assertEquals("Sophie", names.get(0));
        assertEquals("Riley", names.get(1));
    }
}

