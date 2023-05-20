package simpleFrame18.core.view.delegator.converter;

import java.awt.Dimension;

import simpleFrame18.api.view.delegator.ViewResourceConverter;

/**
 * This class converts an input string of type<br/><br/>
 * 200,150<br/><br/>
 * representing width and height into a java.awt.Dimension instance. For example you have a given
 * property like<br/><br/>
 * viewDialog.size= 300,250
 * <br/><br/>
 * Then the expression will become something like<br/><br/>
 * viewDialog.setSize(new java.awt.Dimension(300,250));
 * <br/>
 * 
 * @author Mario Garcia
 *
 */
public class ViewResourceDimensionConverter implements ViewResourceConverter {

	private static final String SPLIT_SIGN = ",";

	/* (non-Javadoc)
	 * @see org.viewaframework.view.delegator.ViewResourceConverter#convert(java.lang.Object, java.lang.Class)
	 */
	public Object convert(Object arg0, Class<? extends Object> arg1) {
		String st = String.valueOf(arg0);
		String[] widthAndHeight = st.split(SPLIT_SIGN);
		Integer width = Integer.valueOf(widthAndHeight[0]);
		Integer height = Integer.valueOf(widthAndHeight[1]);
	 /* Finally returns a java.awt.Dimension instance */
		return new Dimension(width,height);
	}

	/* (non-Javadoc)
	 * @see org.viewaframework.view.delegator.ViewResourceConverter#getDestinationClass()
	 */
	public Class<? extends Object> getDestinationClass() {
		return Dimension.class;
	}

}