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

import org.vaadin.addon.gwtgraphics.client.filter.Filter;

import com.google.gwt.dom.client.Element;

/**
 * Shape is an abstract upper-class for VectorObjects that support filling,
 * stroking and positioning.
 *
 * TODO: JavaDoc no longer reflects reality
 *
 * @author Henri Kerola
 *
 */
public abstract class Shape extends VectorObject {

	protected Filter filter;

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}


	@Override
	public void redraw() {
		super.redraw();
		if(filter!=null) {
			Element e = getElement();
			e.setAttribute("filter", "url(#" + filter.getId() + ")");
		}
	}


}
