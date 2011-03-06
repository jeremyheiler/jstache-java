package org.jstache.internal;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import org.jstache.ParseException;

import static org.jstache.Constants.CLOSE;
import static org.jstache.Constants.OPEN;

/**
 * A TemplateParser is an object, given a string of text, that will parse the
 * text into tokens according to the WIT Template grammar.
 */
public final class Parser{
	private static final Pattern tagKeyRegex=Pattern.compile("[#/^]?\\w*");
	private final BlockStack stack=new BlockStack();
	private final TemplateReader reader;

	public Parser(String template){
		reader=new TemplateStringReader(template);
	}

	public Parser(Reader template){
		try{
			StringBuilder builder=new StringBuilder();
			char[] cbuf=new char[1024];
			int read=0;
			while((read=template.read(cbuf))!=-1){
				builder.append(cbuf,0,read);
			}
			reader = new TemplateStringReader(builder.toString());
		}
		catch(IOException e){
			throw new RuntimeException(e);
		}
	}

	public BlockElement execute(){
		while(reader.hasRemaining())
			findOpenDelim();
		return stack.getRootBlock();
	}

	private void findOpenDelim(){
		reader.mark();
		while(reader.hasRemaining()){
			char c=reader.get();
			if(c==OPEN[0] && reader.rewind(1).match(OPEN)){
				String token=reader.sliceExcept(OPEN.length);
				if(!token.isEmpty())
					stack.peek().add(new StringElement(token));
				findCloseDelim();
			}
		}
		String token=reader.slice();
		if(!token.isEmpty()){
			stack.peek().add(new StringElement(token));
		}
	}

	private void findCloseDelim(){
		reader.mark();
		while(reader.hasRemaining()){
			char c=reader.get();
			if(c==CLOSE[0] && reader.rewind(1).match(CLOSE)){
				String token=reader.sliceExcept(CLOSE.length);
				if(token.isEmpty()){
					throw new ParseException("cannot have an empty key");
				}
				if(!tagKeyRegex.matcher(token).matches()){
					throw new ParseException("token key may only contain word characters");
				}
				char head=token.charAt(0);
				if(head=='#'){
					BlockElement block=new BlockElement(token.substring(1));
					stack.peek().add(block);
					stack.push(block);
				}
				else if(head=='^'){
					BlockElement block=new BlockElement(token.substring(1),true);
					stack.peek().add(block);
					stack.push(block);
				}
				else if(head=='/'){
					stack.pop();
				}
				else{
					stack.peek().add(new VariableElement(token));
				}
				reader.mark();
				return;
			}
		}
		// Reached the end and didn't find a close delim...
		throw new ParseException("Expecting a close delimiter");
	}

	/**
	 * Aids in parsing the template by providing scope for template blocks.
	 *
	 * There will always be one block on the stack, namely the implicit root
	 * block. This block represent the entire document and cannot be removed.
	 */
	class BlockStack{
		private final List<BlockElement> stack = new LinkedList<BlockElement>();

		/**
		 * Creates a block stack with an implicit root.
		 */
		public BlockStack(){
			stack.add(new BlockElement("__root__"));
		}

		/**
		 * Adds the given block to the top of the stack.
		 */
		public void push(BlockElement block){
			stack.add(0,block);
		}

		/**
		 * Returns a reference to the block at the top of the stack.
		 */
		public BlockElement peek(){
			return (BlockElement) stack.get(0);
		}

		/**
		 * This method acts like peek() if there is only one block token on the
		 * stack. Otherwise this will remove the block token from the topic of the
		 * stack and return it.
		 */
		public BlockElement pop(){
			return (BlockElement) (stack.size()==1 ? stack.get(0) : stack.remove(0));
		}

		/**
		 * Returns the block token at the bottom of the stack. This is called the
		 * root block and will always be at the bottom of the stack.
		 */
		public BlockElement getRootBlock(){
			return stack.get(stack.size()-1);
		}

		public void asdf(){

		}
	}
}
