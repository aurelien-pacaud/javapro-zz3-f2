package org.isifaces.component;

import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

public class IsiComponentCoreUtils {
	
	public static final String COMPONENT_FAMILY = "org.isifaces";
	
	
	public static String resolveWidgetVar(UIComponent component) {
		FacesContext context = FacesContext.getCurrentInstance();
		String userWidgetVar = (String) component.getAttributes().get("widgetVar");

		if(userWidgetVar != null)
			return userWidgetVar;
		 else
			return "widget_" + component.getClientId(context).replaceAll("-|" + UINamingContainer.getSeparatorChar(context), "_");
	}

}
