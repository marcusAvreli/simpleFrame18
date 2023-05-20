package simpleFrame18.api.ioc;

/**
 * This interface should be used when a third party IOC container wants to be exposed inside
 * the application.
 * 
 * @author Mario Garcia
 *
 */
public interface IOCContext {

	/**
	 *  This must be the key that is going to be used to reference the IOCCOntext inside
	 *  the ApplicationContext
	 */
	public static final String ID = IOCContext.class.getCanonicalName();
	/**
	 * This method should return a given bean named equally as the name passed as parameter
	 * 
	 * @param id The name or id of the bean inside the IOC container
	 * @return The object referenced
	 */
	public Object getBean(String id);
	
}