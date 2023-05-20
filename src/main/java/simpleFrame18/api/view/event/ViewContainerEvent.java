package simpleFrame18.api.view.event;

import simpleFrame18.api.view.ViewContainer;

/**
 * @author Mario García
 * @since 1.0
 *
 */

//https://github.com/mariogarcia/viewa/blob/c39f7f46dc39908bd23cd4ded0b60c5f555617b8/api/src/main/java/org/viewaframework/view/event/ViewContainerEvent.java
public class ViewContainerEvent {
	
	private ViewContainer source;

	/**
	 * @param view
	 */
	public ViewContainerEvent(ViewContainer view){
		this.source = view;;
	}
	
	/**
	 * @return
	 */
	public ViewContainer getSource() {
		return source;
	}

	/**
	 * @param source
	 */
	public void setSource(ViewContainer source) {
		this.source = source;
	}

}