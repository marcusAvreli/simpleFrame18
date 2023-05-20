package simpleFrame18.core.util;

/**
 * This class is a holder for the splash screen current status
 * 
 * @author Mario Garcia
 * @since 1.0
 *
 */
public class SplashStatus {
	
	private String message;
	private Integer progress;
	
	
	/**
	 * 
	 */
	public SplashStatus(){
		super();
	}
	
	/**
	 * @param message
	 * @param progress
	 */
	public SplashStatus(String message, Integer progress) {
		super();
		this.message = message;
		this.progress = progress;
	}
	/**
	 * @return
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @return
	 */
	public Integer getProgress() {
		return progress;
	}
	/**
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @param progress
	 */
	public void setProgress(Integer progress) {
		this.progress = progress;
	}

}
