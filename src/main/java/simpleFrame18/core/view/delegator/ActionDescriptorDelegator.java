package simpleFrame18.core.view.delegator;

import java.awt.BorderLayout;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRootPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;


import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import simpleFrame18.api.view.ViewContainer;
import simpleFrame18.api.view.ViewException;
import simpleFrame18.api.view.delegator.Delegator;
import simpleFrame18.core.util.ViewActionDescriptorFileHandler;

/**
 * This class extracts actions from the action's xml descriptor and populates the JMenuBar and JToolBar
 * from the current ViewContainer.
 * 
 * @author Mario Garcia
 * @since 1.0
 *
 */
public class ActionDescriptorDelegator implements Delegator {
	
	/* (non-Javadoc)
	 * @see org.viewaframework.view.ActionDescriptorDelegator#injectActionDescriptors(org.viewaframework.view.ViewContainer, java.util.List, java.util.Map)
	 */
	public void inject(ViewContainer view)throws ViewException {
		ViewActionDescriptorFileHandler documentHandler = new ViewActionDescriptorFileHandler();
		Document document = documentHandler.getDocument(view);
	 /* If there's no menu.xml file then the action descriptor list from view is processed */
		document = document != null ? document : documentHandler.getDocumentFromActionDescriptors(view);
		if (document != null){
			Map<String,List<JButton>>				buttonMap				= new HashMap<String, List<JButton>>(); 
			JToolBar								toolBar					= view.getJToolBar();
			JRootPane								rootPane				= view.getRootPane() 		!= null ? view.getRootPane() 		: new JRootPane();
			JMenuBar								menuBar					= rootPane.getJMenuBar() 	!= null ? rootPane.getJMenuBar() 	: new JMenuBar();
			/* Adding toolbar and menu bar to the view */
			view.getRootPane().setJMenuBar(menuBar);
			view.getContentPane().add(toolBar,BorderLayout.NORTH);			
			 /* Populatinf toolbar and jmenubar */
			try {
				populateJToolBarMap(document.getDocumentElement(), buttonMap);
				populateJToolBar(buttonMap,toolBar);
				populateJMenuBar(document.getDocumentElement(), null,menuBar);
			} catch (Exception e) {
				throw new ViewException("Errors processing action descriptors: "+e.getMessage());
			}
		}
	}

	

	/**
	 * This method populates the actions into the ViewContainer's JToolBar
	 * 
	 * @param buttonMap
	 * @param toolBar
	 */
	private void populateJToolBar(Map<String, List<JButton>> buttonMap,JToolBar toolBar) {
		List<String> sortedList 		= new ArrayList<String>(buttonMap.keySet());
		List<String> cleanSortedList 	= new ArrayList<String>();
		for (String cleanKey : sortedList){
			if (cleanKey!=null)
				cleanSortedList.add(cleanKey);
		}
		Collections.sort(cleanSortedList);
		for (String lista : cleanSortedList){
			if (lista != null){
				List<JButton> list = buttonMap.get(lista);
				for (JButton button : list){
					toolBar.add(button);
				}			
				toolBar.addSeparator();
			}
		}		
	}

	/**
	 * This method populates the actions into the ViewContainer's JMenuBar 
	 * 
	 * @param node
	 * @param menu
	 * @param bar
	 */
	private void populateJMenuBar(Node node,JMenu menu,JMenuBar bar){
		JMenu 			newMenu 		= null;
		JMenuItem 		newItem 		= null;
		NodeList		children 		= null;		
		NamedNodeMap	nodeMap			= node.getAttributes();;
		boolean			isThereAtts		= nodeMap!=null && nodeMap.getLength() > 0;
		boolean			rootNode		= node.getNodeName().equalsIgnoreCase("menubar");
		String			itemEnabled		= isThereAtts && nodeMap.getNamedItem("enabled")	!=null ? nodeMap.getNamedItem("enabled").getNodeValue() : "true";
		String			itemVisible		= isThereAtts && nodeMap.getNamedItem("visible")	!=null ? nodeMap.getNamedItem("visible").getNodeValue() : "true";		
		String			menuName		= isThereAtts && nodeMap.getNamedItem("text")		!=null ? nodeMap.getNamedItem("text").getNodeValue() : node.getNodeName();		
		KeyStroke		keyStroke		= isThereAtts && nodeMap.getNamedItem("keyStroke")	!=null ? processKeyStrokeExpression(nodeMap.getNamedItem("keyStroke").getNodeValue()) : null;		
		ImageIcon		imageIcon		= isThereAtts && nodeMap.getNamedItem("icon")		!=null ? processIconExpression(nodeMap.getNamedItem("icon").getNodeValue()) : null;
	/*	If node has children */
		if (node.hasChildNodes()){
		 /* If it is not the rootNode it should be a new menu */
			if (!rootNode){				
				newMenu 		= new JMenu(menuName);
				children 		= node.getChildNodes();				
				newMenu.setName(node.getNodeName());
				newMenu.setEnabled(Boolean.valueOf(itemEnabled));
				newMenu.setVisible(Boolean.valueOf(itemVisible));
				if (keyStroke != null) newMenu.setAccelerator(keyStroke);
				if (imageIcon != null) newMenu.setIcon(imageIcon); 
			 /* If theres a parent menu it is nested within the parent node */
				if (menu!=null){
					menu.add(newMenu);
			 /* Otherwise it is added to the menu bar */
				} else {
					bar.add(newMenu);
				}				
		 /* If node is rootNode we should iterate over its children */
			} else {
				children 		= node.getChildNodes();	
			}
		 /* Whether if it is the rootNode or it is a rootNode's children we should continuing
		  * iterating over its children */
			for (int i=0;i<children.getLength();i++){
				populateJMenuBar(children.item(i),rootNode ? menu : newMenu ,bar);
			}			
	 /* If it has no children */
		} else {
		 /* If menu exists */
			if (menu!=null){
			 /* If it is not a separator item */
				if (!node.getNodeName().equalsIgnoreCase("separator")){					
					newItem = new JMenuItem(menuName);
					newItem.setName(node.getNodeName());		
					newItem.setEnabled(Boolean.valueOf(itemEnabled));
					newItem.setVisible(Boolean.valueOf(itemVisible));
					if (keyStroke != null) newItem.setAccelerator(keyStroke);
					if (imageIcon != null) { 
						newItem.setIcon(imageIcon); 
					} else {
						//newItem.setBorder(BorderFactory.createEmptyBorder(0,22,0,0));
					}
					menu.add(newItem);
			 /* If it is a separator item then we add a separator item */
				} else {
					menu.addSeparator();
				}
			}
		}
	}

