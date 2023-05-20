package simpleFrame18.api.core;

/**
 *  
* @author Mario Garcia
* @since 1.0
*
*/
public interface ApplicationListener {

	/**
	 * @param event
	 */
	public void onClose(ApplicationEvent event);
	/**
	 * @param event
	 */
	public void onPrepare(ApplicationEvent event);
	/**
	 * @param event
	 */
	public void onPrepareUI(ApplicationEvent event);
	/**
	 * @param event
	 */
	public void onInitUI(ApplicationEvent event);
	
}