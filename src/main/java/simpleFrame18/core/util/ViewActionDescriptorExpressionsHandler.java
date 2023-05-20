package simpleFrame18.core.util;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


/**
 * This class translates simple xpath expressions into xml.
 * 
 * @author Mario Garcia
 * @since 1.0
 *
 */
public class ViewActionDescriptorExpressionsHandler {

	/**
	 * This method gets the conditions of the xpath expression and puts them within a map
	 * 
	 * @param st The condition expression
	 * @return A map containing all the conditions
	 */
	private Map<String,String> getConditionsAsAttributeMap(String st) {		
		Map<String,String> 	attributeMap 			= new HashMap<String, String>();		
		Pattern 			corcheteConAttributos 	= Pattern.compile("(.*)(\\[.*\\])(.*)");
		Pattern 			claveValor				= Pattern.compile("(.*)='(.*)'");
		Matcher				matcher					= corcheteConAttributos.matcher(st);
		Matcher				bruteAttsMatcher		= null;
		String 				charSequence			= "";
		String 				finalAtts				= "";
		String[]			bruteAtts				= null;
		
		if (matcher.find()){
			charSequence 	= matcher.group(2);
			finalAtts 		= charSequence.replaceAll("\\[","").replaceAll("\\]","").trim();
			bruteAtts 		= finalAtts.split("@");
			for (String sst : bruteAtts){
				bruteAttsMatcher = claveValor.matcher(sst);
				if (bruteAttsMatcher.find()){
					attributeMap.put(bruteAttsMatcher.group(1),bruteAttsMatcher.group(2));
				}
			}
		}
		return attributeMap;
	}
	
	/**
	 * This method returns an xml document built upon the xpath expressions
	 * 
	 * @param actionDescriptors A list of xpath expressions
	 * @return An xml representation of the expressions
	 * @throws Exception If anything goes wrong
	 */
	public Document getXML(List<String> actionDescriptors) throws Exception {
		DocumentBuilderFactory 		factory 	= DocumentBuilderFactory.newInstance();
		DocumentBuilder 			builder 	= factory.newDocumentBuilder();		
		Document 					document 	= builder.parse(new InputSource(new StringReader("<menuBar></menuBar>")));		
		Element						rootElement = document.getDocumentElement();
		
		for (String st : actionDescriptors)
		{						
			String 				path					= st.replaceAll("\\[.*\\]","");
			String				atts					= st.replaceAll(path,"");
			String				transfAtts				= atts != null ? atts.replaceAll("\\/","#0001#") : "";
			String				common					= st.replace(atts,transfAtts);
			String[] 			menuPath 				= common.split("/");			
			String 				incrementalExpression 	= "";
			String 				currentParent 			= "";
			String 				incrementFragment		= "";
			NodeList 			lista 					= null;
			Map<String,String>  attributesMap			= null;
			boolean 			hasConditions			= false;
				for (String inc : menuPath){
					if (inc!=null && !inc.equalsIgnoreCase("")){
						hasConditions			= inc.matches(".*\\[.*\\].*");
						attributesMap			= hasConditions ? getConditionsAsAttributeMap(inc.replace("#0001#", "/")) : null;							
						currentParent 			= incrementalExpression;
						incrementFragment		= inc.replaceAll("\\[.*\\]","");
						incrementalExpression  += "/"+incrementFragment;												
						//lista 					= XPathAPI.selectNodeList(document,"/"+incrementalExpression);						
						lista = (NodeList)XPathFactory.newInstance().newXPath().evaluate("/"+incrementalExpression, document, XPathConstants.NODESET);						
							if (lista.getLength() == 0 || (lista.getLength()!=0 && inc.equalsIgnoreCase("separator"))){
								boolean 	documentRootIsParent 	= currentParent.equalsIgnoreCase("") ? true : false;
								String 		currentParentParsed 	= documentRootIsParent ? "" : currentParent;								
								//Node 		node 					= XPathAPI.selectSingleNode(rootElement,"/"+currentParentParsed);
								Node 		node = (Node)XPathFactory.newInstance().newXPath().evaluate("/"+currentParentParsed, rootElement, XPathConstants.NODE);								
								Element 	element 				= document.createElement(incrementFragment);
								if (hasConditions){
									for (String key : attributesMap.keySet()){
										element.setAttribute(key,attributesMap.get(key));
									}
								}
								
								if (documentRootIsParent){
									node.getFirstChild().appendChild(element);								
								} else {
									node.appendChild(element);
								}
							}
					}
				}
		}

		return document;
	}
	
	/**
	 * This method returns an xml document built upon the xpath expressions
	 * 
	 * @param actionDescriptors A list of xpath expressions
	 * @return An xml representation of the expressions
	 * @throws Exception If anything goes wrong
	 */
	public Document getXML(Reader reader) throws Exception {
		DocumentBuilderFactory 		factory 	= DocumentBuilderFactory.newInstance();
		factory.setIgnoringComments(true);
		DocumentBuilder 			builder 	= factory.newDocumentBuilder();	
		Document 					document 	= builder.parse(new InputSource(reader));		
		return document;
	}

	/**
	 * Method for retrieving the menu contruction as a String object. Mostly
	 * used for logging purposes.
	 * 
	 * @param document xml document
	 * @return A String representation of the ActionDescriptor list
	 * @throws Exception If anything goes wrong
	 */
	public String getXMLAsString(Document document) throws Exception {
		TransformerFactory 	factory 	= TransformerFactory.newInstance();
		Transformer 		transformer = factory.newTransformer();
		StringWriter 		writer 		= new StringWriter();
		
		transformer.transform(new DOMSource(document),new StreamResult(writer));
		
		return writer.toString();
	}
}
