import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.awt.Graphics2D;
import javax.swing.JComponent;

public class Chart extends JComponent {

	// array lists for the points on graph
	private ArrayList<Double> xAxis;
	private ArrayList<Double> yAxis;

	public Chart(ArrayList<Double> xAxis, ArrayList<Double> yAxis) {
		this.xAxis = xAxis;
		this.yAxis = yAxis;
	}

	// set the values used for x and y axis, values will be loaded via controller
	public void setAxisX(ArrayList<Double> list) {
		xAxis = list;
	}

	public void setAxisY(ArrayList<Double> list) {

		yAxis = list;
	}

	// maximum value from the x axis array list for the selectedTrade method in
	// controller class
	public double getMaxX() {
		double max = xAxis.get(0);
		for (int i = 1; i < xAxis.size(); i++) {
			if (xAxis.get(i) > max) {
				max = xAxis.get(i);
			}
		}
		return max;
	}

	// maximum value from the y axis array list for the selectedTrade method in
	// controller class
	public double getMaxY() {
		double max = yAxis.get(0);
		for (int i = 1; i < yAxis.size(); i++) {
			if (yAxis.get(i) > max) {
				max = yAxis.get(i);
			}
		}
		return max;
	}

	// minimum value from the x axis array list for the selectedTrade method in
	// controller class
	public double getMinX() {

		double minValue = xAxis.get(0);
		for (int i = 1; i < xAxis.size(); i++) {
			if (xAxis.get(i) < minValue) {
				minValue = xAxis.get(i);
			}
		}
		return minValue;
	}

	// minimum value from the y axis array list for the selectedTrade method in
	// controller class
	public double getMinY() {
		// Getting the minimum value from a list
		double minValue = yAxis.get(0);
		for (int i = 1; i < yAxis.size(); i++) {
			if (yAxis.get(i) < minValue) {
				minValue = yAxis.get(i);
			}
		}
		return minValue;
	}

	public void paintComponent(Graphics graphic) {
		Graphics2D gr2D = (Graphics2D) graphic;
		whiteBackgroundCreation(gr2D);
		XYAxisCreation(gr2D);
		HatchMarksCreation(gr2D);
		pointsCreation(gr2D);

	}

	// draw points using the two class array attributes for x and y axis
	public void pointsCreation(Graphics2D graphic) {
		for (int i = 0; i < xAxis.size(); i++) {
			graphic.setColor(Color.BLUE);
			double x = xAxis.get(i) * 680 / (getMaxX() - getMinX());
			double y = yAxis.get(i) * 430 / (getMaxY() - getMinY());
			double r = 5;
			if (getMinX() < 0) {
				x = x - getMinX() * 680 / (getMaxX() - getMinX());
			}
			if (getMinY() < 0) {
				y = y - getMinY() * 430 / (getMaxY() - getMinY());
			}
			Ellipse2D.Double e = new Ellipse2D.Double(x + 130, 450 - y, r, r);
			graphic.fill(e);
		}
	}

	// draw x and y axis
	public void XYAxisCreation(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		g2.drawLine(130, 20, 130, 450);
		g2.drawLine(130, 450, 830, 450);

	}

	// draw hatch marks
	public void HatchMarksCreation(Graphics2D g2) {
		for (int i = 0; i < 11; i++) {
			g2.drawLine(130, 450 - i * 430 / 10, 110, 450 - i * 430 / 10);
			String Y = String.format("%.1f", i * (getMaxY() - getMinY()) / 10 + getMinY());
			g2.drawString(Y, 60, 450 - i * 430 / 10);
			g2.drawLine(130 + i * 680 / 10, 450, 130 + i * 680 / 10, 440);
			String X = String.format("%.1f", i * (getMaxX() - getMinX()) / 10 + getMinX());
			g2.drawString(X, 110 + i * 680 / 10, 465);
		}

	}

	// draw chart's white background
	public void whiteBackgroundCreation(Graphics2D g2) {
		g2.setColor(Color.WHITE);
		g2.fillRect(130, 20, 700, 430);
		g2.setColor(Color.BLACK);
	}

}
