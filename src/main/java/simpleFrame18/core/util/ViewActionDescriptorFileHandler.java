package simpleFrame18.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import simpleFrame18.App;
import simpleFrame18.api.view.ViewActionDescriptor;
import simpleFrame18.api.view.ViewContainer;
import simpleFrame18.api.view.ViewException;

/**
 * This class parses the menu.xml files to a Document object. It also converts the xpath
 * expressions from the action descriptors to a Document object.
 * 
 * @author Mario Garcia
 * @since 1.0.4
 *
 */
public class ViewActionDescriptorFileHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(ViewActionDescriptorFileHandler.class);
	private static final String MENU_XML_FILE_SUFFIX = ".menu.xml";
	private static final String NO_WHITESPACE_BETWEEN_NODES = "><";
	private static final String WHITESPACE_BETWEEN_NODES = ">\\s*<";
	
	/**
	 * Parses the menu.xml file from the view and returns a readable Document object
	 * 
	 * @param view The view
	 * @return An xml Document object
	 * @throws ViewException
	 */
	public Document getDocument(ViewContainer view)throws ViewException {
		Document document = null;
	 /* Always the menu.xml file is processed first */
		document = getDocumentFromFile(view);	
	 /* if there's any action descriptor the processed document is returned */
		return document;
	}
	
	/**
	 * Parses the xpath expressions of the action descriptors from the view and returns a
	 * readable xml Document object
	 * 
	 * @param view The view
	 * @return An xml readable object
	 */
	public Document getDocumentFromActionDescriptors(ViewContainer view) {
		Document document = null;
		List<ViewActionDescriptor> descriptors = view.getActionDescriptors();//List.class.cast(Map.class.cast(map).get("descriptors"));
		/* If there is any descriptor */
		if (descriptors != null && descriptors.size() > 0){
			List<String> expressionDescriptors 	= new ArrayList<String>();
			/* Looping through descriptors */
			for (ViewActionDescriptor des : descriptors){
				expressionDescriptors.add(des.getExpression());
			}
			try {
				ViewActionDescriptorExpressionsHandler 	handler = new ViewActionDescriptorExpressionsHandler();				
				document = handler.getXML(expressionDescriptors);
				if (logger.isDebugEnabled()){
					logger.debug(handler.getXMLAsString(document));
				}
			} catch (Exception e) {
				logger.warn("Error while processing ActionDescriptor list from view.");
			}	
		}
		return document;
	}
	
	/**
	 * Locates and parses the menu.xml file from the view
	 * 
	 * @param view
	 * @return
	 */
	private Document getDocumentFromFile(ViewContainer view) {	
		Class<? extends ViewContainer> viewClazz = view.getClass();
		Document document = null;
		BufferedReader fileReader = null;
		ViewActionDescriptorExpressionsHandler 	handler = null;
		URL menuFileURL = null;
		String cleanXml = null;
		StringBuilder sb = null;
		try{
			// TODO a better way of removing whitespace from parsed xml
			menuFileURL = viewClazz.getResource(viewClazz.getSimpleName() + MENU_XML_FILE_SUFFIX);
			fileReader = new BufferedReader(new InputStreamReader(menuFileURL.openStream()));
			sb = new StringBuilder();
		 /* We need to read the file in order to remove whitespaces */
			while (fileReader.ready()){
				sb.append(fileReader.readLine());
			}
		 /* Now is time to clean up the xml */
			cleanXml = sb.toString().replaceAll(WHITESPACE_BETWEEN_NODES,NO_WHITESPACE_BETWEEN_NODES).trim();
			handler = new ViewActionDescriptorExpressionsHandler();
			document = handler.getXML(new StringReader(cleanXml));			
			if (logger.isDebugEnabled()){
				logger.debug(handler.getXMLAsString(document));
			}
		} catch (Exception e) {
			logger.warn("Errors processing action descriptors from menu file: "+e.getMessage());
		} finally {
			try {
				if (fileReader != null){
					fileReader.close();
				}
			} catch (IOException e) {
				logger.warn("Errors processing action descriptors from menu file: "+e.getMessage());
			}
		}
		return document;
	}
}