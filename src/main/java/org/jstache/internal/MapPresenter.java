package org.jstache.internal;

import java.util.Map;
import org.jstache.Presenter;

/**
 *
 */
public final class MapPresenter implements Presenter{
	private final Map<String,?> data;

	/**
	 *
	 * @param data
	 */
	public MapPresenter(Map<String,?> data){
		this.data=data;
	}

	/**
	 *
	 * @param key
	 */
	@Override
	public Object get(String key){
		return data.get(key);
	}
}
