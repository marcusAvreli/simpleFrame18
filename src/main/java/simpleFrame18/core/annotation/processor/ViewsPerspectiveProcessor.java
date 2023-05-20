package simpleFrame18.core.annotation.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import simpleFrame18.App;
import simpleFrame18.api.core.Application;
import simpleFrame18.api.view.perspective.Perspective;
import simpleFrame18.core.annotation.ViewsPerspective;

/**
 * This processor handles the @ViewsPerspective annotation
 * 
 * @author Mario Garcia
 * @since 1.0.2
 *
 */
public class ViewsPerspectiveProcessor implements AnnotationProcessor{

	private static final Logger logger = LoggerFactory.getLogger(ViewsPerspectiveProcessor.class);
	private Application app;
	private Class<? extends Perspective> perspectiveClass;
	
	/**
	 * @param app
	 */
	public ViewsPerspectiveProcessor(Application app){
		this.app = app;
	}
	
	/* (non-Javadoc)
	 * @see org.viewaframework.annotation.processor.AnnotationProcessor#getResult()
	 */
	public Object getResult() {
		Perspective perspective = null;		
		try {
			if (perspectiveClass != null){
				perspective = perspectiveClass.newInstance();
			}
		} catch (Exception e) {
			logger.warn("Error while processing ViewsPerspective annotation at startup");
		} 		
		return perspective;
	}

	/* (non-Javadoc)
	 * @see org.viewaframework.annotation.processor.AnnotationProcessor#process()
	 */
	public void process() throws Exception {
		if (logger.isDebugEnabled()){
			logger.debug("Processing @ViewsPerspective annotation");
		}
		ViewsPerspective perspectiveAnnotation = this.app.getClass().getAnnotation(ViewsPerspective.class);
		if (perspectiveAnnotation != null){
			this.perspectiveClass = perspectiveAnnotation.value();
		}
	}

}
