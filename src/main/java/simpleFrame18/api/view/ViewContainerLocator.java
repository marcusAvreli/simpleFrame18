package simpleFrame18.api.view;


/**
 * This class helps to find a given view container by its name
 * 
 * @author Mario Garcia
 *
 * @param <T> Type of the view container searched
 */
public class ViewContainerLocator<T> {

	private ViewManager viewManager;
	private Class<T> clazz;
	
	/**
	 * @param clazz
	 * @param viewManager
	 */
	public ViewContainerLocator(Class<T> clazz,ViewManager viewManager){
		this.clazz = clazz;
		this.viewManager = viewManager;
	}
	
	/**
	 * @param name
	 * @return
	 */
	public T named(String name){
		return clazz.cast(this.viewManager.getViews().get(name));
	}	
}