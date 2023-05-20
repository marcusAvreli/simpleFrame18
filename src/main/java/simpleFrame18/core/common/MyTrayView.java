package simpleFrame18.core.common;

import simpleFrame18.core.annotation.Controller;
import simpleFrame18.core.annotation.Controllers;
import simpleFrame18.core.view.AbstractViewContainerTray;

@Controllers({
	@Controller(type=MyTrayViewController.class,pattern="hiderestore")
})
public class MyTrayView extends AbstractViewContainerTray{
	
	public static final String ID = "MyTrayViewID";
	
	public MyTrayView(){
		super(ID);
	}
}