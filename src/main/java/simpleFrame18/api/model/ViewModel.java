package simpleFrame18.api.model;

import java.util.Map;

/**
 * A placeholder for view models.
 * 
 * @author Mario Garcia
 * @since 1.0
 *
 */
public interface ViewModel
{	
	/**
	 * @param property
	 * @param converter
	 */
	public void addModelConverter(String property,ViewModelConverter<?,?> converter);
	
	/**
	 * @param property
	 * @param strategy
	 */
	public void addModelStrategy(String property,ViewModelStrategy strategy);

	/**
	 * @param strategy
	 */
	public void addModelStrategy(ViewModelStrategy strategy);		

	/**
	 * @return
	 */
	public Class<?> getModelClass();

	/**
	 * @return
	 */
	public Map<String,ViewModelConverter<?,?>> getModelConverterMap();

	/**
	 * @return
	 */
	public Map<String,ViewModelStrategy> getModelStrategyMap();


	/**
	 * @return
	 */
	public ViewModelStrategy getModelStrategy();
	

	/**
	 * @param property
	 * @return
	 */
	public ViewModelStrategy getModelStrategy(String property);
	

	/**
	 * @param classType
	 */
	public void setModelClass(Class<?> classType);
}