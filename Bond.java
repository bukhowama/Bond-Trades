import java.util.ArrayList;

// class to retrieve information about each bond trade represented by a dot in the graph
public class Bond {

	private double yield;
	private double days;
	private double amount;
	// counters that will ensure we retrieve the correct value to compare in our
	// mouse listener (matches integer to attribute of class through get methods
	// below), by default they point
	// to yield and maturity
	private static int xBondDrawing = 0;
	private static int yBondDrawing = 1;

	// constructor
	public Bond(double y, double doubleData, double doubleData2) {
		yield = y;
		days = doubleData;
		amount = doubleData2;

	}

	// get and set methods to retrieve and store values in objects of class
	public double getyield() {
		return yield;
	}

	public double getdays() {
		return days;
	}

	public double getamount() {
		return amount;
	}

	// set methods for that will store a number indicating which column x and y axis
	// was changed into upon user click
	public static void setXBondDrawing(int x) {
		xBondDrawing = x;
	}

	public static void setYBondDrawing(int y) {
		yBondDrawing = y;
	}

	// get methods that will use the integer input from the set method to determine
	// which value represents that column and returns the attribute
	public double getXBondDrawing() {
		double value = 0;
		if (xBondDrawing == 0) {
			value = yield;
		} else if (xBondDrawing == 1) {
			value = days;
		} else if (xBondDrawing == 2) {
			value = amount;
		}
		return value;
	}

	public double getYBondDrawing() {
		double value = 0;
		if (yBondDrawing == 0) {
			value = yield;
		} else if (yBondDrawing == 1) {
			value = days;
		} else if (yBondDrawing == 2) {
			value = amount;
		}
		return value;
	}

}
