package simpleFrame18.core.view.delegator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import simpleFrame18.api.view.ViewContainer;
import simpleFrame18.api.view.ViewManager;
import simpleFrame18.api.view.ViewResourceDelegator;
import simpleFrame18.api.view.delegator.ViewResourceConverter;
import simpleFrame18.core.util.ChildResourceBundleAware;
import simpleFrame18.core.util.LastModifiedControl;
import simpleFrame18.core.view.AbstractViewContainer;
import simpleFrame18.core.view.delegator.converter.ViewResourceBooleanConverter;
import simpleFrame18.core.view.delegator.converter.ViewResourceColorConverter;
import simpleFrame18.core.view.delegator.converter.ViewResourceDimensionConverter;
import simpleFrame18.core.view.delegator.converter.ViewResourceIconConverter;
import simpleFrame18.core.view.delegator.converter.ViewResourceImageConverter;
import simpleFrame18.core.view.delegator.converter.ViewResourcePrimitiveBooleanConverter;
import simpleFrame18.core.view.delegator.converter.ViewResourceStringConverter;


/**
 * This class gets the Resources Available for a view
 * 
 * @author Mario García
 *
 */
public class DefaultViewResourceDelegator implements ViewResourceDelegator {

	private static final String SETTER_PREFIX ="set";
	private static final String APPLICATION_RESOURCE_BUNDLE_BASE_NAME="Application";
	private static final String VIEWA_FILE_BASE_NAME = "viewa";
	private static final Logger logger = LoggerFactory.getLogger(DefaultViewResourceDelegator.class);
	//private static final Log logger = LogFactory.getLog(DefaultViewResourceDelegator.class);
	private LastModifiedControl control;
	private Map<Class<? extends Object>,ViewResourceConverter> converters;
	private ViewContainer view;
	private ResourceBundle viewBundle;
	
	/**
	 *  Default constructor.
	 */
	public DefaultViewResourceDelegator(){
		this.converters = new HashMap<Class<? extends Object>, ViewResourceConverter>();
		this.loadBasicConverterTypes();
	}

	/* (non-Javadoc)
	 * @see org.viewaframework.view.delegator.ViewResourceDelegator#clearResources(java.lang.Object, java.lang.Class)
	 */
	public void clean(ViewContainer viewContainer) {
		this.getControl().setTimeToLive(ResourceBundle.Control.TTL_DONT_CACHE);
	}

	/**
	 * If no control is defined it returns a org.viewaframework.util.LastModifiedControl class.
	 * 
	 * @return The control that defines the bundle's cache policy
	 */
	public LastModifiedControl getControl() {
		if (this.control == null){
			this.control = new LastModifiedControl();
		}
		return control;
	}

	/**
	 * @return
	 */
	public ViewContainer getView() {
		return view;
	}

	/**
	 * @return
	 */
	public ResourceBundle getViewBundle() {
		return viewBundle;
	}

	/**
	 * This method looks for a converter suitable for converting a given property from the resource bundle
	 * to the destination class type.
	 * 
	 * @param stringObject The object we want to convert
	 * @param destinationClass The class the stringObject has to be
	 * @return The converted object
	 */
	private Object handleObjectForClass(String stringObject, Class<?> destinationClass) {
		ViewResourceConverter 	converter 				= null;		
		String 					destinationClassName 	= destinationClass.getName();
		String 					userConverter 			= destinationClassName;
		try{
			userConverter = ResourceBundle.
				getBundle(VIEWA_FILE_BASE_NAME).
				getString(destinationClassName);
		} catch (MissingResourceException ex){
			logger.warn("There's no custom user converter for the key: "+destinationClassName);
		}
	 /* If there's a specific converter implemented by the user then it will be loaded to be used */
		if (!userConverter.equals(destinationClassName)){
			try{
				converter = ViewResourceConverter.class.cast(
						this.getClass().getClassLoader().loadClass(userConverter).newInstance());
			} catch (Exception ex){
			 /* The error is posted and then the process continue looking for a default converter */
				logger.error("Converter can't be instantiated: "+ex.getMessage());
			}
		}
	 /* If the user converter is ok , then it will be used otherwise the default converters will be used instead */
		converter =  converter != null ? 
				converter : converters.get(destinationClass);
	 /* If the type we want to handle doesn't have a specific converter then String converter will handle it */
		if (converter == null){
			converter = converters.get(String.class);
		}		
		return converter.convert(stringObject, destinationClass);
	}

