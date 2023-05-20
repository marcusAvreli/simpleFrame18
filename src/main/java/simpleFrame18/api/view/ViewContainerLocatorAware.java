package simpleFrame18.api.view;

/**
 * 
 * @author Mario Garcia
 *
 */
public interface ViewContainerLocatorAware {

	/**
	 * This method should return a ViewContainerLocator
	 * 
	 * @param <U>
	 * @param clazz
	 * @return
	 */
	public <U extends ViewContainer> ViewContainerLocator<U> locate(Class<U> clazz);
	
	/**
	 * This method should return a ViewContainer with the name passed as parameter
	 * 
	 * @param name
	 * @return
	 */
	public ViewContainer locate(String name);
	
	/**
	 * This method should return the current root view 
	 * 
	 * @return
	 */
	public ViewContainerFrame locateRootView(); 
	
}
