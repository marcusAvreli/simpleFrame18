package simpleFrame18.core.core;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.swing.JFrame;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*
import org.viewaframework.annotation.processor.AnnotationProcessor;
import org.viewaframework.annotation.processor.ViewsPerspectiveProcessor;
import org.viewaframework.annotation.processor.ViewsProcessor;
import org.viewaframework.annotation.processor.ViewsProcessorWrapper;
import org.viewaframework.controller.DefaultViewControllerDispatcher;
import org.viewaframework.controller.ViewControllerDispatcher;
import org.viewaframework.controller.ViewControllerDispatcherException;
import org.viewaframework.model.AbstractViewModelManager;
import org.viewaframework.model.ViewModelManager;
import org.viewaframework.view.DefaultViewManager;
import org.viewaframework.view.ViewContainer;
import org.viewaframework.view.ViewContainerFrame;
import org.viewaframework.view.ViewException;
import org.viewaframework.view.ViewManager;
import org.viewaframework.view.ViewManagerException;
import org.viewaframework.view.perspective.DefaultPerspective;
import org.viewaframework.view.perspective.Perspective;
*/
import simpleFrame18.App;
import simpleFrame18.api.controller.ViewControllerDispatcher;
import simpleFrame18.api.controller.ViewControllerDispatcherException;
import simpleFrame18.api.core.Application;
import simpleFrame18.api.core.ApplicationContext;
import simpleFrame18.api.core.ApplicationContextException;
import simpleFrame18.api.core.ApplicationEvent;
import simpleFrame18.api.core.ApplicationException;
import simpleFrame18.api.core.ApplicationListener;
import simpleFrame18.api.model.ViewModelManager;
import simpleFrame18.api.view.ViewContainer;
import simpleFrame18.api.view.ViewContainerFrame;
import simpleFrame18.api.view.ViewException;
import simpleFrame18.api.view.ViewManager;
import simpleFrame18.api.view.ViewManagerException;
import simpleFrame18.api.view.perspective.Perspective;
import simpleFrame18.core.annotation.processor.AnnotationProcessor;
import simpleFrame18.core.annotation.processor.ViewsPerspectiveProcessor;
import simpleFrame18.core.annotation.processor.ViewsProcessor;
import simpleFrame18.core.annotation.processor.ViewsProcessorWrapper;
import simpleFrame18.core.controller.DefaultViewControllerDispatcher;
import simpleFrame18.core.model.AbstractViewModelManager;
import simpleFrame18.core.view.DefaultViewManager;
import simpleFrame18.core.view.perspective.DefaultPerspective;

/**
 * This is a default implementation of an Application which has
 * a <code>ViewControllerDispatcher</code> as well as a
 * <code>ViewManager</code>.
 * 
 * @author Mario Garcia
 * @since 1.0
 *
 */

//https://github.com/mariogarcia/viewa/blob/c39f7f46dc39908bd23cd4ded0b60c5f555617b8/core/src/main/java/org/viewaframework/core/AbstractApplication.java
public abstract class AbstractApplication implements Application
{
	private static final Logger logger = LoggerFactory.getLogger(AbstractApplication.class);
	private ApplicationContext			applicationContext;
	private List<ApplicationListener>	applicationListeners;
	private ViewControllerDispatcher 	dispatcher;
	private Locale						locale;
	
	private String 						name;
	private ViewContainerFrame			rootView;
	private ViewManager 				viewManager;
	private ViewModelManager			viewModelManager;
	private List<ViewsProcessorWrapper> initViews;
	
	/**
	 * 
	 */
	public AbstractApplication(){
		this.dispatcher 			= new DefaultViewControllerDispatcher();
		this.applicationListeners 	= new ArrayList<ApplicationListener>();
		this.viewManager 			= new DefaultViewManager(this,new DefaultPerspective());
	}
	/**
	 * @param name
	 * @param mainView
	 */
	public AbstractApplication(String name,ViewContainerFrame mainView){
		this();
		this.setName(name);
		this.setRootView(mainView);
	}
	/**
	 * This constructor sets the default root view
	 * 
	 * @param mainView
	 */
	public AbstractApplication(ViewContainerFrame mainView){
		this();
		this.setRootView(mainView);
	}
	/* (non-Javadoc)
	 * @see org.viewaframework.core.ApplicationListenerAware#addApplicationListener(org.viewaframework.core.ApplicationListener)
	 */
	public void addApplicationListener(ApplicationListener listener) {
		this.applicationListeners.add(listener);
	}

	/* (non-Javadoc)
	 * @see org.viewaframework.core.Application#close()
	 */
	public void close() {
		logger.debug("Application closing!");
		this.fireClose(new ApplicationEvent());
	}
	
