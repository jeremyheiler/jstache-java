package org.jstache.internal;

import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jstache.ParseException;

/**
 * A TemplateParser is an object, given a string of text, that will parse the
 * text into tokens according to the WIT Template grammar.
 */
public final class Parser{
	private final List<TokenItem> tokens;

	public Parser(String template){
		tokens = Collections.unmodifiableList(new Lexer(template).run());
	}

	public Parser(Reader template){
		try{
			StringBuilder builder=new StringBuilder();
			char[] cbuf=new char[1024];
			int read=0;
			while((read=template.read(cbuf))!=-1){
				builder.append(cbuf,0,read);
			}
			tokens = Collections.unmodifiableList(new Lexer(builder.toString()).run());
		}
		catch(IOException e){
			throw new RuntimeException(e);
		}
	}

	public BlockElement execute(){
		BlockElement root = new BlockElement("__root__");
		Iterator<TokenItem> items = tokens.iterator();
		execute(items,root);
		return root;
	}

	private void execute(Iterator<TokenItem> items,BlockElement block){
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
				execute(items,next);
			}
			else if(token == Token.INVERTED){
				BlockElement next = new BlockElement(item.getValue(),true);
				block.add(next);
				execute(items,next);
			}
			else if(token == Token.END){
				if(item.getValue().equals(block.getKey())){
					return;
				}
				else{
					throw new ParseException("Found {{"+item.getValue()+"}} but expected {{"+block.getKey()+"}}.");
				}
			}
		}
		if(!block.getKey().equals("__root__")){
			throw new ParseException("Expecting a close delimiter");
		}
	}
}
