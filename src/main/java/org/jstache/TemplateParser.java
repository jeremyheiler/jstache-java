package org.jstache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jstache.internal.BlockElement;
import org.jstache.internal.StringElement;
import org.jstache.internal.TemplateLexer;
import org.jstache.internal.Token;
import org.jstache.internal.TokenItem;
import org.jstache.internal.VariableElement;

/**
 * A TemplateParser is an object, given a string of text, that will parse the
 * text into tokens according to the WIT Template grammar.
 */
public final class TemplateParser{
	private final Charset encoding;
	private final String begin;
	private final String end;
	private final Mode mode;

	public TemplateParser(Charset encoding,String begin,String end,Mode mode){
		this.encoding = encoding;
		this.begin = begin;
		this.end = end;
		this.mode = mode;
	}

	public Template parse(InputStream source){
		Parser parser = new Parser(new InputStreamReader(source,encoding));
		return new Template(parser.parse(),encoding,begin,end,mode);
	}

	public Template parse(String source){
		return new Template(new Parser(source).parse(),encoding,begin,end,mode);
	}

	public Template parse(File source) throws FileNotFoundException{
		return parse(new FileInputStream(source));
	}

	public Template parse(URL source) throws IOException{
		return parse(source.openStream());
	}

	public TemplateBuilder generateBuilder(){
		return new TemplateBuilder().with(begin,end).encodedAs(encoding).inMode(mode);
	}

	private class Parser{
		private final List<TokenItem> tokens;

		Parser(String source){
			tokens = Collections.unmodifiableList(new TemplateLexer(source,begin,end).run());
		}

		Parser(Reader source){
			try{
				StringBuilder builder=new StringBuilder();
				char[] cbuf=new char[1024];
				int read=0;
				while((read=source.read(cbuf))!=-1){
					builder.append(cbuf,0,read);
				}
				tokens = Collections.unmodifiableList(new TemplateLexer(builder.toString(),begin,end).run());
			}
			catch(IOException e){
				throw new RuntimeException(e);
			}
		}

		BlockElement parse(){
			BlockElement root = new BlockElement("__root__");
			Iterator<TokenItem> items = tokens.iterator();
			parse(items,root);
			return root;
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
					BlockElement next = new BlockElement(item.getValue(),true);
					block.add(next);
					parse(items,next);
				}
				else if(token == Token.ESCAPE){
					block.add(new VariableElement(mode.escape(item.getValue())));
				}
				else if(token == Token.END){
					if(item.getValue().equals(block.getKey())){
						return;
					}
					else{
						throw new ParseException("Found "+begin+item.getValue()+end+" but expected "+begin+block.getKey()+end+".");
					}
				}
			}
			if(!block.getKey().equals("__root__")){
				throw new ParseException("Expecting a close delimiter");
			}
		}
	}
}
