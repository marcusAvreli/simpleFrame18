package simpleFrame18.core.view;

import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import simpleFrame18.App;
import simpleFrame18.api.view.ViewActionDescriptor;
import simpleFrame18.core.util.StatusBar;
import simpleFrame18.core.util.ViewActionDescriptorFactoryUtil;

/**
 * @author Mario Garcia
 * @since 1.0
 * 
 */
public class DefaultViewContainerFrame extends AbstractViewContainerFrame {
	private static final Logger logger = LoggerFactory.getLogger(DefaultViewContainerFrame.class);
	private static final List<ViewActionDescriptor> DEFAULT_ACTION_DESCRIPTORS = Arrays.asList(new ViewActionDescriptor[]{
			ViewActionDescriptorFactoryUtil.ACTION_ROOT,
			ViewActionDescriptorFactoryUtil.ACTION_EXIT,
			ViewActionDescriptorFactoryUtil.ACTION_EDIT,
			ViewActionDescriptorFactoryUtil.ACTION_EDIT_COPY,
			ViewActionDescriptorFactoryUtil.ACTION_EDIT_CUT,
			ViewActionDescriptorFactoryUtil.ACTION_EDIT_PASTE,
			ViewActionDescriptorFactoryUtil.ACTION_EDIT_SEPARATOR,
			ViewActionDescriptorFactoryUtil.ACTION_EDIT_SELECT_ALL,
			ViewActionDescriptorFactoryUtil.ACTION_HELP,
			ViewActionDescriptorFactoryUtil.ACTION_HELP_ABOUT
	});
	
	
	
	/**
	 * 
	 */
	public DefaultViewContainerFrame(){
		super();
		
		logger.info("content_pane_parent:"+this.getContentPane().getParent().getClass().getSimpleName());
		logger.info("content_pane:"+this.getContentPane().getClass().getSimpleName());
		this.setActionDescriptors(DefaultViewContainerFrame.DEFAULT_ACTION_DESCRIPTORS);
		//this.getContentPane().add(new StatusBar(),BorderLayout.SOUTH);
		//this.getContentPane().add(new StatusBar(),BorderLayout.SOUTH);
	}	
}