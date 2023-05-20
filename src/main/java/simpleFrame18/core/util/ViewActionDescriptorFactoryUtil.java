package simpleFrame18.core.util;

import simpleFrame18.api.view.ViewActionDescriptor;

public final class ViewActionDescriptorFactoryUtil {
	
	private ViewActionDescriptorFactoryUtil(){}
	
	public static final ViewActionDescriptor ACTION_ROOT = new ViewActionDescriptor("/root[@text='root']");
	public static final ViewActionDescriptor ACTION_EXIT = new ViewActionDescriptor("/root/exit[@keyStroke='CTRL_T' and @icon='org/viewaframework/widget/icon/fan/img/door/door_out.png']");
	
	public static final ViewActionDescriptor ACTION_EDIT = new ViewActionDescriptor("/edit[@text='edit']");
	public static final ViewActionDescriptor ACTION_EDIT_CUT = new ViewActionDescriptor("/edit/cut[@text='cut' and @keyStroke='CTRL_X' and @icon='org/viewaframework/widget/icon/fan/img/cut/cut.png']");
	public static final ViewActionDescriptor ACTION_EDIT_COPY = new ViewActionDescriptor("/edit/copy[@text='copy' and @keyStroke='CTRL_C' and @icon='org/viewaframework/widget/icon/fan/img/page/page_copy.png']");
	public static final ViewActionDescriptor ACTION_EDIT_PASTE = new ViewActionDescriptor("/edit/paste[@text='paste' and @keyStroke='CTRL_V' and @icon='org/viewaframework/widget/icon/fan/img/paste/paste_plain.png']");
	public static final ViewActionDescriptor ACTION_EDIT_SEPARATOR =   new ViewActionDescriptor("/edit/separator[@text='editSeparator']");
	public static final ViewActionDescriptor ACTION_EDIT_SELECT_ALL = new ViewActionDescriptor("/edit/selectAll[@text='selectAll' and @keyStroke='CTRL_A']");
	
	public static final ViewActionDescriptor ACTION_HELP = new ViewActionDescriptor("/help[@text='help']");
	public static final ViewActionDescriptor ACTION_HELP_ABOUT = new ViewActionDescriptor("/help/about[@text='about' and @keyStroke='CTRL_H' and @icon='org/viewaframework/widget/icon/fan/img/misc/help.png']");
}