package simpleFrame18.api.view.event;

import java.util.List;

//https://github.com/mariogarcia/viewa/blob/c39f7f46dc39908bd23cd4ded0b60c5f555617b8/api/src/main/java/org/viewaframework/view/event/ViewContainerEventControllerAware.java
public interface ViewContainerEventControllerAware {

	public abstract void addViewContainerListener(ViewContainerEventController listener);
	public abstract void removeViewContainerListener(ViewContainerEventController listener);
	public abstract void setViewContainerListeners(List<ViewContainerEventController> listeners);
	public abstract List<ViewContainerEventController> getViewContainerListeners();
}