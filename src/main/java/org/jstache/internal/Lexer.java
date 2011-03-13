package org.jstache.internal;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.jstache.ParseException;

public class Lexer{
	private final Pattern keyPattern = Pattern.compile("[#/^]?[a-zA-Z_][a-zA-Z0-9_]*");
	private final List<TokenItem> tokens = new ArrayList<TokenItem>();
	private final CharBuffer begin;
	private final CharBuffer end;
	private final CharBuffer buf;

	public Lexer(String template,String begin,String end){
		buf = CharBuffer.wrap(template).asReadOnlyBuffer();
		this.begin = CharBuffer.wrap(begin);
		this.end = CharBuffer.wrap(end);
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
		for(int i=0; i<begin.length() && buf.hasRemaining(); ++i){
			if(buf.charAt(i) != begin.charAt(i)){
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
		buf.limit(buf.capacity()).position(position + begin.length()).mark();
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
		for(int i=0; i<end.length() && buf.hasRemaining(); ++i){
			if(buf.charAt(i) != end.charAt(i)){
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
			throw new ParseException("The tag "+begin+key+end+" is invalid.");
		}
		tokens.add(new TokenItem(type,key));
		buf.limit(buf.capacity()).position(position + end.length()).mark();
		return true;
	}
}