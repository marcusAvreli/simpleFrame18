package simpleFrame18.core.util;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * This class is responsible for building the right and left JPopupMenu from the TrayIcon depending
 * on the menu.xml file from the tray icon's view.
 * 
 * @author Mario Garcia
 * @since 1.0.4
 *
 */
public class ViewTrayIconBuilder {
	
	/**
	 * This class has been built because there's no mouseover behavior in
	 * the JPopupMenu elements. This fix shows a different background
	 * whether the mouse is over the element or not.
	 * 
	 * @author Mario Garcia
	 * @since 1.0.4
	 *
	 */
	private class JMenuItemDefaultBehaviorAdapter extends MouseAdapter{
		/* (non-Javadoc)
		 * @see java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
		 */
		public void mouseEntered(MouseEvent e) {
			JMenuItem item = JMenuItem.class.cast(e.getComponent());
			item.setBackground(UIManager.getColor("MenuItem.selectionBackground"));
			item.setForeground(UIManager.getColor("MenuItem.selectionForeground"));
		}
		/* (non-Javadoc)
		 * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseExited(MouseEvent e) {
			JMenuItem item = JMenuItem.class.cast(e.getComponent());
			item.setBackground(UIManager.getColor("MenuItem.background"));
			item.setForeground(UIManager.getColor("MenuItem.foreground"));
		}
		/* (non-Javadoc)
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			JMenuItem item = JMenuItem.class.cast(e.getComponent());
			item.getParent().setVisible(false);
		}
	}
	
	private static final String TRAY_RIGHT_MENU_NODE_NAME = "rightMenu";
	private static final String TRAY_LEFT_MENU_NODE_NAME = "leftMenu";
	private Document document;
	
	/**
	 * Initializing the builder with the xml representation of the tray icon
	 * 
	 * @param document
	 */
	public ViewTrayIconBuilder(Document document){
		this.document = document;
	}
	
	/**
	 * Populates the right menu
	 * 
	 * @return
	 */
	private JPopupMenu populateRightMenu(){
		return populateMenu(TRAY_RIGHT_MENU_NODE_NAME);
	}
	
	/**
	 * Populates the left menu
	 * 
	 * @return
	 */
	private JPopupMenu populateLeftMenu(){
		return populateMenu(TRAY_LEFT_MENU_NODE_NAME);
	}
	
	/**
	 * Populates the menu which name is passed as argument
	 * 
	 * @param name The name of the menu to populate
	 * @return
	 */
	private JPopupMenu populateMenu(String name){
		NodeList list = document.getElementsByTagName(name);
		JPopupMenu menu = null;
		if (list != null && list.getLength() > 0){
			Node currentNode = list.item(0);
			menu = populateJMenuBar(name,currentNode,null,new JPopupMenu());
		}
		return menu;
	}
	
	/**
	 * It creates a blank image because at this point there's no image for the tray icon
	 * 
	 * @return
	 */
	private Image createBlankImage(){
		BufferedImage image = new BufferedImage(24, 24, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 =image.createGraphics();
		g2.setBackground(Color.white);
		g2.setPaint(Color.WHITE);
		g2.fillRect(0,0,image.getWidth(),image.getHeight());		
		return image;
	}
	
	/**
	 * This method launches the building process of a ViewTrayIcon object
	 * 
	 * @return
	 */
	public ViewTrayIcon build() {			
		JPopupMenu rigthMenu = populateRightMenu();
		JPopupMenu leftMenu = populateLeftMenu();
		ViewTrayIcon trayIcon = new ViewTrayIcon(createBlankImage(),rigthMenu,leftMenu);
		return trayIcon;
	}
	
	/**
	 * This method loops over the xml representation of the tray view and builds the menus
	 * 
	 * @param partialRootNode
	 * @param node
	 * @param menu
	 * @param bar
	 * @return
	 */
	private JPopupMenu populateJMenuBar(String partialRootNode,Node node,JPopupMenu menu,JPopupMenu bar){
		JPopupMenu 		newMenu 		= null;		
		JMenuItem 		newItem 		= null;
		NodeList		children 		= null;		
		NamedNodeMap	nodeMap			= node.getAttributes();;
		boolean			isThereAtts		= nodeMap!=null && nodeMap.getLength() > 0;
		boolean			rootNode		= node.getNodeName().equalsIgnoreCase(partialRootNode);
		String			itemEnabled		= isThereAtts && nodeMap.getNamedItem("enabled")	!=null ? nodeMap.getNamedItem("enabled").getNodeValue() : "true";
		String			itemVisible		= isThereAtts && nodeMap.getNamedItem("visible")	!=null ? nodeMap.getNamedItem("visible").getNodeValue() : "true";		
		String			menuName		= isThereAtts && nodeMap.getNamedItem("text")		!=null ? nodeMap.getNamedItem("text").getNodeValue() : node.getNodeName();		
		KeyStroke		keyStroke		= isThereAtts && nodeMap.getNamedItem("keyStroke")	!=null ? processKeyStrokeExpression(nodeMap.getNamedItem("keyStroke").getNodeValue()) : null;		
		ImageIcon		imageIcon		= isThereAtts && nodeMap.getNamedItem("icon")		!=null ? processIconExpression(nodeMap.getNamedItem("icon").getNodeValue()) : null;
	/*	If node has children */
		if (node.hasChildNodes()){
		 /* If it is not the rootNode it should be a new menu */
			if (!rootNode){				
				newMenu 		= new JPopupMenu(menuName);
				children 		= node.getChildNodes();				
				newMenu.setName(node.getNodeName());
				newMenu.setEnabled(Boolean.valueOf(itemEnabled));
				newMenu.setVisible(Boolean.valueOf(itemVisible));
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
				populateJMenuBar(partialRootNode,children.item(i),rootNode ? bar : newMenu ,bar);
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
					newItem.addMouseListener(new JMenuItemDefaultBehaviorAdapter());
					if (keyStroke != null) {
						newItem.setAccelerator(keyStroke);
					}
					if (imageIcon != null) { 
						newItem.setIcon(imageIcon); 
					}
					menu.add(newItem);
			 /* If it is a separator item then we add a separator item */
				} else {
					menu.addSeparator();
				}
			}
		}
		
		return bar;
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
}