	/* (non-Javadoc)
	 * @see org.viewaframework.example.person.view.util.ViewResourceDelegator#injectResources(java.lang.Object, java.lang.Class)
	 */
	public void inject(ViewContainer ob){
		Class<? extends Object> clazz = ob.getClass();
		logger.debug("Injecting Resources to "+clazz.getName());
		view 						= ob;
		boolean isRootView = view.getId().equalsIgnoreCase(ViewManager.ROOT_VIEW_ID);		
		viewBundle 					= getCurrentViewBundle(clazz, isRootView,getControl());
	 /* If any resource bundle has been specified */
		if (viewBundle != null){
			Enumeration<String> keys 	= viewBundle.getKeys();
			/* For every key in the resource bundle, the loop tries to find every component that could match the entry */
			while (keys.hasMoreElements()){
				String 	key 					= keys.nextElement();
				if (key!=null && !key.isEmpty()){
					int 					propertySeparatorIndex 	= key.indexOf('.');
					boolean					isViewProperty 			= propertySeparatorIndex == -1;
					String					propertyName			= isViewProperty ? key : key.substring(propertySeparatorIndex + 1);
					String					possibleMethodName		= SETTER_PREFIX + propertyName.substring(0,1).toUpperCase()+propertyName.substring(1);
					String 					componentName 			= isViewProperty ? null : key.substring(0,propertySeparatorIndex);					
					List<?> 				objects2Set 			= componentName !=null 	? view.getComponentsByName(componentName) : Arrays.asList(view);
					if (objects2Set != null && objects2Set.size() > 0){
						for (Object component2Set : objects2Set){
							Class<? extends Object> klazz 					= component2Set.getClass();
							Method[]				methods					= klazz.getMethods();
							Object					paramObject				= null;
							boolean					processed				= false;
							processed = false;
							/* The following loop could be optimized */
							for (int i=0 ; i < methods.length && !processed ; i++){							
								Method currentMethod = methods[i];
								String currentMethodName = currentMethod.getName();
								/* If the method name match the property then the process tries to set the value */	
								if (currentMethodName.equalsIgnoreCase(possibleMethodName)){
									paramObject = handleObjectForClass(viewBundle.getString(key),currentMethod.getParameterTypes()[0]);
									/* If something fails the rest of the resources could be processed */
									try {
										currentMethod.invoke(component2Set,new Object[]{paramObject});
										processed = true;
									} catch (InvocationTargetException e){
										logger.warn("Cannot process the resource value: "+e.getMessage());
									} catch (IllegalArgumentException e) {
										logger.warn("Cannot process the resource value: "+e.getMessage());
									} catch (IllegalAccessException e) {
										logger.warn("Cannot process the resource value: "+e.getMessage());
									} catch (Exception ex){
										logger.warn("The view resource file doesnt exist: "+ex.getMessage());
									}
								}
							}
						}
					} else {
						logger.debug("There is no component named: "+componentName+" for view "+view.getId());
					}
				}
			}
			view.setMessageBundle(viewBundle);
		}
	}

	/**
	 * This method gets the right ResourceBundle for this view. If this view is other than the ROOT view
	 * then Application.properties is used as parent for the current view ResourceBundle.
	 * 
	 * If the current view ResourceBundle does not exists then Application.properties is taken as the default.
	 * Finally if also Application.properties does not exists then the method returns null.
	 * 
	 * @param clazz
	 * @param isRootView
	 * @return
	 */
	private ResourceBundle getCurrentViewBundle(Class<? extends Object> clazz,boolean isRootView,ChildResourceBundleAware control) {
		ResourceBundle bundle = null;
	 /* Getting the specific locale for all bundles */
		Locale locale = this.view.getApplication().getLocale();
		try{
			control.setParent(ResourceBundle.getBundle(APPLICATION_RESOURCE_BUNDLE_BASE_NAME,locale));						
			bundle = ResourceBundle.getBundle(clazz.getName(),locale,this.getControl());			
		} catch (MissingResourceException m){
			logger.warn("There's no specific properties file for view '"+view.getId()+"'");
			bundle = ResourceBundle.getBundle(APPLICATION_RESOURCE_BUNDLE_BASE_NAME,locale);
		}
		return bundle;
	}
	
	/**
	 *  This method loads the default converters located in the following package "org.viewaframework.view.delegator.converter"
	 */
	@SuppressWarnings("all")
	private void loadBasicConverterTypes(){	
		try {
			List<Class<? extends ViewResourceConverter>> loadedClasses = Arrays.asList(
				ViewResourceBooleanConverter.class,
				ViewResourceColorConverter.class,
				ViewResourceDimensionConverter.class,
				ViewResourceIconConverter.class,
				ViewResourceImageConverter.class,
				ViewResourcePrimitiveBooleanConverter.class,
				ViewResourceStringConverter.class			
			);
			for (Class<? extends ViewResourceConverter> clazz: loadedClasses){
				ViewResourceConverter converter = ViewResourceConverter.class.cast(clazz.newInstance());
				this.converters.put(converter.getDestinationClass(),converter);
			}
		} catch (InstantiationException e) {
			logger.error(e.getMessage());
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		}
	}


	/**
	 * @param control
	 */
	public void setControl(LastModifiedControl control) {
		this.control = control;
	}
	
	/**
	 * @param view
	 */
	public void setView(ViewContainer view) {
		this.view = view;
	}
	
	/**
	 * @param viewBundle
	 */
	public void setViewBundle(ResourceBundle viewBundle) {
		this.viewBundle = viewBundle;
	}

	/* (non-Javadoc)
	 * @see org.viewaframework.view.delegator.ViewResourceDelegator#setViewResourceConverters(java.util.Map)
	 */
	public void setViewResourceConverters(Map<Class<? extends Object>, ViewResourceConverter> converters) {
		this.converters = converters;
	}

}