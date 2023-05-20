package simpleFrame18.api.view.delegator;

import simpleFrame18.api.view.ViewContainer;
import simpleFrame18.api.view.ViewException;

public interface Delegator {
	
	public void inject(ViewContainer viewContainer) throws ViewException;
	public void clean(ViewContainer viewContainer)throws ViewException;

}