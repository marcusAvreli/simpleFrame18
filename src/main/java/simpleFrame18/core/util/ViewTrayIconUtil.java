package simpleFrame18.core.util;

import java.awt.SystemTray;
import java.awt.Toolkit;

/**
 * This class helps the tray icon to get the right "Y" coordinate depending on
 * the operating system.
 * 
 * @author Mario Garcia
 * @since 1.0.4
 *
 */
public class ViewTrayIconUtil {
	
	private static final String ENV_VARIABLE_LINUX_DESKTOP_SESSION = "DESKTOP_SESSION";
	private static final String OS_LINUX = "linux";
	private static final String OS_LINUX_DESKTOP_GNOME = "gnome";
	private static final String OS_LINUX_DESKTOP_KDE = "kde";
	private static final String OS_WINDOWS = "windows";
	private static final String SYSTEM_PROPERTY_OS_NAME = "os.name";	
	
	/**
	 * @return
	 */
	private static int getBottomTrayIconYCordinate(){
		return Toolkit.getDefaultToolkit().getScreenSize().height - SystemTray.getSystemTray().getTrayIconSize().height;
	}
	
	/**
	 * @return
	 */
	public static String getOSName(){
		return System.getProperty(SYSTEM_PROPERTY_OS_NAME);
	}
	
	/**
	 * Returns the "Y" cordinate.
	 * 
	 * @return
	 */
	public static int getSystemTrayYPosition(){
		int result = 0;
		if (isWindows()){
			result = getBottomTrayIconYCordinate();
		} else if (isLinux()){
			String desktop_session = System.getenv(ENV_VARIABLE_LINUX_DESKTOP_SESSION).toLowerCase();
			if (desktop_session.indexOf(OS_LINUX_DESKTOP_GNOME) != -1){
				result = getTopTrayIconYCordinate();
			} else if (desktop_session.indexOf(OS_LINUX_DESKTOP_KDE)!=-1){
				result = getBottomTrayIconYCordinate();
			}
		} else {
			result = getBottomTrayIconYCordinate();
		}
		return result;
	}
	
	/**
	 * @return
	 */
	private static int getTopTrayIconYCordinate(){
		return SystemTray.getSystemTray().getTrayIconSize().height;  
	}
	
	/**
	 * @return
	 */
	public static boolean isLinux(){
		return getOSName().toLowerCase().indexOf(OS_LINUX) != -1;
	}
	
	/**
	 * @return
	 */
	public static boolean isWindows(){
		return getOSName().toLowerCase().indexOf(OS_WINDOWS) != -1;
	}
}