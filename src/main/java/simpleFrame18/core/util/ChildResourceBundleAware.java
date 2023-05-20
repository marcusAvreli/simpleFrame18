package simpleFrame18.core.util;

import java.util.ResourceBundle;

/**
 * This interface enables LastModifiedControl to have a parent ResourceBundle
 * 
 * @author Mario Garcia
 *
 */
public interface ChildResourceBundleAware {

	/**
	 * @param parent
	 */
	public void setParent(ResourceBundle parent);
	/**
	 * @return
	 */
	public ResourceBundle getParent();
	
}