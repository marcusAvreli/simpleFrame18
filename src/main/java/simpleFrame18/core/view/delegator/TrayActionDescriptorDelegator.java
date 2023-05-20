package simpleFrame18.core.view.delegator;

import java.awt.SystemTray;

import javax.swing.JPopupMenu;


import org.w3c.dom.Document;

import simpleFrame18.api.view.ViewContainer;
import simpleFrame18.api.view.ViewException;
import simpleFrame18.api.view.delegator.Delegator;
import simpleFrame18.core.util.ViewActionDescriptorFileHandler;
import simpleFrame18.core.util.ViewTrayIcon;
import simpleFrame18.core.util.ViewTrayIconBuilder;
import simpleFrame18.core.view.AbstractViewContainerTray;

/**
 * This Delegator builds the tray icon's view and adds the tray icon to the 
 * system tray. Once the view has been closed it removes the tray icon from the
 * system tray.
 * 
 * @author Mario Garcia
 * @since 1.0.4
 *
 */
public class TrayActionDescriptorDelegator implements Delegator{

	/**
	 * It's a shortcut
	 * 
	 * @param view
	 * @return
	 */
	private AbstractViewContainerTray castView(ViewContainer view) {
		return AbstractViewContainerTray.class.cast(view);
	}

	/* (non-Javadoc)
	 * @see org.viewaframework.view.delegator.Delegator#inject(org.viewaframework.view.ViewContainer)
	 */
	public void inject(ViewContainer view) throws ViewException {
		if (isRightViewType(view) && SystemTray.isSupported()){
			AbstractViewContainerTray trayView = castView(view);
			ViewActionDescriptorFileHandler handler = new ViewActionDescriptorFileHandler();
			Document document = handler.getDocument(trayView);			
			ViewTrayIconBuilder builder = new ViewTrayIconBuilder(document);
			try {
				ViewTrayIcon trayIcon = builder.build();
				addMenuToContentPane(view,trayIcon.getLeftClickMenu());
				addMenuToContentPane(view,trayIcon.getRightClickMenu());
				trayView.setTrayIcon(trayIcon);		
				SystemTray.getSystemTray().add(trayIcon);
			} catch (Exception e) {
				throw new ViewException(e.getMessage());
			}
		}		
	}

	/**
	 * A checked way of adding the menu to the content pane. This way both JPopupMenu are
	 * available through all views
	 * 
	 * @param view
	 * @param menu
	 */
	private void addMenuToContentPane(ViewContainer view,
			JPopupMenu menu) {
		if (menu != null){
			view.getContentPane().add(menu);
		}		
	}
	
	/**
	 * A checked way of hiding a menu
	 * 
	 * @param menu
	 */
	private void hideMenu(JPopupMenu menu){
		if (menu != null){
			menu.setVisible(false);
		}
	}

	/* (non-Javadoc)
	 * @see org.viewaframework.view.delegator.Delegator#clean(org.viewaframework.view.ViewContainer)
	 */
	public void clean(ViewContainer view) throws ViewException {
		if (isRightViewType(view) && SystemTray.isSupported()){
			AbstractViewContainerTray trayView = castView(view);			
			ViewTrayIcon viewTrayIcon = trayView.getTrayIcon();
		 /* Everything must be hidden before removing tray icon from system tray */
			hideMenu(viewTrayIcon.getLeftClickMenu());
			hideMenu(viewTrayIcon.getRightClickMenu());
		 /* Now we can safely remove the tray icon from system tray */
			SystemTray.getSystemTray().remove(viewTrayIcon);
		}
	}

	/**
	 * Checking if the view is the right one.
	 * 
	 * @param view
	 * @return
	 */
	private boolean isRightViewType(ViewContainer view) {
		return AbstractViewContainerTray.class
				.isAssignableFrom(view.getClass());
	}
}
