/*
 * $Id: RequiredStyle.java 80 2012-11-20 21:34:38Z pj.chartre@gmail.com $
 * 
 * Copyright to Michelin Corporation.
 */
package org.isifaces.component.requiredstyle;

import javax.faces.component.UIComponentBase;

import org.isifaces.component.IsiComponentCoreUtils;

/**
 * The required style component analyze component value binding to set a style class if the 
 * value binding is required.
 *
 */
public class RequiredStyle extends UIComponentBase {
	
	public static final String COMPONENT_TYPE = "org.isifaces.component.requiredstyle.RequiredStyle";
	public static final String DEFAULT_RENDERER = "org.isifaces.component.requiredstyle.RequiredStyleRenderer";
	
	/**
     * Properties that are tracked by state saving.
     */
    enum PropertyKeys {
    	styleClass("styleClass");
        
        String toString;
		PropertyKeys(String toString) {
			this.toString = toString;
		}
		PropertyKeys() {}
		public String toString() {
			return ((this.toString != null) ? this.toString : super.toString());
		}
    }

	/**
	 * Default constructor.
	 */
	public RequiredStyle() {
		setRendererType(DEFAULT_RENDERER);
	}
	
	/**
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	@Override
	public String getFamily() {
		return IsiComponentCoreUtils.COMPONENT_FAMILY;
	}
	
	/**
	 * Get the style class to apply to required components.
	 * 
	 * @return
	 */
	public String getStyleClass() {
		return (String) getStateHelper().eval(PropertyKeys.styleClass, null);
	}
	
	/**
	 * Set the style class to apply to required components.
	 * 
	 * @param styleClass
	 */
	public void setStyleClass(java.lang.String styleClass) {
		getStateHelper().put(PropertyKeys.styleClass, styleClass);
	}

	

}