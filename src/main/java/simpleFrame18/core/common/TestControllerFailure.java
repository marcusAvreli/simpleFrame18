package simpleFrame18.core.common;

import static simpleFrame18.core.util.ComponentFinder.find;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import simpleFrame18.api.controller.BackgroundException;
import simpleFrame18.api.view.ViewContainer;
import simpleFrame18.api.view.ViewException;
import simpleFrame18.core.controller.AbstractViewController;


/**
 * This controller fails in the handleView method and should pass control to the
 * {@link ViewController#postHandlingViewOnError(ViewContainer, java.util.EventObject, Throwable)} 
 * postHandlingViewOnError method. This way you can use the UI to inform user 
 * why the background method failed.
 * 
 * @author mgg
 *
 */
public class TestControllerFailure extends AbstractViewController<ActionListener, ActionEvent> {

	/* (non-Javadoc)
	 * @see org.viewaframework.controller.ViewController#getSupportedClass()
	 */
	public Class<ActionListener> getSupportedClass() {
		return ActionListener.class;
	}

	/* (non-Javadoc)
	 * @see org.viewaframework.controller.AbstractViewController#handleView(org.viewaframework.view.ViewContainer, java.util.EventObject)
	 */
	@Override
	public void handleView(ViewContainer view, ActionEvent eventObject) throws BackgroundException{
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// No matter what happens here the method will throw an exception
			}
			throw new BackgroundException("Bad situation!");
	}

	/* (non-Javadoc)
	 * @see org.viewaframework.controller.AbstractViewController#postHandlingViewOnError(org.viewaframework.view.ViewContainer, java.util.EventObject, java.lang.Throwable)
	 */
	@Override
	public void postHandlingViewOnError(ViewContainer view,ActionEvent eventObject, BackgroundException th) throws ViewException {
		JTextField field = find(JTextField.class).in(view).named("text");
		field.setText(th.getMessage());
	}
}