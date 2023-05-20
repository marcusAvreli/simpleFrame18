package simpleFrame18.core.view;

import java.awt.Component;

import javax.swing.JToolBar;

import simpleFrame18.api.view.ViewException;

/**
 * This is a helper class. It helps programmer not to implement certain methods. Also
 * its constructor helps for setting the id directly.
 * 
 * @author Mario García
 *
 */
public class DefaultViewContainer extends AbstractViewContainer{


	/**
	 * 
	 */
	public DefaultViewContainer(){
		super();
	}
	
	/**
	 * @param id
	 * @param component
	 */
	public DefaultViewContainer(String id,Component component){
		this();
		this.setId(id);
		this.setTitle(id);
		this.getContentPane().add(component);
	}
	
	/**
	 * @param id
	 * @param title
	 * @param component
	 */
	public DefaultViewContainer(String id,String title,Component component){
		this();
		this.setId(id);
		this.setTitle(title);
		this.getContentPane().add(component);
	}

	
}