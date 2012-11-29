/*
 * $Id: RequiredStyleRenderer.java 80 2012-11-20 21:34:38Z pj.chartre@gmail.com $
 * 
 * Copyright to Michelin Corporation.
 */
package org.isifaces.component.requiredstyle;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.isifaces.utils.ValueExpressionAnalyzer;
import org.isifaces.utils.ValueExpressionAnalyzer.ValueReference;
import org.primefaces.renderkit.CoreRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Component which add the required style class to all components inside the same parent container 
 * which have a required value binding.
 */
public class RequiredStyleRenderer extends CoreRenderer {
	
	/** Logger of the class */
	private static final Logger LOGGER = LoggerFactory.getLogger(RequiredStyleRenderer.class);
	
	
	/**
	 * @see javax.faces.render.Renderer#encodeBegin(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	@Override
	public void encodeBegin(FacesContext context, UIComponent component)
			throws IOException {
		super.encodeBegin(context, component);
		
		RequiredStyle requiredStyle = (RequiredStyle) component;
		String styleClass = requiredStyle.getStyleClass();
		
		updateStyle(requiredStyle, context, component.getParent(), styleClass);
	}
	
	/**
	 * @see javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	@Override
	public void encodeEnd(FacesContext context, UIComponent component)
			throws IOException {
		super.encodeEnd(context, component);
	}
	
	/**
	 * @see javax.faces.render.Renderer#encodeChildren(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	@Override
	public void encodeChildren(FacesContext context, UIComponent component)
			throws IOException {
		super.encodeChildren(context, component);
	}
	
	/**
	 * Add the required style class to all components having a required value binding.
	 * 
	 * @param requiredStyle
	 * @param context
	 * @param root
	 * @param styleClass
	 */
	protected void updateStyle(RequiredStyle requiredStyle, FacesContext context, UIComponent root, String styleClass) {		
		for (UIComponent child : root.getChildren()) {
			try {
				if( isRequired(child, context) ){
					LOGGER.debug("Required component: {}", child.getClientId());
					setRequiredStyle(styleClass, child);
				}
			} 
			catch (Exception e) {
				LOGGER.error("Error in updateStyle", e);
			}
			updateStyle( requiredStyle, context, child, styleClass );
		}
	}

	/**
	 * Test if the component is required or not.
	 * 
	 * @param component
	 * @param context
	 * @return
	 */
	protected boolean isRequired(UIComponent component, FacesContext context) {
		//simple output is never required
		if( component instanceof HtmlOutputText || component instanceof HtmlOutputLabel ){
			return false;
		}
		//required input
		else if( component instanceof UIInput && ((UIInput) component).isRequired() ){
			return true;
		}
		//required validator
		else if( containsRequiredValidator(component, context) ){
			return true;
		}
		else{
			//test required on component
			//test required on value binding
			ValueExpression valueExpression = component.getValueExpression("value");
			if( valueExpression != null ){
				ValueExpressionAnalyzer expressionAnalyzer = new ValueExpressionAnalyzer(valueExpression);
				ValueReference valueReference = expressionAnalyzer.getReference(context.getELContext());
				if (valueReference != null) {
					final Object viewBean = valueReference.getBase();
					final String property = valueReference.getProperty();
					
					//change style
					if( isRequired(viewBean, property) ){
						return true;
					}
				}
			}	
		}
		return false;
	}

	/**
	 * Test if the component has a required validator
	 * 
	 * @param component
	 * @param context
	 * @return
	 */
	protected boolean containsRequiredValidator(UIComponent component, FacesContext context) {
		//TODO
//		if( component.getChildren() != null ){
//			for(UIComponent child : component.getChildren()){
//				if( child instanceof RequiredValidator ){
//					return true ;
//				}
//			}
//		}
		return false;
	}

	/**
	 * Set the required style to the component.
	 * 
	 * @param styleClass
	 * @param child
	 */
	protected void setRequiredStyle(String styleClass, UIComponent child){
		try {
			String existingStyle = (String) PropertyUtils.getProperty( child, "styleClass" );
			PropertyUtils.setProperty( child, "styleClass",
					existingStyle != null ? existingStyle+" "+styleClass : styleClass );
		} 
		catch (Exception e) {
			LOGGER.error("Error in setRequiredStyle", e);
		}
	}

	/**
	 * Test if the property of a bean is required or not.
	 * 
	 * @param bean
	 * @param property
	 * @return
	 */
	protected boolean isRequired(final Object bean, final String property) {
		try {
			//search required annotations
			PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptor(bean, property);
			if( propertyDescriptor != null ){
				Method getter = propertyDescriptor.getReadMethod();
				if( getter != null ){
					return getter.isAnnotationPresent(NotNull.class) ||
							getter.isAnnotationPresent(NotEmpty.class) ||
							getter.isAnnotationPresent(NotBlank.class) ||
							 (getter.isAnnotationPresent(Size.class)&&getter.getAnnotation(Size.class).min()>0);
				}
			} 
		}
		catch (Exception e) {
			//FIXME
			e.printStackTrace();
		}
		return false;		
	}
}