	/**
	 * Before actions could be populated into the JToolBar it should be mapped first.
	 * 
	 * @param node
	 * @param menu
	 * @param bar
	 */
	private void populateJToolBarMap(Node node,Map<String,List<JButton>> mapeo){
		NamedNodeMap	nodeMap			= node.getAttributes();
		JButton			button			= null;
		List<JButton> 	buttonList		= null;
		NodeList		children		= null;
		boolean			isThereAtts		= nodeMap!=null && nodeMap.getLength() > 0;
	 /* Since 1.0.2 */
		String			toolButtonText	= isThereAtts && nodeMap.getNamedItem("enableToolButtonText")		!=null ? nodeMap.getNamedItem("enableToolButtonText").getNodeValue() : "true";
		String			itemEnabled		= isThereAtts && nodeMap.getNamedItem("enabled")					!=null ? nodeMap.getNamedItem("enabled").getNodeValue() : "true";
		String			itemVisible		= isThereAtts && nodeMap.getNamedItem("visible")					!=null ? nodeMap.getNamedItem("visible").getNodeValue() : "true";		
		String			menuName		= node.getNodeName();
		String			toolBarGroup	= isThereAtts && nodeMap.getNamedItem("toolBarGroup")				!=null ? nodeMap.getNamedItem("toolBarGroup").getNodeValue() : null;
		ImageIcon		imageIcon		= isThereAtts && nodeMap.getNamedItem("icon")						!=null ? processIconExpression(nodeMap.getNamedItem("icon").getNodeValue()) : null;		
	/*	If node has children */
		if (node.hasChildNodes()){
			children 	= node.getChildNodes();
		 /* Whether if it is the rootNode or it is a rootNode's children we should continuing
		  * iterating over its children */
			for (int i=0;i<children.getLength();i++){
				populateJToolBarMap(children.item(i),mapeo);
			}							
		} else {
			buttonList 	= mapeo.get(toolBarGroup);
			if (Boolean.valueOf(toolButtonText)){
				button 	= new JButton();
			} else {
				button =  new JButton();
			}
			button.setBorderPainted(false);
			button.setOpaque(false);
			button.setName(menuName);
			button.setEnabled(Boolean.valueOf(itemEnabled));
			button.setVisible(Boolean.valueOf(itemVisible));			
			if (imageIcon!=null){
				button.setIcon(imageIcon);
			}			
			if (buttonList == null){
				buttonList = new ArrayList<JButton>();

				buttonList.add(button);	
				mapeo.put(toolBarGroup, buttonList);
			} 
			buttonList.add(button);						
		}
	}

	/**
	 * This method helps actions to find out its icon images within the current class loader
	 * 
	 * @param nodeValue
	 * @return
	 */
	private ImageIcon processIconExpression(String nodeValue) {
		URL 		path = this.getClass().getClassLoader().getResource(nodeValue);
		ImageIcon 	icon = path != null ? new ImageIcon(path) : null;		
		return icon;
	}
	
	/**
	 * This method translates the expression written in the descriptors into keystrokes.
	 * 
	 * @param namedItem
	 * @return
	 */
	private KeyStroke processKeyStrokeExpression(String namedItem) {
		String[] 	parts 			= namedItem.split("_");
		boolean 	enoughArgs		= parts.length == 2;
		String 		validExpression = enoughArgs ? parts[0].toLowerCase()+" "+parts[1] : ""; 
		return KeyStroke.getKeyStroke(validExpression);
	}

	public void clean(ViewContainer view) throws ViewException {
		view.getContentPane().remove(view.getJToolBar());
		view.setJToolbar(null);
		view.getRootPane().setJMenuBar(null);
	}
}