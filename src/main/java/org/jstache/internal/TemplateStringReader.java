package org.jstache.internal;

import java.nio.CharBuffer;

public class TemplateStringReader implements TemplateReader{
	private final CharBuffer buf;

	public TemplateStringReader(String template){
		buf=CharBuffer.wrap(template).asReadOnlyBuffer();
	}

	@Override
	public char peek(){
		return buf.charAt(0);
	}

	@Override
	public boolean hasRemaining(){
		return buf.hasRemaining();
	}

	@Override
	public TemplateReader mark(){
		buf.mark();
		return this;
	}

	@Override
	public char get(){
		return buf.get();
	}

	/**
	 * Slices a String from the mark to the current position. The current
	 * position is preserved.
	 */
	@Override
	public String slice(){
		return sliceExcept(0);
	}

	/**
	 * Slices a String from the mark to the current position minus n characters.
	 * This is to prevent the need drop characters from the end of String later.
	 *
	 * The current position is preserved.
	 */
	@Override
	public String sliceExcept(int n){
		int p=buf.position();
		buf.reset();
		CharSequence str=buf.subSequence(0,p-buf.position()-n);
		buf.position(p);
		return str.toString();
	}

	@Override
	public TemplateReader rewind(int n){
		buf.position(buf.position()-n);
		return this;
	}

//	@Override
//	public boolean match(char[] str){
//		for(int i=0; i<str.length; ++i){
//			if(str[i] != buf.get()){
//				this.rewind(i);
//				return false;
//			}
//		}
//		return true;
//	}

	@Override
	public boolean match(char[] str){
		if(buf.remaining() < str.length){
			return false;
		}
		for(int i = 0; i < str.length; ++i){
			if(str[i] != buf.get()){
				rewind(i);
				return false;
			}
		}
		return true;
	}
}
