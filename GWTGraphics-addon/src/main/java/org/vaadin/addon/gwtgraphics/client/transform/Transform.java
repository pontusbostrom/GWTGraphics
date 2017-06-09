package org.vaadin.addon.gwtgraphics.client.transform;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Transform {

	public enum TransformType {
		ROTATE,
		TRANSLATE,
		SCALE,
		SKEW_X,
		SKEW_Y,
		MATRIX
	}

	protected TransformType type;
	protected List<Double> values = new ArrayList<Double>();

	protected Transform(TransformType type, double... values) {
		this.type = type;
		for(double v : values) {
			this.values.add(v);
		}
	}

	protected double getValue(int idx) {
		return values.get(idx);
	}

	protected void setValue(int idx, double value) {
		values.set(idx, value);
	}

	protected void setValues(double... v) {
		int i = 0;
		for(double d : v) {
			values.set(i++, d);
		}
	}

	protected void setType(TransformType t) {
		type = t;
	}

	public TransformType getType() {
		return type;
	}

	public String getTypeString() {
		switch(type) {
		case MATRIX:
			return "matrix";
		case ROTATE:
			return "rotate";
		case SCALE:
			return "scale";
		case SKEW_X:
			return "skewX";
		case SKEW_Y:
			return "skewY";
		case TRANSLATE:
			return "translate";
		}
		return "rotate";
	}

	public static RotationTransform rotation(double amount) {
		return new RotationTransform(amount);
	}

	public static TranslationTransform translation(double xamount, double yamount) {
		return new TranslationTransform(xamount, yamount);
	}

	public static ScalingTransform scaling(double amount) {
		return new ScalingTransform(amount);
	}

	public static ScalingTransform scaling(double xamount, double yamount) {
		return new ScalingTransform(xamount, yamount);
	}

	public static SkewTransform skewX(double amount) {
		return new SkewTransform(amount, true);
	}

	public static SkewTransform skewY(double amount) {
		return new SkewTransform(amount, false);
	}

	public static MatrixTransform matrix(double a, double b, double c, double d, double tx, double ty) {
		return new MatrixTransform(a, b, c, d, tx, ty);
	}

	/**
	 * Generate an SVG string.
	 * @return tag field declaration, for example "rotate(45)"
	 */
	public String toSVGString() {
		String tag = getTypeString();
		tag += "(";
		Iterator<Double> it = values.iterator();
		while(it.hasNext()) {
			double value = it.next();
			tag += value;
			if(it.hasNext()) {
				tag += ",";
			}
		}
		tag += ")";
		return tag;
	}

}
