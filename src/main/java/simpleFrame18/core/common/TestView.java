package simpleFrame18.core.common;

import java.util.Arrays;

import simpleFrame18.core.annotation.Controllers;
import simpleFrame18.api.view.ViewActionDescriptor;
import simpleFrame18.core.annotation.Controller;
import simpleFrame18.core.view.DefaultViewContainer;


/**
 * @author Mario Garcia
 * @since 1.0.2
 *
 */
@Controllers({
	@Controller(type=TestController.class,pattern="testButton"),
	@Controller(type=TestControllerFailure.class,pattern="failureButton")
})
public class TestView extends DefaultViewContainer{
	public static final String ID = "ControllerTestViewId";	
	/**
	 * Default constructor
	 */
	public TestView(){
		super(ID,new TestPanel());
		this.setActionDescriptors(Arrays.asList(
				new ViewActionDescriptor("/edit[@text='edit']")));
	}	
}