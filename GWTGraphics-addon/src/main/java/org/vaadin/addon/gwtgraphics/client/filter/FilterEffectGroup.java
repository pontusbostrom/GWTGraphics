package org.vaadin.addon.gwtgraphics.client.filter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.dom.client.Element;


public class FilterEffectGroup {

	protected final Set<FilterEffect> effects;

	public FilterEffectGroup() {
		effects = new LinkedHashSet<FilterEffect>();
	}

	public boolean isTrivial() {
		return effects.size() < 2;
	}

	public void add(FilterEffect e) {
		effects.add(e);
	}

	public void remove(FilterEffect e) {
		effects.remove(e);
	}

	List<Element> getElements() {
		List<Element> elements = new ArrayList<>();
		for(FilterEffect effect: effects) {
			elements.add(effect.getElement());
		}
		return elements;
	}

}
