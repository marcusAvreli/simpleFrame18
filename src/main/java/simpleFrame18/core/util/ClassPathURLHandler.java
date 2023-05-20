package simpleFrame18.core.util;

import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * This class handles url's having "classpath" as possible protocol.
 * 
 * @author Mario Garcia
 *
 */
public class ClassPathURLHandler extends URLStreamHandler {

	/* (non-Javadoc)
	 * @see java.net.URLStreamHandler#openConnection(java.net.URL)
	 */
	protected URLConnection openConnection(URL u) throws java.io.IOException {
		URL actualURL = ClassPathURLHandler.class.getClassLoader().getResource(u.getPath());
		if (actualURL == null){
			try{
				actualURL = new URL(u.toExternalForm());
			} catch (Exception ex){
				throw new FileNotFoundException(u.toExternalForm());
			}
		}
		return actualURL.openConnection();
	}
}