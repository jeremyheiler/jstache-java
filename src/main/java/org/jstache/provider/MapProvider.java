package org.jstache.provider;

import java.util.Map;

/**
 * A provider that supplies a <tt>Map</tt> as the data source of a template.
 */
public final class MapProvider implements Provider{
	private final Map<String,?> data;

	/**
	 * Creates a provider that wraps a <tt>Map</tt>.
	 * @param data The <tt>Map</tt> containing the data.
	 */
	public MapProvider(Map<String,?> data){
		this.data=data;
	}

	/**
	 * Calls <tt>Map#get</tt> on the underlying map.
	 */
	@Override
	public Object get(String key){
		return data.get(key);
	}
}
