package simpleFrame18.core.view;

import java.awt.Container;
import java.util.Collection;
import java.util.EventListener;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import simpleFrame18.App;
import simpleFrame18.api.controller.ViewController;
import simpleFrame18.api.controller.ViewControllerDispatcher;
import simpleFrame18.api.core.Application;
import simpleFrame18.api.model.ViewModel;
import simpleFrame18.api.model.ViewModelManager;
import simpleFrame18.api.view.ViewContainer;
import simpleFrame18.api.view.ViewContainerDialog;
import simpleFrame18.api.view.ViewContainerFrame;
import simpleFrame18.api.view.ViewException;
import simpleFrame18.api.view.ViewManager;
import simpleFrame18.api.view.ViewManagerException;
import simpleFrame18.api.view.perspective.PerspectiveConstraint;
import simpleFrame18.core.annotation.processor.ControllersProcessor;
import simpleFrame18.core.annotation.processor.ListenersProcessor;


/**
 * A default implementation of View Manager. It manages the views added to the application
 * and those which are removed from the application. It also is resposible of launching
 * the views lifecycle and add them to the current perspective. 
 * 
 * It's also responsible for keeping the visual part of the application stable.
 * 
 * @author Mario Garcia
 * @since 1.0
 *
 */
//https://github.com/mariogarcia/viewa/blob/c39f7f46dc39908bd23cd4ded0b60c5f555617b8/core/src/main/java/org/viewaframework/view/AbstractViewManager.java
public abstract class AbstractViewManager implements ViewManager
{
	private Application 				application;
	private Map<Object,ViewContainer> 	views;
	private static final Logger logger = LoggerFactory.getLogger(AbstractViewManager.class);
	/**
	 * Default constructor. It creates a new List where the views are
	 * added.
	 */
	public AbstractViewManager(){
		this.views = new HashMap<Object,ViewContainer>();		
	}


	
	/* (non-Javadoc)
	 * @see org.viewaframework.view.ViewManager#addView(org.viewaframework.view.ViewContainer, org.viewaframework.view.perspective.PerspectiveConstraint)
	 */
	public void addView(ViewContainer view,PerspectiveConstraint constraint) throws ViewException 
	{	 
		Map<Object,ViewContainer> 				viewContainerCollection = this.getViews();
		ViewContainer 							viewContainer 			= viewContainerCollection.get(view.getId());		
		Application								app						= this.getApplication();
		ViewModelManager						modelManager			= app.getViewModelManager();
		ViewControllerDispatcher				controllerDispatcher	= app.getControllerDispatcher();
		Map<String,ViewModel>					model					= null;
		Map<String,ViewModel>					viewModel				= null;
		Map<String,List<ViewController<?,?>>>	controllers 			= null;
		String									viewId					= view.getId();
	 /* Views must have their ids and these ids must not be repeated */		
		if (viewId!=null && viewContainer == null)
		{
			model 		= modelManager.getViewModelMap(viewId);
			viewModel	= view.getViewModelMap();
			controllers = controllerDispatcher.getViewControllers(view);
			logger.info("view_controllers_map_0:"+controllers);
		 /* Then application instance is injected in the view */
			view.setApplication(app);
		 /* The view is added to the application holder */
			this.getViews().put(viewId,view);
		 /* The view lifecycle begins */
			if (viewModel!=null){
				model.putAll(viewModel);
			}
			view.setViewModelMap(model);
		 /* This view can already have some controllers, if so the manager adds the dispatcher controllers*/
			logger.info("view_controllers_map_1:"+controllers);
			if (view.getViewControllerMap()!=null){
				view.getViewControllerMap().putAll(controllers);
		 /* Otherwise the dispatcher sets the controllers */
			} else {
				view.setViewControllerMap(controllers);
			}
			// TODO refactoring
			try{
				logger.info("view:"+view);
				logger.info("proc:"+view.getApplication().getApplicationContext());
				Map<String,List<ViewController<? extends EventListener,? extends EventObject>>> controllerMap = view.getViewControllerMap();
				if(null !=controllerMap && !controllerMap.isEmpty()) {
				logger.info("controllers_map:"+controllerMap.size());
				controllerMap.putAll(
						new ControllersProcessor(
								view,
								view.getApplication().getApplicationContext()).process());
				}else {
					logger.info("controller_map_is_empty");
				}
				view.setViewContainerListeners(
					new ListenersProcessor(view).getResult()
				);
			} catch(Exception ex){
				logger.info("exception_here");
				ex.printStackTrace();
				throw new ViewException(ex.getMessage());
			}
			
			view.viewInit();
		 /* And finally the view is added to the current perspective */
			if (!view.getId().equals(ViewManager.ROOT_VIEW_ID)){
				if (view instanceof ViewContainerDialog){
					JDialog dialog 		= ((ViewContainerDialog)view).getDialog();
					JFrame 	rootFrame 	= this.getRootView().getFrame();
					dialog.setLocationRelativeTo(rootFrame);
					dialog.setTitle(view.getTitle());
					dialog.setIconImage(view.getIconImage());
					dialog.setVisible(true);			
			 /* Otherwise if it is not an AbstractViewContainerTray is added to the current perspective */
				} else if (!(view instanceof AbstractViewContainerTray)){
					this.getPerspective().addView(view,constraint);
				 /* Now the new view has the focus. Not needed in the other cases
				  * because in one hand dialogs catch the focus and on the other
				  * the frame has the focus when is made visible */
					view.getRootPane().requestFocusInWindow();
				}
			} else {
				ViewContainerFrame f = (ViewContainerFrame) view;
				f.getFrame().setTitle(f.getTitle());
			}
		} else {
			throw new ViewException(viewId == null ? "view must have an id" : "View is already added");
		}
	}
	
