package simpleFrame18.api.view;


import java.awt.Image;
import java.util.List;

import javax.swing.JToolBar;
import javax.swing.RootPaneContainer;

import simpleFrame18.api.controller.ViewControllerAware;
import simpleFrame18.api.core.ApplicationAware;
import simpleFrame18.api.core.MessageAware;
import simpleFrame18.api.model.ViewModelAware;
import simpleFrame18.api.model.ViewModelManagerAware;
import simpleFrame18.api.view.event.ViewContainerEventAware;
import simpleFrame18.api.view.event.ViewContainerEventControllerAware;


/**
 * This class it is the container of all graphical components.
 * 
 * @author Mario Garcia
 *
 */
//https://github.com/mariogarcia/viewa/blob/c39f7f46dc39908bd23cd4ded0b60c5f555617b8/api/src/main/java/org/viewaframework/view/ViewContainer.java
public interface ViewContainer  extends ViewContainerEventAware,ViewContainerEventControllerAware,ApplicationAware,ComponentsAware,ViewControllerAware ,RootPaneContainer,MessageAware,ViewModelAware
{
	public static final String CONTENTPANE 	= "contentPane";
	public static final String FRAME		= "frame";
	public static final String MENUBAR		= "menuBar";
	public static final String ROOTPANE 	= "rootPane";
	public static final String TOOLBAR 		= "toolBar";
	
	
	public List<ViewActionDescriptor> getActionDescriptors();
	/**
	 * Returns the image that represents this view
	 * 
	 * @return
	 */
	public Image getIconImage();
	
	/**
	 * Returns the id of the view
	 * 
	 * @return the name
	 */
	public abstract String getId();
	
	/**
	 * @return
	 */
	public JToolBar getJToolBar();
	
	/**
	 * Returns the title of the view
	 * 
	 * @return
	 */
	public abstract String getTitle();
	

	
	/**
	 * Sets a representative image for this view
	 * 
	 * @param image
	 */
	public void setIconImage(Image image);
	
	/**
	 * Sets the name view.
	 * 
	 * @param name the id to set
	 */
	public abstract void setId(String name);		
	
	/**
	 * @param toolBar
	 */
	public void setJToolbar(JToolBar toolBar);

	/**
	 * Sets the title of the view
	 * 
	 * @param title
	 */
	public abstract void setTitle(String title);

	/**
	 * Closes the view
	 */
	public abstract void viewClose() throws ViewException;

	/**
	 * Once background actions has been performed we can establish the 
	 * final aspect of the view within this method.
	 * 
	 * @throws ViewException
	 */
	public abstract void viewFinalUIState() throws ViewException;
	
	/**
	 * Initializes the view. It should be implemented by an
	 * abstract class and it should call sequentially to the
	 * following methods.<br/><br/>
	 * 
	 * viewInitUIState();<br/>
	 * viewInitBackActions();<br/>
	 * viewFinalUIState();<br/>
	 * 
	 */
	public abstract void viewInit() throws ViewException;
	
	/**
	 * Background actions affecting the final view.
	 * Actions that could freeze the UI, should be done here.
	 * 
	 * @throws ViewException
	 */
	public abstract void viewInitBackActions() throws ViewException;
	
	/**
	 * How the view is visualized before background actions are performed.
	 * For example, if we want to keep disabled the UI until some
	 * database actions finished we should do it here.
	 * 
	 * @throws ViewException
	 */
	public abstract void viewInitUIState() throws ViewException;

}