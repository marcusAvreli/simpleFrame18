package simpleFrame18.api.model;

import java.util.Map;

/**
 * Implemented for those classes which holds a ViewModel
 * 
 * @author Mario Garcia
 * @since 1.0
 *
 */
public interface ViewModelAware {
	
	/**
	 * @param alias
	 * @param object
	 */
	public void addModelValue(String alias,Object object);
	
	/**
	 * @param alias
	 * @return
	 */
	public Object getModelValue(String alias);
	
	/**
	 * @return
	 */
	public Map<String,ViewModel> getViewModelMap();
	
	/**
	 * @param viewModelMap
	 */
	public void setViewModelMap(Map<String,ViewModel> viewModelMap);

}