	public void addView(ViewContainer view) throws ViewException {
		this.addView(view,null);
	}
	
	/* (non-Javadoc)
	 * @see org.viewa.view.ViewManager#arrangeView()
	 */
	
	public Container arrangeViews() throws ViewManagerException
	{
		logger.info("arrange_views_start");
		Map<Object,ViewContainer> cviews 					= new HashMap<Object, ViewContainer>();
		Collection<ViewContainer> viewContainerCollection 	= this.getViews().values();
	
		for (ViewContainer view : viewContainerCollection){
			cviews.put(view.getId(), view);
		}
		logger.info("perspective: " +this.getPerspective());
		this.getPerspective().setViews(cviews);
		
		Container container = this.getPerspective().arrange();
		logger.info("arrange_views_finish");
		return container;
	}
	
	/* (non-Javadoc)
	 * @see org.viewa.core.ApplicationAware#getApplication()
	 */
	public Application getApplication() {
		return this.application;
	}
	
	/* (non-Javadoc)
	 * @see org.viewa.view.ViewManager#getViews()
	 */
	public Map<Object, ViewContainer> getViews() {
		return this.views;
	}

	/* (non-Javadoc)
	 * @see org.viewa.view.ViewManager#removeView(org.viewa.view.View)
	 */
	public void removeView(ViewContainer view) throws ViewException{
		if (view.getId()!=null){
		/* First the view is removed from the application holder */
			ViewContainer view2Close = this.getViews().remove(view.getId());
		/* If the view still exists is removed from the current perspective */
			if (view2Close!=null){
				view2Close.viewClose();			
			 /* Floatable views like dialogs and frames are not added to any perspective so theys couldnt be removed from any perspective */
				if (!(view2Close instanceof ViewContainerDialog) && !(view2Close instanceof ViewContainerFrame)){
					this.getPerspective().removeView(view2Close);
				} else if ((view2Close instanceof ViewContainerDialog) && !(view2Close instanceof ViewContainerFrame)){
					this.getRootView().getFrame().toFront();
				}
				
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.viewa.core.ApplicationAware#setApplication(org.viewa.core.Application)
	 */
	public void setApplication(Application application) {
		this.application = application;
	}
		
}