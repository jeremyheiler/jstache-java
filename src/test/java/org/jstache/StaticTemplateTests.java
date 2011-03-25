package org.jstache;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StaticTemplateTests{
	private Object NULL=new Object();

	@Test
	public void testEmptyTemplate(){
		assertTrue(Template.parse("").render(NULL).equals(""));
	}

	@Test
	public void testNonEmptyTemplate(){
		Template template = Template.parse("hello");
		String output = template.render(NULL);
		assertTrue(output.equals("hello"));
	}

	@Test
	public void testEvilStringTemplate(){
		assertTrue(Template.parse("hi{there").render(NULL).equals("hi{there"));
	}
}
