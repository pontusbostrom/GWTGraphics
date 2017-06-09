package org.vaadin.addon.gwtgraphics.client.fill;

public class Fill {

	// TODO: add fill-rule (see https://developer.mozilla.org/en-US/docs/Web/SVG/Attribute/fill-rule)

	protected String style;
	protected double opacity = 1.0;

	public Fill() {
		style = "white";
	}

	public Fill(String style) {
		this.style = style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getStyle() {
		return style;
	}

	public void setOpacity(double opacity) {
		this.opacity = Math.max(0, Math.min(opacity, 1));
	}

	public double getOpacity() {
		return opacity;
	}

	public String toSVGString() {
		String s = "fill=\"" + style + "\"";
		if(opacity != 1.0) {
			s += " fill-opacity=\"" + opacity + "\"";
		}
		return s;
	}

}
