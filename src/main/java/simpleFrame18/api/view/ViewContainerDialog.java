package simpleFrame18.api.view;

import javax.swing.JDialog;

import javax.swing.JDialog;

public interface ViewContainerDialog extends ViewContainer{

	public static final String VIEW_DIALOG_NAME ="viewDialog";
	
	public void setDialog(JDialog dialog);
	
	public JDialog getDialog();
	
}
