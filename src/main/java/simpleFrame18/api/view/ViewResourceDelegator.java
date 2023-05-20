package simpleFrame18.api.view;

import java.util.Map;

import simpleFrame18.api.view.delegator.Delegator;
import simpleFrame18.api.view.delegator.ViewResourceConverter;

public interface ViewResourceDelegator extends Delegator {

	/**
	 * @param converters
	 */
	public abstract void setViewResourceConverters(Map<Class<? extends Object>,ViewResourceConverter> converters);

}