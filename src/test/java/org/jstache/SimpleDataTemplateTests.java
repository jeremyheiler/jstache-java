package org.jstache;

import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SimpleDataTemplateTests{
	public static Map<String,Object> data=new HashMap<String,Object>();

	@BeforeClass
	public static void setupData(){
		data.put("name","Sophie");
		data.put("greeting","Hola");
		data.put("question",true);
		data.put("que",false);
	}

	@Test
	public void testVariable(){
		assertTrue(Template.parse("{{name}}").render(data).equals("Sophie"));
	}

	@Test
	public void testTextAndVariable(){
		assertTrue(Template.parse("Hello {{name}}").render(data).equals("Hello Sophie"));
	}

	@Test
	public void testAnotherTextAndVariable(){
		assertTrue(Template.parse("Hello {{name}}!").render(data).equals("Hello Sophie!"));
	}

	@Test
	public void testManyVariables(){
		assertTrue(Template.parse("{{greeting}} {{name}}").render(data).equals("Hola Sophie"));
	}

	@Test
	public void testTrueIfBlock(){
		assertTrue(Template.parse("{{#question}}How are you?{{/question}}").render(data).equals("How are you?"));
	}

	@Test
	public void testFalseIfBlock(){
		assertTrue(Template.parse("{{#que}}How are you?{{/que}}").render(data).equals(""));
	}

	@Test
	public void testTrueElseBlock(){
		assertTrue(Template.parse("{{^question}}How are you?{{/question}}").render(data).equals(""));
	}

	@Test
	public void testFalseElseBlock(){
		assertTrue(Template.parse("{{^que}}How are you?{{/que}}").render(data).equals("How are you?"));
	}

	@Test
	public void testTrueIfBlockWithVariable(){
		assertTrue(Template.parse("{{#question}}Hey {{name}}, how are you?{{/question}}").render(data).equals("Hey Sophie, how are you?"));
	}

	@Test
	public void testTrueIfElseBlocks(){
		Template template=Template.parse("Hello, {{#question}}how are you?{{/question}}{{^question}}{{name}}{{/question}}");
		assertTrue(template.render(data).equals("Hello, how are you?"));
	}

	@Test
	public void testFalseIfElseBlocks(){
		Template template=Template.parse("Hello, {{#que}}how are you?{{/que}}{{^que}}{{name}}{{/que}}");
		String output=template.render(data);
		assertTrue(output.equals("Hello, Sophie"));
	}

	@Test
	public void testTruthyIfBlock(){
		String output=Template.parse("{{#greeting}}yeah{{/greeting}} yeah").render(data);
		assertTrue(output.equals("yeah yeah"));
	}

	@Test
	public void testFalseyIfBlock(){
		String output=Template.parse("{{#greeting}}yeah{{/greeting}} yeah").render(data);
		assertTrue(output.equals("yeah yeah"));
	}

//	@Test
//	public void testLoopListBlockTemplate(){
//		Template template=Template.parse("My dogs are: {{#dogs}}{{name}},{{/dogs}}");
//		final Map<String,Object> data=new HashMap<String,Object>();
//		final List<String> dogs=new ArrayList<String>();
//		dogs.add("Sophie");
//		dogs.add("Riley");
//		dogs.add("Duke");
//		dogs.add("Schultz");
//		dogs.add("Arthur");
//		data.put("dogs",dogs);
//		String result=template.render(data);
//		assertTrue(result.equals("My dogs are: Sophie, Riley, Duke, Schultz, Arthur,"));
//	}
}
