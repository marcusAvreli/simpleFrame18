package simpleFrame18.core.view.delegator.converter;

import simpleFrame18.api.view.delegator.ViewResourceConverter;

public class ViewResourceStringConverter implements ViewResourceConverter {

	public Object convert(Object arg0, Class<? extends Object> arg1) {
		return arg0.toString();
	}

	public Class<? extends Object> getDestinationClass() {
		return String.class;
	}

}