	/* (non-Javadoc)
	 * @see org.viewaframework.core.ApplicationListenerAware#fireClose(org.viewaframework.core.ApplicationEvent)
	 */
	public void fireClose(ApplicationEvent event) {
	 /* First we execute all related listeners */
		for (ApplicationListener applicationListener : this.applicationListeners){
			applicationListener.onClose(event);
			/* Some kind of vetoableCloseException should being created. If any view
			 * throws an exception like that closing process should be aborted otherwise
			 * the application finishes even if there's other type of exceptions.*/
		}
	 /* Finally we close all children views */
		Collection<ViewContainer> views = this.getViewManager().getViews().values();
	 /* Except the root view */
		ViewContainerFrame viewContainerFrame = this.getViewManager().getRootView();
	 /* Closing all children views */
		for (ViewContainer view:views){
			if (view != viewContainerFrame)
				try {
					/* This should be view.viewSave(); or persist just the view collection from the
					 * application. Next time application starts the collection will be retrieved
					 * again and the collections could be added to the application. */
					view.viewClose();
				} catch (ViewException e) {
					throw new RuntimeException(e);
				}
		}		
	 /* And finally we close the root view */
		viewContainerFrame.getFrame().dispose();
	}
	
	/* (non-Javadoc)
	 * @see org.viewaframework.core.ApplicationListenerAware#fireinitUI(org.viewaframework.core.ApplicationEvent)
	 */
	public void fireinitUI(ApplicationEvent event) {
		for (ApplicationListener l : this.applicationListeners){
			l.onInitUI(event);
		}
	}

	/* (non-Javadoc)
	 * @see org.viewaframework.core.ApplicationListenerAware#firePrepare(org.viewaframework.core.ApplicationEvent)
	 */
	public void firePrepare(ApplicationEvent event) {
		for (ApplicationListener l : this.applicationListeners){
			l.onPrepare(event);
		}
	}

	/* (non-Javadoc)
	 * @see org.viewaframework.core.ApplicationListenerAware#firePrepareUI(org.viewaframework.core.ApplicationEvent)
	 */
	public void firePrepareUI(ApplicationEvent event) {
		for (ApplicationListener l : this.applicationListeners){
			l.onPrepareUI(event);
		}
	}

	/* (non-Javadoc)
	 * @see org.viewaframework.core.ApplicationContextAware#getApplicationContext()
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/* (non-Javadoc)
	 * @see org.viewaframework.core.ApplicationListenerAware#getApplicationListeners()
	 */
	public List<ApplicationListener> getApplicationListeners() {
		return applicationListeners;
	}

	/* (non-Javadoc)
	 * @see org.viewa.core.ApplicationBase#getControllerDispatcher()
	 */
	public ViewControllerDispatcher getControllerDispatcher() {
		return this.dispatcher;
	}

	public Locale getLocale() {
		if (this.locale == null){
			this.locale = Locale.ROOT;
		}
		return locale;
	}

	/* (non-Javadoc)
	 * @see org.viewa.core.ApplicationBase#getName()
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return
	 */
	public ViewContainerFrame getRootView() {
		return rootView;
	}

	/* (non-Javadoc)
	 * @see org.viewa.core.Application#getViewManager()
	 */
	public ViewManager getViewManager() {
		return viewManager;
	}
	
	/* (non-Javadoc)
	 * @see org.viewaframework.model.ViewModelManagerAware#getViewModelManager()
	 */
	public ViewModelManager getViewModelManager() {
		if (this.viewModelManager == null){
			this.viewModelManager = new AbstractViewModelManager();
		}
		return this.viewModelManager;
	}
	

	/* (non-Javadoc)
	 * @see org.viewaframework.core.Application#hideOrRestore()
	 */
	public boolean hideOrRestore(){
		boolean shouldBeVisible = !this.isVisible();
		this.setVisible(shouldBeVisible);
		return shouldBeVisible;
	}

