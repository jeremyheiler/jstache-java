package org.jstache.provider;

public class LiteralProvider implements Provider{
    private Object literal;

    public LiteralProvider(Object literal){
        this.literal=literal;
    }

    @Override
    public Object get(String key){
        return literal;
    }
}

