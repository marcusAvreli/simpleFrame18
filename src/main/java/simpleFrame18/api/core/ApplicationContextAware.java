package simpleFrame18.api.core;

/**
 * This interface allows Application to have a context. Within that context you can 
 * store and retrieve attributes by their names.
 * 
 * @author Mario Garcia
 *
 */
public interface ApplicationContextAware {

	/**
	 * This method returns the current available ApplicationContext
	 * 
	 * @return
	 */
	public ApplicationContext getApplicationContext();
	/**
	 * This method sets up the ApplicationContext. If there's already an available 
	 * applicationContext it will throw an ApplicationContextException.
	 * 
	 * @param applicationContext The ApplicationContext
	 * @throws ApplicationContextException If there's a previous ApplicationContext
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws ApplicationContextException;
}