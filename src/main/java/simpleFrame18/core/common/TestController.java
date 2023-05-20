package simpleFrame18.core.common;

import static simpleFrame18.core.util.ComponentFinder.find;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import simpleFrame18.api.view.ViewContainer;
import simpleFrame18.api.view.ViewException;
import simpleFrame18.core.controller.AbstractViewController;


/**
 * @author Mario Garcia
 * @since 1.0.2
 * 
 */
public class TestController extends
		AbstractViewController<ActionListener, ActionEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.viewaframework.controller.ViewController#getSupportedClass()
	 */
	public Class<ActionListener> getSupportedClass() {
		return ActionListener.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.viewaframework.controller.AbstractViewController#postHandlingView
	 * (org.viewaframework.view.ViewContainer, java.util.EventObject)
	 */
	@Override
	public void postHandlingView(ViewContainer view, ActionEvent eventObject)
			throws ViewException {
		JTextField field = find(JTextField.class).in(view).named("text");
		field.setText("Hey it worked");
	}
}
