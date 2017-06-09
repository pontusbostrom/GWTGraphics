/*
 * Copyright 2011 Henri Kerola
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vaadin.addon.gwtgraphics.client.impl;

import java.util.List;

import org.vaadin.addon.gwtgraphics.client.Group;
import org.vaadin.addon.gwtgraphics.client.Image;
import org.vaadin.addon.gwtgraphics.client.Line;
import org.vaadin.addon.gwtgraphics.client.VectorObject;
import org.vaadin.addon.gwtgraphics.client.impl.util.NumberUtil;
import org.vaadin.addon.gwtgraphics.client.impl.util.SVGBBox;
import org.vaadin.addon.gwtgraphics.client.impl.util.SVGUtil;
import org.vaadin.addon.gwtgraphics.client.shape.Circle;
import org.vaadin.addon.gwtgraphics.client.shape.Ellipse;
import org.vaadin.addon.gwtgraphics.client.shape.Path;
import org.vaadin.addon.gwtgraphics.client.shape.Rectangle;
import org.vaadin.addon.gwtgraphics.client.shape.Text;
import org.vaadin.addon.gwtgraphics.client.shape.path.Arc;
import org.vaadin.addon.gwtgraphics.client.shape.path.ClosePath;
import org.vaadin.addon.gwtgraphics.client.shape.path.CurveTo;
import org.vaadin.addon.gwtgraphics.client.shape.path.LineTo;
import org.vaadin.addon.gwtgraphics.client.shape.path.MoveTo;
import org.vaadin.addon.gwtgraphics.client.shape.path.PathStep;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * This class contains the SVG implementation module of GWT Graphics.
 *
 * @author Henri Kerola
 *
 */
public class SVGImpl {

	// TODO: get rid of all this crap

	public String getRendererString() {
		return "SVG";
	}

	public String getStyleSuffix() {
		return "svg";
	}

	public Element createDrawingArea(Element container, int width, int height) {
		Element root = SVGUtil.createSVGElementNS("svg");
		// IE9 needs this to crop overflowing content
		root.setAttribute("overflow", "hidden");
		container.appendChild(root);
		setWidth(root, width);
		setHeight(root, height);

		Element defs = SVGUtil.createSVGElementNS("defs");
		root.appendChild(defs);

		return root;
	}

	public Element createElement(Class<? extends VectorObject> type) {
		Element element = null;
		if (type == Rectangle.class) {
			element = SVGUtil.createSVGElementNS("rect");
		} else if (type == Circle.class) {
			element = SVGUtil.createSVGElementNS("circle");
		} else if (type == Ellipse.class) {
			element = SVGUtil.createSVGElementNS("ellipse");
		} else if (type == Path.class) {
			element = SVGUtil.createSVGElementNS("path");
		} else if (type == Text.class) {
			element = SVGUtil.createSVGElementNS("text");
			element.setAttribute("text-anchor", "start");
		} else if (type == Image.class) {
			element = SVGUtil.createSVGElementNS("image");
			// Let aspect ration behave like VML's image does
			element.setAttribute("preserveAspectRatio", "none");
		} else if (type == Line.class) {
			element = SVGUtil.createSVGElementNS("line");
		} else if (type == Group.class) {
			element = SVGUtil.createSVGElementNS("g");
		}

		return element;
	}

	public int getX(Element element) {
		if ( element.getTagName().toLowerCase().equals("g") ){
			MatchResult r = getTranslation(element);
			return r != null && r.getGroupCount() == 4 ? NumberUtil.parseIntValue(r.getGroup(1), 0) : 0;
		}else{
			return NumberUtil.parseIntValue(element,
					getPosAttribute(element, true), 0);
		}
	}

	public void setX(final Element element, final int x, final boolean attached) {
		setXY(element, true, x, attached);
	}

	public int getY(Element element) {
		if ( element.getTagName().toLowerCase().equals("g") ){
			MatchResult r = getTranslation(element);
			return r != null && r.getGroupCount() == 4 ? NumberUtil.parseIntValue(r.getGroup(3), 0) : 0;
		}else{
			return NumberUtil.parseIntValue(element,
					getPosAttribute(element, false), 0);
		}

	}

