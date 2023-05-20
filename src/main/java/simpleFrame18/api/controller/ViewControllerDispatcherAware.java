package simpleFrame18.api.controller;

public interface ViewControllerDispatcherAware {
	
	/**
	 * This method sets the application controller dispatcher. There is only one
	 * ViewControllerDispatcher by application. 
	 * 
	 * @param dispatcher Sets the ViewControllerDispatcher. If a controller dispatcher
	 * has been already set then an exception should be thrown.
	 * @throws
	 */
	public abstract void setControllerDispatcher(ViewControllerDispatcher dispatcher) throws ViewControllerDispatcherException;

	/**
	 * This method returns the controller dispatcher. The controller
	 * dispatcher knows about what action should be called depending
	 * on what component has launched the event.
	 * 
	 * @return The ViewControllerDispatcher
	 */
	public abstract ViewControllerDispatcher getControllerDispatcher();
	
}

