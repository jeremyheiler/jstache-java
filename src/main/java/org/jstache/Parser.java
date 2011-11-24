package org.jstache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import org.jstache.internal.BlockElement;
import org.jstache.internal.Element;
import org.jstache.internal.InvertedBlockElement;
import org.jstache.internal.StringElement;
import org.jstache.internal.Token;
import org.jstache.internal.TokenItem;
import org.jstache.internal.VariableElement;

/**
 *
 */
public final class Parser{
    private final Charset encoding;
    private final String begin;
    private final String end;
    private final Mode mode;

    /**
     *
     */
    public Parser(Charset encoding, String begin, String end, Mode mode){
        this.encoding = encoding;
        this.begin = begin;
        this.end = end;
        this.mode = mode;
    }

    public Template parse(InputStream source){
        InternalParser parser = new InternalParser(new InputStreamReader(source,encoding));
        return new Template(parser.parse(), encoding, begin, end, mode);
    }

    public Template parse(String source){
        return new Template(new InternalParser(source).parse(),encoding,begin,end,mode);
    }

    public Template parse(File source) throws FileNotFoundException{
        return parse(new FileInputStream(source));
    }

    public Template parse(URL source) throws IOException{
        return parse(source.openStream());
    }

    public ParserBuilder generateBuilder(){
        return new ParserBuilder().with(begin, end).encodedAs(encoding).inMode(mode);
    }

    private final class InternalParser{
        private final List<TokenItem> tokens;

        public InternalParser(String source){
            tokens = Collections.unmodifiableList(new Lexer(source).run());
        }

        public InternalParser(Reader source){
            try{
                StringBuilder builder=new StringBuilder();
                char[] cbuf=new char[1024];
                int read=0;
                while((read=source.read(cbuf))!=-1){
                    builder.append(cbuf,0,read);
                }
                tokens = Collections.unmodifiableList(new Lexer(builder.toString()).run());
            }
            catch(IOException e){
                throw new RuntimeException(e);
            }
        }

        public List<Element> parse(){
            BlockElement root = new BlockElement("__root__");
            parse(tokens.iterator(),root);
            return root.getElements();
        }

        private void parse(Iterator<TokenItem> items,BlockElement block){
            while(items.hasNext()){
                TokenItem item = items.next();
                Token token = item.getToken();
                if(token == Token.LITERAL){
                    block.add(new StringElement(item.getValue()));
                }
                else if(token == Token.VARIABLE){
                    block.add(new VariableElement(item.getValue()));
                }
                else if(token == Token.BLOCK){
                    BlockElement next = new BlockElement(item.getValue());
                    block.add(next);
                    parse(items,next);
                }
                else if(token == Token.INVERTED){
                    InvertedBlockElement next = new InvertedBlockElement(item.getValue());
                    block.add(next);
                    parse(items,next);
                }
                else if(token == Token.ESCAPE){
                    block.add(new VariableElement(mode.escape(item.getValue())));
                }
                else if(token == Token.END){
                    if(item.getValue().equals(block.getName())){
                        return;
                    }
                    else{
                        throw new ParseException("Found "+begin+item.getValue()+end+" but expected "+begin+block.getName()+end+".");
                    }
                }
            }
            if(!block.getName().equals("__root__")){
                throw new ParseException("Expecting a close delimiter");
            }
        }
    }

    private class Lexer{
        private final Pattern keyPattern = Pattern.compile("[#/^]?([a-zA-Z_][a-zA-Z0-9_]*)|\\.");
        private final List<TokenItem> tokens = new ArrayList<TokenItem>();
        private final CharBuffer beginBuf;
        private final CharBuffer endBuf;
        private final CharBuffer buf;

        public Lexer(String template){
            buf = CharBuffer.wrap(template).asReadOnlyBuffer();
            this.beginBuf = CharBuffer.wrap(begin);
            this.endBuf = CharBuffer.wrap(end);
        }

        public List<TokenItem> run(){
            buf.mark();
            while(buf.hasRemaining()){
                findGenericBegin();
            }
            if(buf.reset().position() != buf.limit()){
                tokens.add(new TokenItem(Token.LITERAL,buf.toString()));
            }
            return tokens;
        }

        private void findGenericBegin(){
            for(int i=0; i<beginBuf.length() && buf.hasRemaining(); ++i){
                if(buf.charAt(i) != beginBuf.charAt(i)){
                    buf.get();
                    return;
                }
            }
            int position = buf.position();
            buf.limit(position).reset();
            String literal = buf.toString();
            if(!literal.isEmpty()){
                tokens.add(new TokenItem(Token.LITERAL,literal));
            }
            buf.limit(buf.capacity()).position(position + beginBuf.length()).mark();
            findKey();
        }

        private void findKey(){
            if(buf.hasRemaining()){
                char next = buf.get();
                if(next == '#'){
                    buf.mark();
                    findTag(Token.BLOCK);
                }
                else if(next == '^'){
                    buf.mark();
                    findTag(Token.INVERTED);
                }
                else if(next == '/'){
                    buf.mark();
                    findTag(Token.END);
                }
                else if(next == '&'){
                    buf.mark();
                    findTag(Token.ESCAPE);
                }
                else{
                    findTag(Token.VARIABLE);
                }
            }
        }

        private void findTag(Token type){
            while(buf.hasRemaining()){
                if(findEnd(type)){
                    break;
                }
            }
        }

        private boolean findEnd(Token type){
            for(int i=0; i<endBuf.length() && buf.hasRemaining(); ++i){
                if(buf.charAt(i) != endBuf.charAt(i)){
                    buf.get();
                    return false;
                }
            }
            int position = buf.position();
            buf.limit(position).reset();
            String key = buf.toString();
            if(key.isEmpty()){
                throw new ParseException("An empty key was found.");
            }
            if(!keyPattern.matcher(key).matches()){
                throw new ParseException("The tag "+beginBuf+key+endBuf+" is invalid.");
            }
            tokens.add(new TokenItem(type,key));
            buf.limit(buf.capacity()).position(position + endBuf.length()).mark();
            return true;
        }
    }
}

