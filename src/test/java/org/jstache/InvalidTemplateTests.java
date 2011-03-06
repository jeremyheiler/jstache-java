package org.jstache;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class InvalidTemplateTests{

	@Rule
	public ExpectedException thrown=ExpectedException.none();

	@Test
	public void testMalformedKey(){
		thrown.expect(ParseException.class);
		Template.parse("Hi {{na!me}}");
	}

	@Test
	public void testMalformedOpen(){
		thrown.expect(ParseException.class);
		Template.parse("hi{{there");
	}
}
