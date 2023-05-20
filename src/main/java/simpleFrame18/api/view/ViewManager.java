package simpleFrame18.api.view;

import java.awt.Container;
import java.util.Map;

import simpleFrame18.api.core.ApplicationAware;
import simpleFrame18.api.view.perspective.Perspective;
import simpleFrame18.api.view.perspective.PerspectiveConstraint;

//https://github.com/mariogarcia/viewa/blob/c39f7f46dc39908bd23cd4ded0b60c5f555617b8/api/src/main/java/org/viewaframework/view/ViewManager.java
/**
 * This interface should be implemented by classes used for managing
 * several views.<br/><br/>
 * Views can be added to the manager and then re-arranged before it can
 * be shown.
 * 
 * @author Mario Garcia
 * @since 1.0
 *
 */
public interface ViewManager extends ApplicationAware
{
	public static final String ROOT_VIEW_ID = "rootViewId";

	/**
	 * Adds a view to the manager
	 * 
	 * @param view
	 * @throws
	 */
	public void addView(ViewContainer view) throws ViewException;
	
	/**
	 * Adds a view to the manager given it a specific view constraint
	 * 
	 * @param view
	 * @param constraint
	 * @throws ViewException
	 */
	public void addView(ViewContainer view,PerspectiveConstraint constraint) throws ViewException;
	
	/**
	 * Arranges the views contained in the manager. It would be
	 * called by the application before the views can be viewed.
	 * 
	 * @return
	 */
	public Container arrangeViews() throws ViewManagerException;
	
	/**
	 * @return
	 */
	public Perspective getPerspective();
	
	//[ID:2895658]: getRootView() from ViewManager not to throw and Exception
	/**
	 * @return
	 */
	public ViewContainerFrame getRootView();
	
	/**
	 * @return
	 */
	public Map<Object,ViewContainer> getViews();
	
	/**
	 * It removes the view passed as parameter from the manager.
	 * 
	 * @param view
	 */
	public void removeView(ViewContainer view) throws ViewException;
	
	/**
	 * @param viewLayout
	 */
	public void setPerspective(Perspective perspective) throws ViewException;
	
	
	/**
	 * Sets the main view
	 * 
	 * @param view
	 * @throws ViewException
	 */
	public void setRootView(ViewContainerFrame view) throws ViewException;
	
	
}