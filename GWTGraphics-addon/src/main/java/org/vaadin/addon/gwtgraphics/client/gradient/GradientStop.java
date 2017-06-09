package org.vaadin.addon.gwtgraphics.client.gradient;

import org.vaadin.addon.gwtgraphics.client.impl.util.SVGUtil;

import com.google.gwt.dom.client.Element;

public class GradientStop{

	protected double offset = 0;
	protected double opacity = 1.0;
	protected String color = "";
	protected String className = "";

	public GradientStop() {
		this(0, 1.0, "white");
	}

	public GradientStop(double offsetPct, double opacity, String color) {
		offset = offsetPct;
		this.opacity = opacity;
		this.color = color;
	}

	public void setOffset(double pct) {
		offset = Math.min(100, Math.max(0, pct));
	}

	public double getOffset() {
		return offset;
	}

	public void setColor(String color) {
		if(color == null) {
			color = "";
		}
		this.color = color;
	}

	public void setOpacity(double opacity) {
		this.opacity = offset = Math.min(1, Math.max(0, opacity));
	}

	public double getOpacity() {
		return opacity;
	}

	public void setClassName(String cls) {
		if(cls == null) {
			cls = "";
		}
		className = cls;
	}

	public String getClassName() {
		return className;
	}

	public String getColor() {
		return color;
	}


	public Element getElement() {
		Element se = SVGUtil.createSVGElementNS("stop");
		se.setAttribute("offset", "" + getOffset());
		se.setAttribute("stop-color", getColor());
		se.setAttribute("stop-opacity", "" + getOpacity());
		return se;
	}

}