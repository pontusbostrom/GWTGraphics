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
package org.vaadin.addon.gwtgraphics.client.shape.path;

/**
 * This class represents an arc step.
 *
 * @author Henri Kerola
 *
 */
public class Arc extends LineTo {

	protected double rx;

	protected double ry;

	protected double xAxisRotation;

	protected boolean largeArc;

	protected boolean sweep;

	public Arc(boolean relativeCoords, double rx, double ry, double xAxisRotation,
			boolean largeArc, boolean sweep, double x, double y) {
		super(relativeCoords, x, y);
		this.rx = rx;
		this.ry = ry;
		this.xAxisRotation = xAxisRotation;
		this.largeArc = largeArc;
		this.sweep = sweep;

	}

	public double getRx() {
		return rx;
	}

	public double getRy() {
		return ry;
	}

	public double getxAxisRotation() {
		return xAxisRotation;
	}

	public boolean isLargeArc() {
		return largeArc;
	}

	public boolean isSweep() {
		return sweep;
	}

	public void setRx(int rx) {
		this.rx = rx;
	}

	public void setRy(int ry) {
		this.ry = ry;
	}

	public void setxAxisRotation(int xAxisRotation) {
		this.xAxisRotation = xAxisRotation;
	}

	public void setLargeArc(boolean largeArc) {
		this.largeArc = largeArc;
	}

	public void setSweep(boolean sweep) {
		this.sweep = sweep;
	}

	@Override
	public String toSVGString() {
		return isRelativeCoords() ? "a" : "A" + getRx() + "," + getRy() + " "
				+ getxAxisRotation() + " " + (isLargeArc() ? "1" : "0") + ","
				+ (isSweep() ? "1" : "0") + " " + getX() + "," + getY();
	}

	@Override
	public Arc cloneStep() {
		return new Arc(relativeCoords, rx, ry, xAxisRotation, largeArc, sweep, x, y);
	}
}
