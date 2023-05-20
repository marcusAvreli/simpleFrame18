package simpleFrame18.core.common;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Mario Garcia
 * @since 1.0.2
 *
 */
public class TestPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JButton testButton;
	private JButton failureButton;
	private JTextField text;
	
	/**
	 * 
	 */
	public TestPanel(){
		super();
		this.text = new JTextField();
		this.text.setName("text");
		this.testButton = new JButton("test");
		this.testButton.setName("testButton");
		this.failureButton = new JButton("failure");
		this.failureButton.setName("failureButton");
		this.setLayout(new GridLayout(3,1));
		this.add(testButton);
		this.add(failureButton);
		this.add(text);
	}
}