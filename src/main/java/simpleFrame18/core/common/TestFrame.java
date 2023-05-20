package simpleFrame18.core.common;

import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JRootPane;
import javax.swing.JToolBar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import simpleFrame18.App;
import simpleFrame18.api.controller.ViewController;
import simpleFrame18.api.core.Application;
import simpleFrame18.api.model.ViewModel;
import simpleFrame18.api.view.ViewActionDescriptor;
import simpleFrame18.api.view.ViewContainerFrame;
import simpleFrame18.api.view.ViewException;
import simpleFrame18.api.view.ViewManager;
import simpleFrame18.api.view.event.ViewContainerEvent;
import simpleFrame18.api.view.event.ViewContainerEventController;
import simpleFrame18.core.annotation.Controller;
import simpleFrame18.core.annotation.Controllers;
@Controllers({
	@Controller(type=TestController.class,pattern="testButton")
})
public class TestFrame implements ViewContainerFrame{
	private static final Logger logger = LoggerFactory.getLogger(TestFrame.class);
	private JRootPane rootPane;
	private JFrame frame;
	private Application app;
	public TestFrame() {
		frame = new JFrame();
		rootPane = new JRootPane();
	}
	@Override
	public List<ViewActionDescriptor> getActionDescriptors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image getIconImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return ViewManager.ROOT_VIEW_ID;
	}

	@Override
	public JToolBar getJToolBar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIconImage(Image image) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setId(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setJToolbar(JToolBar toolBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewClose() throws ViewException {
		// TODO Auto-generated method stub
		logger.info("view close");
	}

	@Override
	public void viewFinalUIState() throws ViewException {
		// TODO Auto-generated method stub
		logger.info("viewFinalUIState");
	}

	@Override
	public void viewInit() throws ViewException {
		// TODO Auto-generated method stub
		logger.info("viewInit");
	}

	@Override
	public void viewInitBackActions() throws ViewException {
		// TODO Auto-generated method stub
		logger.info("viewInitBackActions");
	}

	@Override
	public void viewInitUIState() throws ViewException {
		// TODO Auto-generated method stub
		logger.info("viewInitUIState");
	}

	@Override
	public void fireViewClose(ViewContainerEvent event) {
		// TODO Auto-generated method stub
		logger.info("fireViewClose");
	}

	@Override
	public void fireViewInit(ViewContainerEvent event) {
		// TODO Auto-generated method stub
		logger.info("fireViewInit");
	}

	@Override
	public void fireViewInitUIState(ViewContainerEvent event) {
		// TODO Auto-generated method stub
		logger.info("fireViewInitUIState");
	}

	@Override
	public void fireViewInitBackActions(ViewContainerEvent event) {
		// TODO Auto-generated method stub
		logger.info("fireViewInitBackActions");
	}

	@Override
	public void fireViewFinalUIState(ViewContainerEvent event) {
		// TODO Auto-generated method stub
		logger.info("fireViewFinalUIState");
	}

	@Override
	public void addViewContainerListener(ViewContainerEventController listener) {
		// TODO Auto-generated method stub
		logger.info("addViewContainerListener");
	}

	@Override
	public void removeViewContainerListener(ViewContainerEventController listener) {
		// TODO Auto-generated method stub
		logger.info("removeViewContainerListener");
	}

	@Override
	public void setViewContainerListeners(List<ViewContainerEventController> listeners) {
		// TODO Auto-generated method stub
		logger.info("setViewContainerListeners");
	}

	@Override
	public List<ViewContainerEventController> getViewContainerListeners() {
		logger.info("getViewContainerListeners");
		return null;
	}

	@Override
	public void setApplication(Application application) {
		this.app=application;
		
	}

	@Override
	public Application getApplication() {
		// TODO Auto-generated method stub
		return this.app;
	}

	@Override
	public List<Component> getComponentsByName(String name) {
		// TODO Auto-generated method stub
		logger.info("getComponentsByName");
		return null;
	}

	@Override
	public Component getComponentByName(String name) {
		logger.info("getComponentByName");
		return null;
	}

	@Override
	public Map<String, List<Component>> getNamedComponents() {
		// TODO Auto-generated method stub
		logger.info("getNamedComponents");
		return null;
	}

	@Override
	public void setNamedComponents(Map<String, List<Component>> namedComponents) {
		// TODO Auto-generated method stub
		logger.info("setNamedComponents");
	}

	@Override
	public void setViewControllerMap(
			Map<String, List<ViewController<? extends EventListener, ? extends EventObject>>> viewControllerMap) {
		// TODO Auto-generated method stub
		logger.info("setViewControllerMap");
		
	}

	@Override
	public Map<String, List<ViewController<? extends EventListener, ? extends EventObject>>> getViewControllerMap() {
		logger.info("getViewControllerMap");
		return null;
	}

	@Override
	public JRootPane getRootPane() {
		// TODO Auto-generated method stub
		return rootPane;
	}

	@Override
	public void setContentPane(Container contentPane) {
		// TODO Auto-generated method stub
		logger.info("setContentPane");
	}

	@Override
	public Container getContentPane() {
		// TODO Auto-generated method stub
		logger.info("getContentPane");
		return null;
	}

	@Override
	public void setLayeredPane(JLayeredPane layeredPane) {
		// TODO Auto-generated method stub
		logger.info("setLayeredPane");
	}

	@Override
	public JLayeredPane getLayeredPane() {
		logger.info("getLayeredPane");
		return null;
	}

	@Override
	public void setGlassPane(Component glassPane) {
		// TODO Auto-generated method stub
		logger.info("setGlassPane");
	}

	@Override
	public Component getGlassPane() {
		logger.info("getGlassPane");
		return null;
	}

	@Override
	public String getMessage(String key) {
		logger.info("getMessage");
		return null;
	}

	@Override
	public ResourceBundle getMessageBundle() {
		logger.info("getMessageBundle");
		return null;
	}

	@Override
	public void setMessageBundle(ResourceBundle messageBundle) {
		logger.info("setMessageBundle");
		
	}

	@Override
	public void addModelValue(String alias, Object object) {
		logger.info("addModelValue");
		
	}

	@Override
	public Object getModelValue(String alias) {
		logger.info("getModelValue");
		return null;
	}

	@Override
	public Map<String, ViewModel> getViewModelMap() {
		logger.info("getViewModelMap");
		return null;
	}

	@Override
	public void setViewModelMap(Map<String, ViewModel> viewModelMap) {
		// TODO Auto-generated method stub
		logger.info("setViewModelMap");
	}

	@Override
	public JFrame getFrame() {
		// TODO Auto-generated method stub
		return frame;
	}

}
