package simpleFrame18.api.core;

import java.util.ResourceBundle;


/**
 * This interface enables text messaging
 * 
 * @author Mario Garcia
 *
 */
public interface MessageAware {

	/**
	 * @param key
	 * @return
	 */
	public String getMessage(String key);
	/**
	 * @return
	 */
	public ResourceBundle getMessageBundle();
	/**
	 * @param messageBundle
	 */
	public void setMessageBundle(ResourceBundle messageBundle);
	
}
