package org.jstache.provider;

import java.util.Iterator;

public class IterableProvider implements Provider{
    private final Iterator<?> iterator;

    public IterableProvider(Iterable<?> iterable){
        iterator = iterable.iterator();
    }

    @Override
    public Object get(String key){
        return key.equals(".")? iterator.next() : "";
    }
}

