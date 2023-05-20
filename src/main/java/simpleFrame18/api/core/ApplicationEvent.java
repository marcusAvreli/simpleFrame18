package simpleFrame18.api.core;

/**
 * @author Mario García
 * @since 1.0
 *
 */
public class ApplicationEvent {
	
	private Application source;
	
	/**
	 * 
	 */
	public ApplicationEvent(){
		super();
	}
	
	/**
	 * @param app
	 */
	public ApplicationEvent(Application app){
		this.source = app;
	}
	
	/**
	 * @return
	 */
	public Application getSource() {
		return source;
	}

	/**
	 * @param application
	 */
	public void setSource(Application application) {
		this.source = application;
	}
}