package simpleFrame18.api.view.delegator;

public interface ViewResourceConverter {

	public abstract Class<? extends Object> getDestinationClass();
	public abstract Object convert(Object arg0, Class<? extends Object> arg1);

}