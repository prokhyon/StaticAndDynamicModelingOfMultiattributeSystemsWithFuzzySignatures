package net.prokhyon.modularfuzzy.fuzzySet.model.descriptor;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("above")
public class FuzzyPointAbove implements IFuzzyPoint {

	@XStreamAlias("x")
	@XStreamAsAttribute
	private double xCoordinate;

	public FuzzyPointAbove(double d) {
		super();
		this.xCoordinate = d;
	}

	@Override
	public int getCoordinateDimension() {
		return 1;
	}

	public double getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	@Override
	public String toString() {
		return "FuzzyPointAbove [xCoordinate=" + xCoordinate + "]";
	}

}