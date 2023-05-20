package simpleFrame18;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import simpleFrame18.api.view.perspective.PerspectiveConstraint;
import simpleFrame18.core.annotation.View;
import simpleFrame18.core.annotation.Views;
import simpleFrame18.core.annotation.ViewsPerspective;
import simpleFrame18.core.common.MyTrayView;
import simpleFrame18.core.common.TestFrame;
import simpleFrame18.core.common.TestView;
import simpleFrame18.core.core.DefaultApplication;
import simpleFrame18.core.core.DefaultApplicationLauncher;
import simpleFrame18.core.view.perspective.DefaultPerspective;




//https://github.com/mariogarcia/viewa/blob/c39f7f46dc39908bd23cd4ded0b60c5f555617b8/core/src/test/java/org/viewaframework/common/TestApplication.java
@ViewsPerspective(DefaultPerspective.class)
@Views({
	@View(type = TestFrame.class, isRoot = true)
})
public class App  extends DefaultApplication 
{
	private static final Logger logger = LoggerFactory.getLogger(App.class);
    public static void main( String[] args ){     
    	try {
			new DefaultApplicationLauncher().execute(App.class);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    	
    	/*ApplicationTrapper 	app = new ApplicationTrapper(
				new DefaultApplicationLauncher().
					execute(App.class));
    	*/
    }
}
