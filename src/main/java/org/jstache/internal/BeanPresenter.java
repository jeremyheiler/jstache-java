package org.jstache.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import org.jstache.Presenter;
import org.jstache.util.Strings;

/**
 *
 */
public class BeanPresenter implements Presenter{
	private final Map<String,CachedMethod> mappings=new HashMap<String,CachedMethod>();
	private final Object bean;

	/**
	 *
	 * @param bean
	 */
	public BeanPresenter(Object bean){
		this.bean=bean;
		createMappings();
	}

	// Maps the public getter methods that have no arguments for easy access later.
	// The key is inferred from the method name.
	private void createMappings(){
		for(Method method:bean.getClass().getMethods()){
			if(method.getParameterTypes().length==0 && Modifier.isPublic(method.getModifiers())){
				final String name=method.getName();
				String key=null;
				if(name.startsWith("get"))
					key=name.substring(2,3).toLowerCase()+name.substring(4);
				else if(method.getName().startsWith("is"))
					key=name.substring(1,2).toLowerCase()+name.substring(3);
				mappings.put(key,new CachedMethod(method));
			}
		}
	}

	/**
	 *
	 * @param key
	 * @return
	 */
	@Override
	public Object get(String key){
		CachedMethod cache=mappings.get(Strings.toCamalCase(key));
		return cache == null ? "" : cache.get();
	}

	/**
	 * Caches a method and its returned value when it is dynamically executed.
	 */
	class CachedMethod{
		private final Method method;
		private Object value;

		/**
		 *
		 * @param method
		 */
		public CachedMethod(Method method){
			this.method=method;
		}

		/**
		 * Returns the result, as a string, of the cached method's execution.
		 * The cached method will only execute once per render.
		 * @return The result of the cached method.
		 */
		public Object get(){
			if(value==null){
				try{
					value = method.invoke(bean);
				}
				catch(IllegalAccessException e){
					throw new RuntimeException(e);
				}
				catch(InvocationTargetException e){
					throw new RuntimeException(e.getCause());
				}
			}
			return value;
		}
	}
}
