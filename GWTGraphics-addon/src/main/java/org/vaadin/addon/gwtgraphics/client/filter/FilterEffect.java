package org.vaadin.addon.gwtgraphics.client.filter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.vaadin.addon.gwtgraphics.client.impl.util.SVGUtil;

import com.google.gwt.dom.client.Element;

public class FilterEffect {

	protected static int INSTANCE_COUNT = 0;

	public static enum InputSource {
		SOURCE_GRAPHIC ("SourceGraphic"),
		CUSTOM("Custom");

		private String str;

		InputSource(String str) {
			this.str = str;
		}

		public String getSource() {
			return str;
		}


	}

	protected Map<String, String> parameters;
	protected InputSource inputSource = InputSource.SOURCE_GRAPHIC;
	protected String id;
	protected String tag;

	public FilterEffect(String tag) {
		this.tag = tag;
		parameters = new LinkedHashMap<String,String>();
		id = "filter_effect_" + (++INSTANCE_COUNT);
	}

	public String getID() {
		return id;
	}

	public void setInput(String s) {
		if(s == null) {
			inputSource = InputSource.SOURCE_GRAPHIC;
		} else {
			inputSource = InputSource.CUSTOM;
		}
	}

	public InputSource getInputSource() {
		return inputSource;
	}


	/**
	 * Get current value of a previously set parameter
	 *
	 * @param pname
	 *            name of parameter
	 * @return a String value or null
	 */
	public String getParameter(String pname) {
		return getParameter(pname, null);
	}

	/**
	 * Get the current value of a previously set parameter, or a default value
	 * if no value is currently set
	 *
	 * @param pname
	 *            name of parameter
	 * @param defaultValue
	 *            value to return if no value has been set
	 * @return a String value
	 */
	public String getParameter(String pname, String defaultValue) {
		String value = parameters.get(pname);
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

	/**
	 * Set a parameter. If pvalue is null, the parameter is removed.
	 *
	 * @param pname
	 *            name of parameter
	 * @param pvalue
	 *            value of parameter
	 */
	public void setParameter(String pname, String pvalue) {
		if (pvalue == null || pvalue.isEmpty()) {
			parameters.remove(pname);
		} else {
			parameters.put(pname, pvalue);
		}
	}

	public Element getElement() {
		Element element = SVGUtil.createSVGElementNS(tag);
		element.setAttribute("in", getInputSource().getSource());
		for(Entry<String,String> entry: parameters.entrySet()){
			element.setAttribute(entry.getKey(), entry.getValue());
		}
		return element;
	}

}
