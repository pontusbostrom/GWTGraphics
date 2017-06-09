package org.vaadin.addon.gwtgraphics.client.gradient;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.vaadin.addon.gwtgraphics.client.Definition;
import org.vaadin.addon.gwtgraphics.client.transform.Transform;

/**
 * Base gradient class
 */
public abstract class Gradient implements Definition{


	public static enum GradientUnits {
		USER_SPACE_ON_USE, OBJECT_BOUNDING_BOX
	}

	public static enum GradientSpredMethod {
		PAD, REFLECT, REPEAT
	}

	protected static int INSTANCE_COUNTER = 0;

	protected String id;
	protected Set<GradientStop> stops;
	protected Map<String, String> parameters;
	protected Transform transform;

	public Gradient() {
		id = "gradient_" + (++INSTANCE_COUNTER);
		stops = new LinkedHashSet<GradientStop>();
		parameters = new LinkedHashMap<String, String>();
	}

	public void setGradientUnits(GradientUnits u) {
		switch (u) {
		case USER_SPACE_ON_USE:
			setParameter("gradientUnits", "userSpaceOnUse");
			break;
		case OBJECT_BOUNDING_BOX:
		default:
			setParameter("gradientUnits", "objectBoundingBox");
			break;
		}
	}

	public GradientUnits getGradientUnits() {
		// Default: ObjectBoundingBox
		String value = getParameter("gradientUnits", "objectBoundingBox");
		switch (value) {
		case "userSpaceOnUse":
			return GradientUnits.USER_SPACE_ON_USE;
		case "objectBoundingBox":
			return GradientUnits.OBJECT_BOUNDING_BOX;
		}
		return GradientUnits.OBJECT_BOUNDING_BOX;
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

	public void setTransform(Transform t) {
		transform = t;
	}

	public Transform getTransform() {
		return transform;
	}

	/**
	 * Add a gradient stop
	 *
	 * @param stop
	 *            a GradientStop instance that should not be null
	 */
	public void addStop(GradientStop stop) {
		assert stop != null : "GradientStop parameter should never be null";
		stops.add(stop);
	}

	/**
	 * Remove a previously added gradient stop
	 *
	 * @param stop
	 *            a GradientStop instance that should not be null
	 */
	public void removeStop(GradientStop stop) {
		assert stop != null : "GradientStop parameter should never be null";
		stops.remove(stop);
	}

	protected Set<GradientStop> getStops() {
		return stops;
	}

	/**
	 * Get the DOM ID associated with this Gradient. A value for this is
	 * auto-generated.
	 *
	 * @return a unique ID string
	 */
	public String getId() {
		return id;
	}

	/**
	 * Explicitly set the ID of this gradient. You should never need to do this.
	 *
	 * @param id
	 *            a unique ID string
	 */
	public void setId(String id) {
		this.id = id;
	}

}
