package org.vaadin.addon.gwtgraphics.client.gradient;

import org.vaadin.addon.gwtgraphics.client.impl.util.SVGUtil;

import com.google.gwt.dom.client.Element;

public class LinearGradient extends Gradient {

	protected double x0, y0, x1, y1;

	public LinearGradient() {
		this(0,0,1,1);
	}

	public LinearGradient(double x0, double y0, double x1, double y1) {
		setStartPosition(x0, y0);
		setEndPosition(x1, y1);
	}

	public void setStartPosition(double x, double y) {
		x0 = x;
		y0 = y;
		setParameter("x0", "" + x);
		setParameter("y0", "" + y);
	}

	public double getStartX() {
		return x0;
	}

	public double getStartY() {
		return y0;
	}

	public void setEndPosition(double x, double y) {
		x1 = x;
		y1 = y;
		setParameter("x1", "" + x);
		setParameter("y1", "" + y);
	}

	public double getEndX() {
		return x1;
	}

	public double getEndY() {
		return y1;
	}

	@Override
	public Element getElement() {

		Element element = SVGUtil.createSVGElementNS("linearGradient");
		element.setAttribute("x1", ""+x0);
		element.setAttribute("y1", ""+y0);
		element.setAttribute("x2", ""+x1);
		element.setAttribute("y2", ""+y1);
		element.setAttribute("id", getId());
		for (GradientStop stop: getStops()) {
			element.appendChild(stop.getElement());
		}
		return element;
	}

}
