package org.isifaces.component.tooltips;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.renderkit.CoreRenderer;

public class TooltipsRenderer extends CoreRenderer {

	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		Tooltips tooltip = (Tooltips) component;
		
        encodeMarkup(context, tooltip);
		//encodeScript(context, tooltip);
	}
    
    protected void encodeMarkup(FacesContext context, Tooltips tooltip) throws IOException {
        
    	ResponseWriter writer = context.getResponseWriter();
    	
    	writer.startElement("script",  null);
    		writer.writeAttribute("type", "text/javascript", null);
			writer.write("$(function() { $('html [title]').tipsy({fade: true, gravity: 'w', opacity: 1.0});});");
		writer.endElement("script");
    	
    	for (UIComponent c : tooltip.getParent().getChildren()) {
    		
    		render(context, c);
    	}
    }
    
    private void render(FacesContext context, UIComponent c) throws IOException {  
    	
    	if (c.getChildCount() > 0)
    		for (UIComponent child : c.getChildren())
    			render(context, child);
    	else {
    			
			Object obj = c.getAttributes().get("title");
    		
    		if (obj == null) {
    			
    			obj = c.getAttributes().get("value");
    			
    			if (obj != null) {
    				
    				c.getAttributes().put("title", obj);
    			}
    		}     
    	}
    }

	protected void encodeScript(FacesContext context, Tooltips tooltip) throws IOException {
		
		ResponseWriter writer = context.getResponseWriter();        
		endScript(writer);
	}

    @Override
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		//Rendering happens on encodeEnd
	}

    @Override
	public boolean getRendersChildren() {
		return true;
	}
}