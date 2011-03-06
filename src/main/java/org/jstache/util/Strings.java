package org.jstache.util;

/**
 * A collection of string utility methods.
 */
public class Strings{
	private Strings(){};

	/**
	 * Capitalizes the first letter of the given string.
	 * @param word The string that you wish to capitalize.
	 * @return The capitalized version of the given string.
	 */
	public static String captialize(String word){
		return word.substring(0,1).toUpperCase()+word.substring(1);
	}

	/**
	 * Converts the given snake case string (with underscores) to camal case.
	 * @param word
	 * @return
	 */
	public static String toCamalCase(String word){
		String[] parts=word.split("_");
		if(parts.length==1)
			return parts[0];
		StringBuilder buf=new StringBuilder(parts[0]);
		for(String part:parts)
			buf.append(Strings.captialize(part));
		return buf.toString();
	}
}
