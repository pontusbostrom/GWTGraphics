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
package org.vaadin.addon.gwtgraphics.client.shape;

import org.vaadin.addon.gwtgraphics.client.Shape;
import org.vaadin.addon.gwtgraphics.client.VectorObject;

/**
 * Rectangle represents a rectangle.
 *
 * @author Henri Kerola
 *
 */
public class Rectangle extends Shape {

	/**
	 * Creates a new Rectangle with the given position and size properties.
	 *
	 * @param x
	 *            the x-coordinate position of the top-left corner of the
	 *            rectangle in pixels
	 * @param y
	 *            the y-coordinate position of the top-left corner of the
	 *            rectangle in pixels
	 * @param width
	 *            the width of the Rectangle in pixels
	 * @param height
	 *            the height of the Rectangle in pixels
	 */
	public Rectangle(int x, int y, int width, int height) {
		setPosition(x, y);
		setSize(width, height);
	}

	@Override
	protected Class<? extends VectorObject> getType() {
		return Rectangle.class;
	}

	/**
	 * Gets the radius of rounded corners in pixels.
	 *
	 * @return radius of rounded corners in pixels
	 */
	public int getRoundedCorners() {
		return getImpl().getRectangleRoundedCorners(getElement());
	}

	/**
	 * Sets the radius of rounded corners in pixels. Value 0 disables rounded
	 * corners.
	 *
	 * @param radius
	 *            radius of rounded corners in pixels
	 */
	public void setRoundedCorners(int radius) {
		if (radius < 0) {
			radius = 0;
		}
		getImpl().setRectangleRoundedCorners(getElement(), radius);
	}

	@Override
	public String getSVGElementName() {
		return "rect";
	}

	@Override
	public void setPropertyDouble(String property, double value) {
		if("roundedcorners".equals(property)) {
			setRoundedCorners((int)value);
		} else{
			super.setPropertyDouble(property, value);
		}
	}
}