package simpleFrame18.core.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import simpleFrame18.api.view.ViewContainer;
import simpleFrame18.api.view.ViewException;
import simpleFrame18.core.controller.AbstractViewController;


public class MyTrayViewController extends AbstractViewController<ActionListener, ActionEvent> {

	public Class<ActionListener> getSupportedClass() {
		return ActionListener.class;
	}

	@Override
	public void postHandlingView(ViewContainer view, ActionEvent eventObject)throws ViewException {
		view.getApplication().hideOrRestore();
	}
}