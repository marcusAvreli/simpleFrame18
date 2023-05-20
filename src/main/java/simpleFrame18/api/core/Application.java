package simpleFrame18.api.core;

import java.util.Locale;

import simpleFrame18.api.controller.ViewControllerDispatcherAware;
import simpleFrame18.api.model.ViewModelManagerAware;
import simpleFrame18.api.view.ViewContainerFrame;
import simpleFrame18.api.view.ViewManager;
import simpleFrame18.api.view.ViewManagerException;


/**
 * This is the entry point of any application. From Application object
 * views are loaded and unloaded through its ViewManager.<br/><br/>
 * It is also responsible for connecting views to its controllers 
 * through its ControllerDispatcher.<br/><br/>
 * 
 * Before the first view is shown some background work is done. The
 * application lifecycle starts at <br/><br/>
 * <code>execute(Application)</code><br/><br/>
 * This method launches the the viewManager in the EventDispatcherThread while
 * it creates a new Thread for the main application code.
 * 
 * @author Mario Garcia
 * @since 1.0
 *
 */
public interface Application extends ApplicationListenerAware,
ViewControllerDispatcherAware,ViewModelManagerAware, ApplicationContextAware{

/**
 * 
 */
public void close();

/**
 * The name of the application is not very important, but its used
 * as default title name if the application is frame based.
 * 
 * @return the name of the application
 */
public abstract String getName();

/**
 * This method returns the current locale
 * 
 * @return java.util.Locale
 */
public Locale getLocale();

/**
 * Returns the object responsible for arranging and ordering the views. 
 * 
 * @return The view manager.
 * @see {@link ViewManager}
 */
public ViewManager getViewManager();

/**
 * This method hides or restores the application
 * depending on its actual state.
 * 
 * @return The state after executing the method
 */
public boolean hideOrRestore();

/**
 * This method is responsible for creating and launching the application
 * UI. It should create the UI within the EventDispatchThread.
 * 
 * @throws ApplicationException
 * 
 */
public void initUI() throws ApplicationException;

/**
 * Returns whether the application is visible or not
 * 
 * @return
 */
public boolean isVisible();

/**
 * This method is called before the UI has been shown. This method
 * shouldn't be used for configuring the UI, use configUI() instead.  
 * 
 * @throws ApplicationException
 * 
 */
public void prepare() throws ApplicationException;

/**
 * This method is right before the UI has been shown. It should
 * be used for configuring the application UI.
 * 
 * @throws ApplicationException
 * 
 */	
public void prepareUI() throws ApplicationException;

/**
 * Sets the locale for the application. All ResourceBundle messages could be
 * i18n
 * 
 * @param locale
 */
public void setLocale(Locale locale);

/**
 * Establish the name of the application
 * 
 * @param name the name to set
 */
public abstract void setName(String name);

/**
 * Establish the root view of the application
 * 
 * @param rootView
 * @since 1.0.4
 */
public abstract void setRootView(ViewContainerFrame rootView);

/**
 * Sets the ViewManager
 * 
 * @param manager. If a ViewManager has been already set then an
 * exception should be thrown.
 * @see {@link ViewManager}
 */
public void setViewManager(ViewManager manager) throws ViewManagerException;

/**
 * This method sets the visibility of the root view
 * 
 * @param visible
 * @since 1.0.4
 */
public void setVisible(boolean visible);
}