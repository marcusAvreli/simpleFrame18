package simpleFrame18.core.view.delegator.converter;

import java.awt.Color;

import simpleFrame18.api.view.delegator.ViewResourceConverter;



public class ViewResourceColorConverter implements ViewResourceConverter {

	public Class<? extends Object> DESTINATION_CLASS = Color.class;
	private static final int BASE_HEX = 16;
	

	/* (non-Javadoc)
	 * @see org.viewaframework.view.delegator.ViewResourceConverter#getDestinationClass()
	 */
	public Class<? extends Object> getDestinationClass(){
		return DESTINATION_CLASS;
	}

	/* (non-Javadoc)
	 * @see org.viewaframework.view.delegator.ViewResourceConverter#convert(java.lang.Object, java.lang.Class)
	 */
	@SuppressWarnings("all")
	public Object convert(Object arg0, Class arg1) {
		Object object 	= null;
		String color,first,second,third = null;
		if (arg1 == DESTINATION_CLASS){
			color 	= arg0.toString().substring(1);
			first 	= color.subSequence(0,2).toString();
			second 	= color.subSequence(2,4).toString();
			third 	= color.subSequence(4,6).toString();
			object 	= new Color(Long.valueOf(Long.parseLong(first,BASE_HEX)).intValue(),Long.valueOf(Long.parseLong(second,BASE_HEX)).intValue(),Long.valueOf(Long.parseLong(third,BASE_HEX)).intValue());
		}
		return object;
	}
}