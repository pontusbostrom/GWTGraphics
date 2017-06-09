package org.vaadin.addon.gwtgraphics.client.stroke;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Stroke {

	public static enum LineCap {
		BUTT,
		SQUARE,
		ROUND
	}

	public static enum LineJoin {
		MITER,
		ROUND,
		BEVEL
	}

	protected List<Double> dashArray = null;
	protected LineCap lineCap = null;
	protected LineJoin lineJoin = null;
	protected String color;
	protected double width = 1.0;
	protected double miterLimit = 4.0;
	protected double dashOffset = 0.0;
	protected double opacity = 1.0;

	public Stroke() {
		this("black");
	}

	public Stroke(String color) {
		this.color = color == null ? "" : color;
	}

	public void setDashArray(List<Double> values) {
		if(dashArray == null) {
			dashArray = new ArrayList<Double>();
		}
		dashArray.clear();
		dashArray.addAll(values);
	}

	public void setDashArray(Double... values) {
		setDashArray(Arrays.asList(values));

	}

	/**
	 * Returns a temporary list with the defined dash array values
	 *
	 * @return
	 */
	public List<Double> getDashArray() {
		if(dashArray == null) {
			return new ArrayList<Double>();
		}
		return new ArrayList<Double>(dashArray);
	}

	public void setDashOffset(double offs) {
		dashOffset = offs;
	}

	public double getDashOffset() {
		return dashOffset;
	}

	public void setColor(String color) {
		this.color = color == null ? "none" : color;
	}

	public String getColor() {
		return color;
	}

	public void setMiterLimit(double limit) {
		miterLimit = limit;
	}

	public double getMiterLimit() {
		return miterLimit;
	}

	public void setLineCap(LineCap c) {
		lineCap = c;
	}

	public LineCap getLineCap() {
		return lineCap;
	}

	public void setLineJoin(LineJoin j) {
		lineJoin = j;
	}

	public LineJoin getLineJoin() {
		return lineJoin;
	}

	public void setLineWidth(double w) {
		width = w;
	}

	public double getLineWidth() {
		return width;
	}

	public void setOpacity(double o) {
		opacity = Math.max(1, Math.min(0, o));
	}

	public double getOpacity() {
		return opacity;
	}

	/**
	 * Returns properties formatted for inclusion in an SVG tag
	 *
	 * @return a string representing a number of SVG properties
	 */
	public String toSVGString() {
		String s = "stroke=\"" + color + "\"";

		if(width != 1.0) {
			s += " stroke-width=\"" + width  + "\"";
		}

		if(miterLimit != 4.0) {
			s += " stroke-miterlimit=\"" + miterLimit + "\"";
		}

		if(dashArray != null) {
			s += " stroke-dasharray=\"";
			Iterator<Double> it = dashArray.iterator();
			while(it.hasNext()) {
				double d = it.next();
				s += "" + d;
				if(it.hasNext()) {
					s += ",";
				}
			}
			s += "\"";
		}

		if(dashOffset != 0.0) {
			s += " stroke-dashoffset=\"" + dashOffset + "\"";
		}

		if(opacity != 1.0) {
			s += " stroke-opacity=\"" + opacity + "\"";
		}

		return s;
	}

}
