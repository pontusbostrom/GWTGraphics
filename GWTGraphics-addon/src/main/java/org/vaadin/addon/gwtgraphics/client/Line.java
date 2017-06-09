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
package org.vaadin.addon.gwtgraphics.client;

import org.vaadin.addon.gwtgraphics.client.animation.Animatable;

/**
 * Line represents a straight line from one point to another. Line can be
 * stroked.
 *
 * @author Henri Kerola
 *
 */
public class Line extends VectorObject implements Animatable {

	protected double x0, y0, x1, y1;

	/**
	 * Creates a new instance of Line. This is a line from one given point to
	 * another. Default stroke width is 1px and stroke color is black.
	 *
	 * @param x1
	 *            the x-coordinate of the starting point in pixels
	 * @param y1
	 *            the y-coordinate of the starting point in pixels
	 * @param x2
	 *            the x-coordinate of the end point in pixels
	 * @param y2
	 *            the y-coordinate of the end point in pixels
	 */
	public Line(int x1, int y1, int x2, int y2) {
		setStartPosition(x1,y1);
		setEndPosition(x2,y2);
	}

	public void setStartPosition(double x, double y) {
		x0 = x;
		y0 = y;
		setProperty("x1", x);
		setProperty("y1", y);
		redraw();
	}

	public double getX0() {
		return x0;
	}

	public double getY0() {
		return y0;
	}

	public void setEndPosition(double x, double y) {
		x1 = x;
		y1 = y;
		setProperty("x2", x);
		setProperty("y2", y);
		redraw();
	}

	public double getX1() {
		return x1;
	}

	public double getY1() {
		return y1;
	}

	@Override
	protected Class<? extends VectorObject> getType() {
		return Line.class;
	}

	@Override
	public String getSVGElementName() {
		return "line";
	}

}
