package org.jstache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * <p>Allows you to configure a {@link Parser} that is thread-safe. The builder will cache the last parser it builds until the
 * state of the builder changes. Otherwise, after the builder is modified, each invocation of <code>parse()</code> or
 * <code>build()</code> will build a new parser based on the current state of the builder. This class is <b>not</b>
 * thread-safe.</p>
 */
public class ParserBuilder{
    private volatile Parser parser;
    private Charset encoding;
    private String begin;
    private String end;
    private Mode mode;

    /**
     *
     */
    public ParserBuilder(){
        this.encoding = Template.DEFAULT_ENCODING;
        this.begin = Template.DEFAULT_BEGIN;
        this.end = Template.DEFAULT_END;
        this.mode = Template.DEFAULT_MODE;
    }

    /**
     *
     */
    public ParserBuilder with(String begin, String end){
        this.parser = null;
        this.begin = begin;
        this.end = end;
        return this;
    }

    /**
     *
     */
    public ParserBuilder encodedAs(Charset encoding){
        this.parser = null;
        this.encoding = encoding;
        return this;
    }

    /**
     *
     */
    public ParserBuilder inMode(Mode mode){
        this.parser = null;
        this.mode = mode;
        return this;
    }

    /**
     *
     */
    public Template parse(InputStream source){
        return build().parse(source);
    }

    /**
     *
     */
    public Template parse(String source){
        return build().parse(source);
    }

    /**
     *
     */
    public Template parse(File source) throws FileNotFoundException{
        return build().parse(source);
    }

    /**
     *
     */
    public Template parse(URL source) throws IOException{
        return build().parse(source);
    }

    /**
     * <p>Builds a {@link Parser} based on the current configuration of the builder. The returned Parser is immutable,
     * so if the configuration in the builder doesn't change, the previously built Parser will be returned.</p>
     */
    public Parser build(){
        if(parser == null){
            parser = new Parser(encoding, begin, end, mode);
        }
        return parser;
    }
}

