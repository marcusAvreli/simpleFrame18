package simpleFrame18.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This class will add a group of listeners to the annotated view
 * 
 * @author mgg
 * @since 1.0.7
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Listeners {	
	/**
	 * The group of listeners the view is going to keep posted
	 * 
	 * @return
	 */
	Listener[] value();
}
