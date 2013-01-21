package org.isifaces.component.tooltips;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.primefaces.component.tooltip.Tooltip;
import org.primefaces.renderkit.CoreRenderer;

public class TooltipsRenderer extends CoreRenderer {

	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		Tooltips tooltip = (Tooltips) component;
		
		/* Récupération de tous les enfants du body afin de les afficher. */
		for (UIComponent c : tooltip.getParent().getChildren())
			render(context, c);
	}
    
    private void render(FacesContext context, UIComponent c) throws IOException {  
    	
    	/* Si le composant à des enfants, on fait un appel récursif pour les traiter. */
    	if (c.getChildCount() > 0)
    		for (UIComponent child : c.getChildren())
    			render(context, child);
    	else {
    		
    		/* Création d'un tooltip primefaces. */
    		UIComponent tooltip = new Tooltip();
    		
    		if (c.getAttributes().get("title") != null) {
    		
    			/* Récupération de la map d'attributs du composant tooltip. */
    			Map<String, Object> attrs = tooltip.getAttributes();
    			
	    		/*<p:tooltip for="fade" value="Title display" showEffect="fade" hideEffect="fade" /> */
    			attrs.put("for", c.getClientId(context));
    			attrs.put("value", c.getAttributes().get("title"));
    			attrs.put("showEffect", "fade");
    			attrs.put("hideEffect", "fade");
	    		
	    		/* On ajout le tooltip au div contenant les tooltips. */
	    		c.getParent().getChildren().add(tooltip);
    		}
    	}
    }
    
    @Override
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException { }

    @Override
	public boolean getRendersChildren() {
		return true;
	}
}