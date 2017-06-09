package org.vaadin.addon.gwtgraphics.client.gradient;

import org.vaadin.addon.gwtgraphics.client.impl.util.SVGUtil;

import com.google.gwt.dom.client.Element;

public class RadialGradient extends Gradient {

	protected double cx, cy, fx, fy, radius;

	public RadialGradient() {
	}

	public RadialGradient(double cx, double cy,  double fx, double fy, double radius) {
		this.cx = cx;
		this.cy = cy;
		this.fx = fx;
		this.fy = fy;
		setRadius(radius);
	}



	public void setRadius(double r) {
		radius = r;
	}

	public double getRadius() {
		return radius;
	}

	public double getCx() {
		return cx;
	}

	public void setCx(double cx) {
		this.cx = cx;
	}

	public double getCy() {
		return cy;
	}

	public void setCy(double cy) {
		this.cy = cy;
	}

	public double getFx() {
		return fx;
	}

	public void setFx(double fx) {
		this.fx = fx;
	}

	public double getFy() {
		return fy;
	}

	public void setFy(double fy) {
		this.fy = fy;
	}

	@Override
	public Element getElement() {
		Element element = SVGUtil.createSVGElementNS("radialGradient");
		element.setAttribute("radius", ""+radius);
		element.setAttribute("cx", ""+cx);
		element.setAttribute("cy", ""+cy);
		element.setAttribute("fx", ""+fx);
		element.setAttribute("fy", ""+fy);
		element.setAttribute("id", getId());
		for (GradientStop stop: getStops()) {
			element.appendChild(stop.getElement());
		}
		return element;
	}

}
