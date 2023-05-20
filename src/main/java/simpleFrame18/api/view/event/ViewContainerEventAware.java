package simpleFrame18.api.view.event;

public interface ViewContainerEventAware {
	
	public void fireViewClose(ViewContainerEvent event);
	
	public void fireViewInit(ViewContainerEvent event);
	
	public void fireViewInitUIState(ViewContainerEvent event);
	
	public void fireViewInitBackActions(ViewContainerEvent event);
	
	public void fireViewFinalUIState(ViewContainerEvent event);

}