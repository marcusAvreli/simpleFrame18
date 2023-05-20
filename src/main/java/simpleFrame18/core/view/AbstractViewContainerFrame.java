package simpleFrame18.core.view;

import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;

import simpleFrame18.api.view.ViewContainerFrame;



public abstract class AbstractViewContainerFrame extends AbstractViewContainer implements ViewContainerFrame
{
	
	private JFrame frame;

	/**
	 * 
	 */
	public AbstractViewContainerFrame(){
		super("root_id");
	}

	
	

	/* (non-Javadoc)
	 * @see org.viewaframework.view.ViewContainerFrame#getFrame()
	 */
	public JFrame getFrame() {
		if (this.frame == null){
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();     
			GraphicsDevice 		gd = ge.getDefaultScreenDevice();
			this.frame = new JFrame(this.getId());
			//TODO
			this.frame.getContentPane().setLayout(new BorderLayout());
			
			this.frame.setName(FRAME);
			this.frame.addWindowListener(new WindowAdapter(){
				@Override
				public void windowClosing(WindowEvent arg0) {
					getApplication().close();
				}
			});
			this.frame.setBounds(gd.getDefaultConfiguration().getBounds());		
			this.frame.setLocationByPlatform(true);
		}
		return this.frame;
	}

	/**
	 * Setting the application icon
	 * 
	 * @return
	 */
	public Image getIconImage() {
		return this.getFrame()!=null ? this.getFrame().getIconImage():null;
	}
	
	
	/**
	 * Getting the application view
	 * 
	 * @param iconImage
	 */
	public void setIconImage(Image iconImage) {
		if(this.getFrame()!=null)this.getFrame().setIconImage(iconImage);
	}
}