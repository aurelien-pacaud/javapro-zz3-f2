package org.isifaces.component.tooltips;

import javax.faces.component.UIOutput;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;

import org.isifaces.component.IsiComponentCoreUtils;

/**
 * On indique que notre widget à besoin de primefaces.css, de jquery et de primefaces.js.
 * L'inclusion de ces fichiers sera faite automatiquement.
 */
@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="primefaces.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="primefaces.js")
})
public class Tooltips extends UIOutput implements org.primefaces.component.api.Widget {

	private static final String DEFAULT_RENDERER = "org.isifaces.component.TooltipsRenderer";

	protected enum PropertyKeys {

		styleClass;

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
	 * Permet de fixer le renderer de notre classe tooltips afin d'appeler TooltipsRenderer.
	 */
	public Tooltips() {
		setRendererType(DEFAULT_RENDERER);
	}

	/**
	 * Fonction fournissant la famille à laquelle appartient ce composant JSF.
	 */
	public String getFamily() {
		return IsiComponentCoreUtils.COMPONENT_FAMILY;
	}

	@Override
	public String resolveWidgetVar() {
		return null;
	}
}