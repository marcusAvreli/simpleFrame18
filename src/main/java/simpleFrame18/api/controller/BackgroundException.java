package simpleFrame18.api.controller;

public class BackgroundException extends Exception{

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public BackgroundException() {
		super();
	}

	/**
	 * @param message
	 */
	public BackgroundException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public BackgroundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public BackgroundException(Throwable cause) {
		super(cause);
	}
}