	public void setY(final Element element, final int y, final boolean attached) {
		setXY(element, false, y, attached);
	}

	protected void setXY(final Element element, boolean x, final int value,
			final boolean attached) {
		if ( element.getTagName().toLowerCase().equals("g")){
			int other = x ? getY(element) : getX(element);
			StringBuilder sb = new StringBuilder("translate(");
			if ( x ){
				sb.append(value).append(",").append(other);
			}else{
				sb.append(other).append(",").append(value);
			}
			sb.append(")");
			RegExp p = RegExp.compile("translate\\(.+\\)");
			String xform = element.getAttribute("transform");
			if ( xform != null ){
				sb.append(" ");
				MatchResult r = p.exec(xform);
				if ( r.getGroupCount() > 0 ){
					p.replace(xform, sb.toString());
				}else{
					xform = sb.append(xform).toString();
				}

			}
			element.setAttribute("transform", sb.toString());
		}else{
			final int rotation = getRotation(element);
			final String posAttr = getPosAttribute(element, x);
			SVGUtil.setAttributeNS(element, posAttr, value);
			if (rotation != 0) {
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					public void execute() {
						SVGUtil.setAttributeNS(element, "transform", "");
						SVGUtil.setAttributeNS(element, posAttr, value);
						setRotateTransform(element, rotation, attached);
					}
				});
			}
		}
	}

	protected String getPosAttribute(Element element, boolean x) {
		String tagName = element.getTagName();
		String attr = "";
		if (tagName.equals("rect") || tagName.equals("text")
				|| tagName.equals("image")) {
			attr = x ? "x" : "y";
		} else if (tagName.equals("circle") || tagName.equals("ellipse")) {
			attr = x ? "cx" : "cy";
		} else if (tagName.equals("path")) {

		} else if (tagName.equals("line")) {
			attr = x ? "x1" : "y1";
		}
		return attr;
	}

	protected MatchResult getTranslation(Element e){
		String xform = e.getAttribute("transform");
		return xform != null ? RegExp.compile("translate\\(\\s*(\\d+)\\s*(,\\s*(\\d+))?\\s*\\)","i").exec(xform) : null;
	}

	public String getFillColor(Element element) {
		String fill = element.getAttribute("fill");
		return fill.equals("none") ? null : fill;
	}

	public void setFillColor(Element element, String color) {
		if (color == null) {
			color = "none";
		}
		SVGUtil.setAttributeNS(element, "fill", color);
	}

	public double getFillOpacity(Element element) {
		return NumberUtil.parseDoubleValue(
				element.getAttribute("fill-opacity"), 1);
	}

	public void setFillOpacity(Element element, double opacity) {
		SVGUtil.setAttributeNS(element, "fill-opacity", "" + opacity);
	}

	public String getStrokeColor(Element element) {
		String stroke = element.getAttribute("stroke");
		return stroke.equals("none") ? null : stroke;
	}

	public void setStrokeColor(Element element, String color) {
		SVGUtil.setAttributeNS(element, "stroke", color);
	}

	public int getStrokeWidth(Element element) {
		return NumberUtil.parseIntValue(element, "stroke-width", 0);
	}

	public void setStrokeWidth(Element element, int width, boolean attached) {
		SVGUtil.setAttributeNS(element, "stroke-width", width);
	}

	public double getStrokeOpacity(Element element) {
		return NumberUtil.parseDoubleValue(
				element.getAttribute("stroke-opacity"), 1);
	}

	public void setStrokeOpacity(Element element, double opacity) {
		SVGUtil.setAttributeNS(element, "stroke-opacity", "" + opacity);
	}

	public int getWidth(Element element) {
		return NumberUtil.parseIntValue(element, "width", 0);
	}

	public void setWidth(Element element, int width) {
		SVGUtil.setAttributeNS(element, "width", width);
		if (element.getTagName().equalsIgnoreCase("svg")) {
			element.getParentElement().getStyle().setPropertyPx("width", width);
		}
	}

	public int getHeight(Element element) {
		return NumberUtil.parseIntValue(element, "height", 0);
	}

	public void setHeight(Element element, int height) {
		SVGUtil.setAttributeNS(element, "height", height);
		if (element.getTagName().equalsIgnoreCase("svg")) {
			element.getParentElement().getStyle()
			.setPropertyPx("height", height);
		}
	}

	public int getCircleRadius(Element element) {
		return NumberUtil.parseIntValue(element, "r", 0);
	}

	public void setCircleRadius(Element element, int radius) {
		SVGUtil.setAttributeNS(element, "r", radius);
	}

	public int getEllipseRadiusX(Element element) {
		return NumberUtil.parseIntValue(element, "rx", 0);
	}

	public void setEllipseRadiusX(Element element, int radiusX) {
		SVGUtil.setAttributeNS(element, "rx", radiusX);
	}

	public int getEllipseRadiusY(Element element) {
		return NumberUtil.parseIntValue(element, "ry", 0);
	}

	public void setEllipseRadiusY(Element element, int radiusY) {
		SVGUtil.setAttributeNS(element, "ry", radiusY);
	}

	public void drawPath(Element element, List<PathStep> steps) {
		StringBuilder path = new StringBuilder();
		for (PathStep step : steps) {
			appendPathStep(path, step);
		}
		SVGUtil.setAttributeNS(element, "d", path.toString());
	}

	/**
	 * Builds the string for single step Needed when appending step to the end
	 * of path
	 * @param element
	 *
	 * @param step
	 *            step which string we need
	 * @return path step string
	 */
	public void getPathStepString(Element element, PathStep step) {
		StringBuilder path = new StringBuilder();
		appendPathStep(path, step);
		element.getAttribute("d").concat(path.toString());
	}

	protected void appendPathStep(StringBuilder path, PathStep step) {
		if (step instanceof Arc) {
			Arc arc = (Arc) step;
			path.append(arc.isRelativeCoords() ? " a" : " A");
			path.append(arc.getRx()).append(",").append(arc.getRy());
			path.append(" ").append(arc.getxAxisRotation());
			path.append(" ").append(arc.isLargeArc() ? "1" : "0").append(",")
			.append(arc.isSweep() ? "1" : "0");
			path.append(" ").append(arc.getX()).append(",").append(arc.getY());
		} else if (step instanceof CurveTo) {
			CurveTo curve = (CurveTo) step;
			path.append(curve.isRelativeCoords() ? " c" : " C");
			path.append(curve.getX1()).append(" ").append(curve.getY1());
			path.append(" ").append(curve.getX2()).append(" ")
			.append(curve.getY2());
			path.append(" ").append(curve.getX()).append(" ")
			.append(curve.getY());
		} else if (step instanceof LineTo) {
			LineTo lineTo = (LineTo) step;
			path.append(lineTo.isRelativeCoords() ? " l" : " L")
			.append(lineTo.getX()).append(" ").append(lineTo.getY());
		} else if (step instanceof MoveTo) {
			MoveTo moveTo = (MoveTo) step;
			path.append(moveTo.isRelativeCoords() ? " m" : " M")
			.append(moveTo.getX()).append(" ").append(moveTo.getY());
		}else if (step instanceof ClosePath) {
			path.append(" z");
		}
	}

	public String getText(Element element) {
		return element.getInnerText();
	}

	public void setText(Element element, String text, boolean attached) {
		element.setInnerText(text);
	}

	public String getTextFontFamily(Element element) {
		return element.getAttribute("font-family");
	}

	public void setTextFontFamily(Element element, String family,
			boolean attached) {
		SVGUtil.setAttributeNS(element, "font-family", family);
	}

	public int getTextFontSize(Element element) {
		return NumberUtil.parseIntValue(element, "font-size", 0);
	}

	public void setTextFontSize(Element element, int size, boolean attached) {
		SVGUtil.setAttributeNS(element, "font-size", size);
	}

	public String getImageHref(Element element) {
		return element.getAttribute("href");
	}

	public void setImageHref(Element element, String src) {
		SVGUtil.setAttributeNS(SVGUtil.XLINK_NS, element, "href", src);
	}

	public int getRectangleRoundedCorners(Element element) {
		return NumberUtil.parseIntValue(element, "rx", 0);
	}

	public void setRectangleRoundedCorners(Element element, int radius) {
		SVGUtil.setAttributeNS(element, "rx", radius);
		SVGUtil.setAttributeNS(element, "ry", radius);
	}

	public int getLineX2(Element element) {
		return NumberUtil.parseIntValue(element, "x2", 0);
	}

	public void setLineX2(Element element, int x2) {
		SVGUtil.setAttributeNS(element, "x2", x2);

	}

	public int getLineY2(Element element) {
		return NumberUtil.parseIntValue(element, "y2", 0);
	}

	public void setLineY2(Element element, int y2) {
		SVGUtil.setAttributeNS(element, "y2", y2);

	}

	public void add(Element root, Element element, boolean attached) {
		root.appendChild(element);
	}

	public void add(Element root, Element element) {
		NodeList<Node> list = root.getChildNodes();
		for(int i = 0; i<list.getLength(); i++ ) {
			Node node = list.getItem(i);
			if("defs".equals(node.getNodeName())) {
				node.appendChild(element);
				return;
			}
		}
	}

	public void insert(Element root, Element element, int beforeIndex,
			boolean attached) {
		if ("defs".equals(root.getChildNodes().getItem(0).getNodeName())) {
			beforeIndex++;
		}
		Element e = root.getChildNodes().getItem(beforeIndex).cast();
		root.insertBefore(element, e);
	}

	public void remove(Element root, Element element) {
		root.removeChild(element);
	}

	public void bringToFront(Element root, Element element) {
		root.appendChild(element);
	}

	public void clear(Element root) {
		while (root.hasChildNodes()) {
			root.removeChild(root.getLastChild());
		}
	}

	public void setStyleName(Element element, String name) {
		SVGUtil.setClassName(element, name + "-" + getStyleSuffix());
	}

	public void setRotation(final Element element, final int degree,
			final boolean attached) {
		element.setPropertyInt("_rotation", degree);
		if (degree == 0) {
			SVGUtil.setAttributeNS(element, "transform", "");
			return;
		}
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			public void execute() {
				setRotateTransform(element, degree, attached);
			}
		});
	}

	protected void setRotateTransform(Element element, int degree,
			boolean attached) {
		SVGBBox box = SVGUtil.getBBBox(element, attached);
		int x = box.getX() + box.getWidth() / 2;
		int y = box.getY() + box.getHeight() / 2;
		SVGUtil.setAttributeNS(element, "transform", "rotate(" + degree + " "
				+ x + " " + y + ")");
	}

	public int getRotation(Element element) {
		return element.getPropertyInt("_rotation");
	}

	public void onAttach(Element element, boolean attached) {
	}

	// Note that VMLImpl uses this same method impl.
	public int getTextWidth(Element element) {
		return measureTextSize(element, true);
	}

	// Note that VMLImpl uses this same method impl.
	public int getTextHeight(Element element) {
		return measureTextSize(element, false);
	}

	protected int measureTextSize(Element element, boolean measureWidth) {
		String text = getText(element);
		if (text == null || "".equals(text)) {
			return 0;
		}

		DivElement measureElement = Document.get().createDivElement();
		Style style = measureElement.getStyle();
		style.setProperty("visibility", "hidden");
		style.setProperty("display", "inline");
		style.setProperty("whiteSpace", "nowrap");
		style.setProperty("fontFamily", getTextFontFamily(element));
		style.setPropertyPx("fontSize", getTextFontSize(element));
		measureElement.setInnerText(text);
		RootPanel.getBodyElement().appendChild(measureElement);
		int measurement;
		if (measureWidth) {
			measurement = measureElement.getOffsetWidth();
		} else {
			measurement = measureElement.getOffsetHeight();
		}
		RootPanel.getBodyElement().removeChild(measureElement);

		return measurement;
	}
}
