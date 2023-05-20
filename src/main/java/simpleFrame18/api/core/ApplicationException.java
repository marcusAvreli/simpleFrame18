package simpleFrame18.api.core;

/**
 * This exception means the abnormal termination of the application
 * 
 * @author Mario Garcia
 * @since 1.0.2
 *
 */
//https://github.com/mariogarcia/viewa/blob/c39f7f46dc39908bd23cd4ded0b60c5f555617b8/api/src/main/java/org/viewaframework/core/ApplicationException.java
public class ApplicationException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2374434276758301788L;

	/**
	 * @param throwable
	 */
	public ApplicationException(Throwable throwable){
		super(throwable);
	}
	
	/**
	 * @param message
	 */
	public ApplicationException(String message){
		super(message);
	}
	
}