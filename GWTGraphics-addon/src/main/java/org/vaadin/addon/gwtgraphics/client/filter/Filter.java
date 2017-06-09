package org.vaadin.addon.gwtgraphics.client.filter;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.vaadin.addon.gwtgraphics.client.Definition;
import org.vaadin.addon.gwtgraphics.client.impl.util.SVGUtil;

import com.google.gwt.dom.client.Element;

public class Filter implements Definition{

	protected static int INSTANCE_COUNT = 0;

	protected FilterEffectGroup effects;
	protected String id;
	protected LinkedHashMap<String,String> parameters;

	public Filter() {
		id = "filter_" + (++INSTANCE_COUNT);
		effects = new FilterEffectGroup();
		parameters = new LinkedHashMap<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void addEffect(FilterEffect e) {
		effects.add(e);
	}

	public void removeEffect(FilterEffect e) {
		effects.remove(e);
	}

	public void setParameter(String key, String value) {
		if(value == null || value.isEmpty()) {
			parameters.remove(key);
		} else{
			parameters.put(key, value);
		}
	}

	@Override
	public Element getElement() {
		Element element = SVGUtil.createSVGElementNS("filter");
		element.setAttribute("id", getId());
		for(Entry<String,String> entry: parameters.entrySet()) {
			element.setAttribute(entry.getKey(), entry.getValue());
		}
		for(Element effect: effects.getElements() ){
			element.appendChild(effect);
		}

		return element;
	}

}
