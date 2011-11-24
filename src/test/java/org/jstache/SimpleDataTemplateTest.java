package org.jstache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SimpleDataTemplateTest{
    public static Map<String,Object> data = new HashMap<String,Object>();
    public static final Hello bean = new Hello();

    @BeforeClass
    public static void setupData(){
        data.put("name","Sophie");
        data.put("greeting","Hola");
        data.put("true",true);
        data.put("false",false);
    }

    @Test
    public void testVariable(){
        assertEquals("Sophie", Template.parse("{{name}}").render(bean));
    }

    @Test
    public void testTextAndVariable(){
        assertEquals("Hello Sophie!", Template.parse("Hello {{name}}!").render(bean));
    }

    @Test
    public void testManyVariables(){
        assertEquals("Hola Sophie", Template.parse("{{greeting}} {{name}}").render(data));
    }

    @Test
    public void testTrueIfBlock(){
        assertEquals("How are you?", Template.parse("{{#true}}How are you?{{/true}}").render(data));
    }

    @Test
    public void testFalseIfBlock(){
        assertEquals("", Template.parse("{{#false}}How are you?{{/false}}").render(data));
    }

    @Test
    public void testTrueElseBlock(){
        assertEquals("", Template.parse("{{^true}}How are you?{{/true}}").render(data));
    }

    @Test
    public void testFalseElseBlock(){
        assertEquals("How are you?", Template.parse("{{^false}}How are you?{{/false}}").render(data));
    }

    @Test
    public void testTrueIfBlockWithVariable(){
        assertEquals("Hey Sophie, how are you?", Template.parse("{{#true}}Hey {{name}}, how are you?{{/true}}").render(data));
    }

    @Test
    public void testTrueIfElseBlocks(){
        assertEquals("Hello, how are you?", Template.parse("Hello, {{#true}}how are you?{{/true}}{{^true}}{{name}}{{/true}}").render(data));
    }

    @Test
    public void testFalseIfElseBlocks(){
        Template template = Template.parse("Hello, {{#que}}how are you?{{/que}}{{^que}}{{name}}{{/que}}");
        assertEquals("Hello, Sophie", template.render(data));
    }

    @Test
    public void testTruthyIfBlock(){
        Template template = Template.parse("{{#greeting}}yeah{{/greeting}} yeah");
        assertEquals("yeah yeah", template.render(bean));
    }

    @Test
    public void testNullIfBlock(){
        Template template = Template.parse("{{#thing}}yeah{{/thing}} yeah");
        assertEquals(" yeah", template.render(data));
    }

    @Test
    public void testLoopListBlockTemplate(){
        Template template = Template.parse("My dogs are:{{#dogs}} {{.}}{{/dogs}}");
        assertEquals("My dogs are: Sophie Riley Duke Schultz Arthur", template.render(bean));
    }

    public static class Hello{

        public String getName(){
            return "Sophie";
        }

        public String getGreeting(){
            return "Hola";
        }

        public boolean isYeah(){
            return true;
        }

        public String getThing(){
            return null;
        }

        public List<String> getDogs(){
            List<String> dogs=new ArrayList<String>();
            dogs.add("Sophie");
            dogs.add("Riley");
            dogs.add("Duke");
            dogs.add("Schultz");
            dogs.add("Arthur");
            return dogs;
        }
    }
}

