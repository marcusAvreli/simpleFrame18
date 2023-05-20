package simpleFrame18.api.core;

import java.util.List;

/**
 * @author Mario García
 * @since 1.0
 *
 */
public interface ApplicationListenerAware {
	
	/**
	 * @param listener
	 */
	public void addApplicationListener(ApplicationListener listener);
	/**
	 * @param event
	 */
	public void fireClose(ApplicationEvent event);
	
	/**
	 * @param event
	 */
	public void fireinitUI(ApplicationEvent event);
	/**
	 * @param event
	 */
	public void firePrepare(ApplicationEvent event);
	
	/**
	 * @param event
	 */
	public void firePrepareUI(ApplicationEvent event);
	/**
	 * @return
	 */
	public List<ApplicationListener> getApplicationListeners();
	/**
	 * @param listener
	 */
	public void removeApplicationListener(ApplicationListener listener);
	/**
	 * @param applicationListeners
	 */
	public void setApplicationListeners(List<ApplicationListener> applicationListeners);

}