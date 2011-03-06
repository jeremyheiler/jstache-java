package org.jstache.internal;

/**
 *
 */
public interface TemplateReader{

	/**
	 *
	 * @return
	 */
	public boolean hasRemaining();

	/**
	 *
	 * @return
	 */
	public TemplateReader mark();

	/**
	 *
	 * @return
	 */
	public char get();

	/**
	 *
	 * @param n
	 * @return
	 */
	public TemplateReader rewind(int n);

	/**
	 *
	 * @return
	 */
	public String slice();

	/**
	 *
	 * @param n
	 * @return
	 */
	public String sliceExcept(int n);

	/**
	 *
	 * @param str
	 * @return
	 */
	public boolean match(char[] str);
}
