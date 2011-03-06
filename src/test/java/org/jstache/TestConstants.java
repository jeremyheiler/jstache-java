package org.jstache;

import java.util.Collections;
import java.util.Map;

/**
 * This class represents global constants used within the tests.
 */
public final class TestConstants{
	private TestConstants(){}

	/**
	 * A type-safe empty map.
	 */
	@SuppressWarnings("unchecked")
	public static final Map<String,Object> emptyMap = Collections.EMPTY_MAP;
}
