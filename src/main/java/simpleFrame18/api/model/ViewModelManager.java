package simpleFrame18.api.model;

import java.util.Map;

/**
 * It is intended to be the application placeholder for all views
 * models.
 * 
 * @author Mario Garcia
 * @since 1.0
 *
 */
public interface ViewModelManager {

	/**
	 * @param viewId
	 * @param viewModel
	 */
	public void addViewModel(String viewId,ViewModel viewModel);

	/**
	 * @param viewId
	 * @return
	 */
	public Map<String,ViewModel> getViewModelMap(String viewId);
	
	/**
	 * @param viewId
	 */
	public void removeViewModel(String viewId);
	
}