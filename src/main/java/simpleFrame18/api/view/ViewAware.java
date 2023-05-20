package simpleFrame18.api.view;

import java.util.Map;

import simpleFrame18.api.view.perspective.PerspectiveConstraint;

/**
 * This interface is intented to be used for those classes that are
 * going to host views.
 * 
 * @author Mario Garcia
 * @since 1.0
 *
 */
public interface ViewAware {
	
	/**
	 * Add a view to the current perspective 
	 * 
	 * @param view
	 */
	public void addView(ViewContainer view);
	
	/**
	 * From version 1.0.2 all constraints must be included in PerspectiveConstraint enumeration
	 * 
	 * @param view
	 * @param constraint
	 * @since 1.0.2
	 */
	public void addView(ViewContainer view,PerspectiveConstraint constraint);
	
	/**
	 * Returns all views within this perspective
	 * 
	 * @return
	 */
	public Map<Object,ViewContainer> getViews();
	
	/**
	 * Remove the given view from the current perspective
	 * 
	 * @param view
	 */
	public void removeView(ViewContainer view);
	/**
	 * Sets all views used by this perspective
	 * 
	 * @param views
	 */
	public void setViews(Map<Object,ViewContainer> views);
}