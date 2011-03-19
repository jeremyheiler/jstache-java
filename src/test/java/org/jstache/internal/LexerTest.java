package org.jstache.internal;

import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LexerTest{

	@Test
	public void empty(){
		TemplateLexer lexer = new TemplateLexer("");
		List<TokenItem> tokens = lexer.run();
		assertEquals(0,tokens.size());
	}

	@Test
	public void literal(){
		TemplateLexer lexer = new TemplateLexer("hello");
		List<TokenItem> tokens = lexer.run();
		assertEquals(1,tokens.size());
		assertEquals(Token.LITERAL,tokens.get(0).getToken());
		assertEquals("hello",tokens.get(0).getValue());
	}

	@Test
	public void evilLiteral0(){
		TemplateLexer lexer = new TemplateLexer("hel{lo");
		List<TokenItem> tokens = lexer.run();
		assertEquals(1,tokens.size());
		assertEquals(Token.LITERAL,tokens.get(0).getToken());
		assertEquals("hel{lo",tokens.get(0).getValue());
	}

	@Test
	public void evilLiteral1(){
		TemplateLexer lexer = new TemplateLexer("hel}}lo");
		List<TokenItem> tokens = lexer.run();
		assertEquals(1,tokens.size());
		assertEquals(Token.LITERAL,tokens.get(0).getToken());
		assertEquals("hel}}lo",tokens.get(0).getValue());
	}

	@Test
	public void evilLiteral2(){
		TemplateLexer lexer = new TemplateLexer("hell{o");
		List<TokenItem> tokens = lexer.run();
		assertEquals(1,tokens.size());
		assertEquals(Token.LITERAL,tokens.get(0).getToken());
		assertEquals("hell{o",tokens.get(0).getValue());
	}

	@Test
	public void variable(){
		TemplateLexer lexer = new TemplateLexer("{{name}}");
		List<TokenItem> tokens = lexer.run();
		assertEquals(1,tokens.size());
	}

	@Test
	public void twoVariables0(){
		TemplateLexer lexer = new TemplateLexer("{{name}}{{word}}");
		List<TokenItem> tokens = lexer.run();
		assertEquals(2,tokens.size());
		assertEquals(Token.VARIABLE,tokens.get(0).getToken());
		assertEquals("name",tokens.get(0).getValue());
		assertEquals(Token.VARIABLE,tokens.get(1).getToken());
		assertEquals("word",tokens.get(1).getValue());
	}

	@Test
	public void twoVariables1(){
		TemplateLexer lexer = new TemplateLexer("{{name}}aa{{word}}");
		List<TokenItem> tokens = lexer.run();
		assertEquals(3,tokens.size());
		assertEquals(Token.VARIABLE,tokens.get(0).getToken());
		assertEquals("name",tokens.get(0).getValue());
		assertEquals(Token.LITERAL,tokens.get(1).getToken());
		assertEquals("aa",tokens.get(1).getValue());
		assertEquals(Token.VARIABLE,tokens.get(2).getToken());
		assertEquals("word",tokens.get(2).getValue());
	}

	@Test
	public void greeting(){
		TemplateLexer lexer = new TemplateLexer("Hello {{name}}!");
		List<TokenItem> tokens = lexer.run();
		assertEquals(3,tokens.size());
		assertEquals(Token.LITERAL,tokens.get(0).getToken());
		assertEquals("Hello ",tokens.get(0).getValue());
		assertEquals(Token.VARIABLE,tokens.get(1).getToken());
		assertEquals("name",tokens.get(1).getValue());
		assertEquals(Token.LITERAL,tokens.get(2).getToken());
		assertEquals("!",tokens.get(2).getValue());
	}

	@Test
	public void block(){
		TemplateLexer lexer = new TemplateLexer("Hello{{#yep}} nope{{/yep}}!");
		List<TokenItem> tokens = lexer.run();
		assertEquals(5,tokens.size());
		assertEquals(Token.LITERAL,tokens.get(0).getToken());
		assertEquals("Hello",tokens.get(0).getValue());
		assertEquals(Token.BLOCK,tokens.get(1).getToken());
		assertEquals("yep",tokens.get(1).getValue());
		assertEquals(Token.LITERAL,tokens.get(2).getToken());
		assertEquals(" nope",tokens.get(2).getValue());
		assertEquals(Token.END,tokens.get(3).getToken());
		assertEquals("yep",tokens.get(3).getValue());
		assertEquals(Token.LITERAL,tokens.get(4).getToken());
		assertEquals("!",tokens.get(4).getValue());
	}
}
