package simpleFrame18.core.core;

import java.util.HashMap;
import java.util.Map;

import simpleFrame18.api.core.ApplicationContext;

/**
 * This class is a basic implementation of the ApplicationContext
 * 
 * @author Mario Garcia
 * @since 1.0.2
 *
 */
public class DefaultApplicationContext implements ApplicationContext{

	private Map<String,Object> backingMap = new HashMap<String, Object>();

	/* (non-Javadoc)
	 * @see org.viewaframework.core.ApplicationContext#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String arg0) {
		return this.backingMap.get(arg0);
	}

	/* (non-Javadoc)
	 * @see org.viewaframework.core.ApplicationContext#removeAttribute(java.lang.String)
	 */
	public void removeAttribute(String arg0) {
		this.backingMap.remove(arg0);
	}

	/* (non-Javadoc)
	 * @see org.viewaframework.core.ApplicationContext#setAttribute(java.lang.String, java.lang.Object)
	 */
	public void setAttribute(String arg0, Object arg1) {
		this.backingMap.put(arg0,arg1);
	}
}