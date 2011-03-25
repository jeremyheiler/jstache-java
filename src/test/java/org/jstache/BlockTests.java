package org.jstache;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BlockTests{
	private final Foo bean=new Foo();

	@Test
	public void testTrueBoolean(){
		Template t=Template.parse("Are you{{#really_bad}} really{{/really_bad}} late!?");
		assertEquals("Are you really late!?",t.render(bean));
	}

	@Test
	public void testFalseBoolean(){
		Template t=Template.parse("Are you{{#em}} really{{/em}} late!?");
		assertEquals("Are you late!?",t.render(bean));
	}

	@Test
	public void testIterable(){
		Template t=Template.parse("Schnauzers:{{#dogs}} {{.}}{{/dogs}}");
		assertEquals("Schnauzers: Sophie Riley Duke Schultz Arthur",t.render(bean));
	}

	@Test
	public void testNumber(){
		Template t=Template.parse("Count:{{#count}} {{.}}{{/count}}");
		assertEquals("Count: 1 2 3 4 5",t.render(bean));
	}

	public static class Foo{

		public boolean isReallyBad(){
			return true;
		}

		public boolean isEm(){
			return false;
		}

		public List<String> getDogs(){
			List<String> dogs=new LinkedList<String>();
			dogs.add("Sophie");
			dogs.add("Riley");
			dogs.add("Duke");
			dogs.add("Schultz");
			dogs.add("Arthur");
			return dogs;
		}

		public int getCount(){
			return 5;
		}
	}
}