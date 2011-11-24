package org.jstache.provider;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jstache.ParseException;

/**
 * A provider that supplies a URL query string as the data source of a template.
 */
public class QueryStringProvider implements Provider{
    private final Map<String,List<String>> data = new HashMap<String,List<String>>();

    /**
     * Creates a provider that converts the query string into a map.
     * @param query The query string to parse.
     */
    public QueryStringProvider(String query){
        if(query!=null){
            parse(query);
        }
    }

    /**
     * Returns a <tt>String</tt> or a <tt>List<String></tt> that represents the
     * value(s) of the given key in the query string.
     */
    @Override
    public Object get(String key){
        List<String> values = data.get(key);
        return values.size()==1 ? values.get(0) : values;
    }

    /**
     * Parses an additional query string and adds it to this provider.
     * @param query The additional query string to parse.
     */
    public void parse(String query){
        try{
            if(query.charAt(0)=='?'){
                query = query.substring(1);
            }
            String[] pairs = query.split("&");
            for(String entry : pairs){
                String[] kv = entry.split("=");
                String key = URLDecoder.decode(kv[0],"UTF-8");
                String value = URLDecoder.decode(kv[1],"UTF-8");
                List<String> values = data.get(key);
                if(values==null){
                    values = new ArrayList<String>();
                    data.put(key,values);
                }
                values.add(value);
            }
        }
        catch(UnsupportedEncodingException e){
            throw new ParseException(e.getMessage(),e);
        }
    }
}