	/* (non-Javadoc)
	 * @see org.viewa.core.Application#initUI()
	 */
	public void initUI() throws ApplicationException{
		try{
			logger.info("Application UI initializing!");
			ViewManager viewManager = this.getViewManager();
			logger.info("checkPost_1");
			Component 	view 		= viewManager.arrangeViews();
			logger.info("checkPost_2");
			view.setVisible(true);
			logger.info("checkPost_3");
		} catch (ViewManagerException vme){
		  /* since 1.0.2 */
			throw new ApplicationException(vme);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.viewaframework.core.Application#isVisible()
	 */
	public boolean isVisible(){
		JFrame frame = this.getRootView().getFrame();
		boolean result = frame!=null && frame.isVisible();
		return result;
	}

	/* (non-Javadoc)
	 * @see org.viewaframework.core.Application#prepare()
	 */
	@SuppressWarnings("unchecked")
	public void prepare() throws ApplicationException{
		try {
			logger.info("Application preparing!");
			/* If there's no custom applicationContext then a default implementation is provided (v1.0.2) */
			if (this.applicationContext == null){
				this.applicationContext = new DefaultApplicationContext();
			}
		 /* Now any application annotation is going to be processed (v1.0.2) */
			AnnotationProcessor viewsProcessor = new ViewsProcessor(this);
			if(null!=viewsProcessor) {
				logger.info("viewsProcessor_is_not_null");
			}else {
				logger.info("viewsProcessor_is_null");
			}
			AnnotationProcessor viewsPerspectiveProcessor = new ViewsPerspectiveProcessor(this);
			if(null!=viewsPerspectiveProcessor) {
				logger.info("viewsPerspectiveProcessor_is_not_null");
			}else {
				logger.info("viewsPerspectiveProcessor_is_null");
			}
		 /* Executing processors */
			viewsProcessor.process();
			viewsPerspectiveProcessor.process();
		 /* Getting process results */
			Perspective annotationPerspective  = Perspective.class.cast(viewsPerspectiveProcessor.getResult());
		 /* If there's a valid annotation perspective it's set as default */
			if (annotationPerspective != null){
				this.getViewManager().setPerspective(annotationPerspective);
			}
			initViews = List.class.cast(viewsProcessor.getResult());
		} catch (Exception e) {		 
			throw new ApplicationException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.viewaframework.core.Application#prepareUI()
	 */
	public void prepareUI() throws ApplicationException{
		logger.info("Application preparing UI!");
		if (initViews!=null){
			for (ViewsProcessorWrapper w : initViews){
				try {
					if (w.isRootView()){
						logger.info("this_is_root_view");
						this.setRootView(ViewContainerFrame.class.cast(w.getView()));
					} else {
						logger.info("this_is_not_root_view");
						logger.info("view:"+w.getView().getId());
						this.getViewManager().addView(w.getView(),w.getConstraint());
					}
				} catch (ViewException e) {
					throw new ApplicationException(e);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.viewaframework.core.ApplicationListenerAware#removeApplicationListener(org.viewaframework.core.ApplicationListener)
	 */
	public void removeApplicationListener(ApplicationListener listener) {
		this.applicationListeners.remove(listener);
	}
	
	/* (non-Javadoc)
	 * @see org.viewaframework.core.ApplicationContextAware#setApplicationContext(org.viewaframework.core.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws ApplicationContextException {
		if (this.applicationContext != null) {
			throw new ApplicationContextException();
		}
		this.applicationContext = applicationContext;
	}
	
	/* (non-Javadoc)
	 * @see org.viewaframework.core.ApplicationListenerAware#setApplicationListeners(java.util.List)
	 */
	public void setApplicationListeners(List<ApplicationListener> applicationListeners) {
		this.applicationListeners = applicationListeners;
	}

	/* (non-Javadoc)
	 * @see org.viewa.core.ApplicationBase#setControllerDispatcher(org.viewa.controller.ViewControllerDispatcher)
	 */
	public void setControllerDispatcher(ViewControllerDispatcher dispatcher) throws ViewControllerDispatcherException {
		this.dispatcher = dispatcher;
	}


	public void setLocale(Locale locale) {
		this.locale = locale;
		Locale.setDefault(this.locale);
	}

	/* (non-Javadoc)
	 * @see org.viewa.core.ApplicationBase#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @param rootView
	 */
	public void setRootView(ViewContainerFrame rootView) {
		logger.info("Setting_Root_View");
		try {
			if (this.viewManager!=null){
				this.viewManager.setRootView(rootView);
				this.rootView = rootView;
			}
		} catch (ViewException e) {
			logger.info("exception_setting_root_view");
			throw new RuntimeException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.viewa.core.Application#setViewManager(org.viewa.view.ViewManager)
	 */
	public void setViewManager(ViewManager viewManager) throws ViewManagerException {
		this.viewManager = viewManager;
	}

	
	/* (non-Javadoc)
	 * @see org.viewaframework.model.ViewModelManagerAware#setViewModelManager(org.viewaframework.model.ViewModelManager)
	 */
	public void setViewModelManager(ViewModelManager viewModelManager) {
			this.viewModelManager = viewModelManager;
	}
	
	/* (non-Javadoc)
	 * @see org.viewaframework.core.Application#setVisible(boolean)
	 */
	public void setVisible(boolean visible){
		JFrame frame = this.getRootView().getFrame();
		if (frame != null){
			if (visible){
				frame.setVisible(visible);
				frame.repaint();
			} else {
				frame.setVisible(visible);
			}
		}
	}
}