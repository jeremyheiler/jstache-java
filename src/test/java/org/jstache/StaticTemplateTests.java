package org.jstache;

import org.junit.Test;

import static org.jstache.TestConstants.emptyMap;
import static org.junit.Assert.assertTrue;

public class StaticTemplateTests{

	@Test
	public void testEmptyTemplate(){
		assertTrue(Template.parse("").render(emptyMap).equals(""));
	}

	@Test
	public void testNonEmptyTemplate(){
		Template template = Template.parse("hello");
		String output = template.render(emptyMap);
		assertTrue(output.equals("hello"));
	}

	@Test
	public void testEvilStringTemplate(){
		assertTrue(Template.parse("hi{there").render(emptyMap).equals("hi{there"));
	}
}
