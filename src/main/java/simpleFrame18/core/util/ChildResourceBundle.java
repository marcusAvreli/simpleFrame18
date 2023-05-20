package simpleFrame18.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * This resource bundle can set its parent through its constructor
 * 
 * @author Mario Garcia
 *
 */
public class ChildResourceBundle extends PropertyResourceBundle{

	/**
	 * This constructor allows setting the parent ResourceBundle
	 * 
	 * @param stream
	 * @param parent
	 * @throws IOException
	 */
	public ChildResourceBundle(InputStream stream,ResourceBundle parent) throws IOException {
		super(stream);
		super.setParent(parent);
	}
}