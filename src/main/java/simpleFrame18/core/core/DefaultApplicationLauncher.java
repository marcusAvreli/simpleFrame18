package simpleFrame18.core.core;

import simpleFrame18.api.core.Application;

/**
 * This is the launcher of the <code>Application</code>.
 * It launches the application lifecycle in a new Thread.
 * 
 * @author Mario Garcia
 * @since 1.0
 *
 */
public class DefaultApplicationLauncher extends  AbstractApplicationLauncher
{
	private Class<? extends Application> clazz;
	private Application application;
	
	/* (non-Javadoc)
	 * @see org.viewaframework.core.AbstractApplicationLauncher#execute(java.lang.Class)
	 */
	public synchronized Application execute(final Class<? extends Application> app) throws Exception {
		this.clazz = app;
		return super.execute(app);
	}

	/* (non-Javadoc)
	 * @see org.viewaframework.core.AbstractApplicationLauncher#getApplication()
	 */
	@Override
	public Application getApplication() {
		if (application == null){
			try {
				application = clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return application;
	}
}
