package simpleFrame18.core.util;

import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

import simpleFrame18.api.view.ViewException;


/**
 * This is a extended implementation of the java.awt.TrayIcon implementation. It has two JPopupMenu
 * depending on if the user has clicked the tray icon with the right button or with the left button.
 * 
 * @author Mario Garcia
 * @since 1.0.4
 *
 */
public class ViewTrayIcon extends TrayIcon{

	/**
	 * This listener is responsible for the behavior of the visibility
	 * of the JPopupMenu's
	 * 
	 * @author Mario Garcia
	 * @since 1.0.4
	 *
	 */
	private class ViewTrayIconMouseAdapter extends MouseAdapter{
		private int button;
		private JPopupMenu principal;
		private JPopupMenu secondary;
		private int height;
		
		/**
		 * Initializing the listener with the principal and secondary menus. And the button
		 * that triggers the event.
		 * 
		 * @param principal
		 * @param secondary
		 * @param button
		 */
		public ViewTrayIconMouseAdapter(JPopupMenu principal,JPopupMenu secondary,int button){
			this.principal = principal;
			this.secondary = secondary;
			this.button = button;
			this.height = ViewTrayIconUtil.getSystemTrayYPosition();
		}

		public void mouseReleased(MouseEvent e) {
			if (e.getButton() == button){			
			 /* If the secondary menu is visible we have to hide it */
				if (secondary != null && secondary.isVisible()){
					secondary.setVisible(false);
				} else {						
			 /* If the secondary menu was already hidden then principal menu must be shown. */
				 // TODO Windows positioning doesn't work and I don't know why !ViewTrayIconUtil.isLinux() is a patch
					principal.setLocation(e.getPoint().x,!ViewTrayIconUtil.isLinux() ? e.getPoint().y : height);
					principal.setVisible(!principal.isVisible());
				}
			}
		}
	}
	
	public static final String TRAY_VIEW_MENU_LEFT = "TRAY_VIEW_MENU_LEFT";	
	public static final String TRAY_VIEW_MENU_RIGHT = "TRAY_VIEW_MENU_RIGHT";
	private JPopupMenu leftClickMenu;
	private JPopupMenu rightClickMenu;

	/**
	 * Initializing the tray icon with an image. If the image is null an Exception is
	 * thrown.
	 * 
	 * @param image
	 */
	public ViewTrayIcon(Image image) {
		super(image);
	}

	/**
	 * Initializing the tray icon with an image, and both menus. If the image is null an Exception is
	 * thrown.
	 * 
	 * @param image
	 * @param right
	 * @param left
	 */
	public ViewTrayIcon(Image image,JPopupMenu right,JPopupMenu left){
		this(image);
		this.rightClickMenu = right;
		this.leftClickMenu = left;
		this.initComponents();
	
	}

	/**
	 * Returns the JPopupMenu when clicked with the left button
	 * 
	 * @return
	 */
	public JPopupMenu getLeftClickMenu() {
		return leftClickMenu;
	}

	/**
	 * Returns the JPopupMenu when clicked with the right button
	 * 
	 * @return
	 */
	public JPopupMenu getRightClickMenu() {
		return rightClickMenu;
	}

	/**
	 *  Initializes all components
	 */
	private void initComponents() {
		if (this.rightClickMenu != null){
			this.rightClickMenu.setName(TRAY_VIEW_MENU_RIGHT);
			this.addMouseListener(new ViewTrayIconMouseAdapter(getRightClickMenu(),getLeftClickMenu(),MouseEvent.BUTTON3));
		}
		if (this.leftClickMenu != null){
			this.leftClickMenu.setName(TRAY_VIEW_MENU_LEFT);
			this.addMouseListener(new ViewTrayIconMouseAdapter(getLeftClickMenu(),getRightClickMenu(),MouseEvent.BUTTON1));
		}		
	}
	
	/**
	 * Sets the JPopupMenu when clicked with the left button
	 * 
	 * @param leftClickMenu
	 */
	public void setLeftClickMenu(JPopupMenu leftClickMenu) {
		this.leftClickMenu = leftClickMenu;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.TrayIcon#setPopupMenu(java.awt.PopupMenu)
	 */
	@Override
	public void setPopupMenu(PopupMenu popup) {
		throw new RuntimeException(new ViewException("Use setLeftClickMenu and setRightClickMenu instead"));
	}

	/**
	 * Sets the JPopupMenu when clicked with the right button
	 * 
	 * @param rightClickMenu
	 */
	public void setRightClickMenu(JPopupMenu rightClickMenu) {
		this.rightClickMenu = rightClickMenu;
	}
}