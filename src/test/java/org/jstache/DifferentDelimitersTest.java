package org.jstache;

import static org.junit.Assert.assertEquals;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class DifferentDelimitersTest{

	@Test
	public void jsp(){
		Template template = Template.with("${","}").parse("Hello ${name}");
		Map<String,String> data = new HashMap<String,String>();
		data.put("name","Sophie");
		String output = template.render(data);
		assertEquals("Hello Sophie",output);
	}

	@Test
	public void square(){
		Template template = Template.with("[","]").parse("Hello [name]");
		Map<String,String> data = new HashMap<String,String>();
		data.put("name","Sophie");
		String output = template.render(data);
		assertEquals("Hello Sophie",output);
	}
}
