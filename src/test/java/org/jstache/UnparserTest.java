package org.jstache;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnparserTest{

	@Test
	public void literal(){
		String source = "Hello there!";
		assertEquals(source,Template.parse(source).unparse());
	}

	@Test
	public void variable(){
		String source = "{{name}}";
		assertEquals(source,Template.parse(source).unparse());
	}

	@Test
	public void block(){
		String source = "{{#name}}Sophie{{/name}}";
		assertEquals(source,Template.parse(source).unparse());
	}

	@Test
	public void inverted(){
		String source = "{{^name}}Sophie{{/name}}";
		assertEquals(source,Template.parse(source).unparse());
	}
}
