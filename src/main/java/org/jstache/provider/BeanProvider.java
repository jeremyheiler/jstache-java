package org.jstache.provider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import org.jstache.RenderException;
import org.jstache.util.Strings;

/**
 * A provider that supplies a bean object as the data source of a template.
 */
public class BeanProvider<T> implements Provider{
	private final Map<String,Method> mappings=new HashMap<String,Method>();
	private final T bean;

	/**
	 * Constructs a provider that supplies the values of a bean object.
	 *
	 * @param bean The bean to be provided.
	 */
	public BeanProvider(T bean){
		this.bean=bean;
		createMappings();
	}

	private void createMappings(){
		for(Method method:bean.getClass().getMethods()){
			if(method.getParameterTypes().length==0 && Modifier.isPublic(method.getModifiers())){
				final String name=method.getName();
				String key=null;
				if(name.startsWith("get"))
					key=name.substring(3,4).toLowerCase()+name.substring(4);
				else if(method.getName().startsWith("is"))
					key=name.substring(2,3).toLowerCase()+name.substring(3);
				mappings.put(key,method);
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
		String name=Strings.toCamelCase(key);
		Method method=mappings.get(name);
		if(method==null){
			throw new RenderException("The bean "+bean.getClass().getCanonicalName()+
					" does not have a method that corresponds to "+name+".");
		}
		try{
			return method.invoke(bean);
		}
		catch(IllegalAccessException e){
			throw new RuntimeException(e);
		}
		catch(InvocationTargetException e){
			throw new RuntimeException(e.getCause());
		}
	}
}
