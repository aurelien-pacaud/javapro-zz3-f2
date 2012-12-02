package org.isifaces.component.tooltips;

import javax.faces.component.UIOutput;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;

import org.isifaces.component.IsiComponentCoreUtils;

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

	public Tooltips() {
		setRendererType(DEFAULT_RENDERER);
	}

	public String getFamily() {
		return IsiComponentCoreUtils.COMPONENT_FAMILY;
	}

	@Override
	public String resolveWidgetVar() {
		return null;
	}
}