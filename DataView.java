import java.awt.BorderLayout;
import javax.swing.JFrame;
import java.util.ArrayList;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JPanel;

public class DataView extends JFrame {

	private static final long serialVersionUID = -3076430845876711202L;
	// display name of chosen file
	public JTextField fileName = new JTextField("<Name of File>");
	// button that triggers a file chooser method which will allow the user to
	// select a csv file from the directory
	public JButton btnOpen = new JButton("Open");
	// button that exits application
	public JButton btnClose = new JButton("Close");
	// field to display the clicked bond related data
	public JTextField tradeInfo = new JTextField(40);
	// combo boxes to choose which columns will be used for the x and y axis of the
	// graph
	public JComboBox<String> xCombo;
	public JComboBox<String> yCombo;
	// main panel that will hold all panels of the GUI and add them to the frame
	private JPanel mainPanel = new JPanel(new BorderLayout());
	// instance of DataModel that allows us to access its attributes
	private DataModel model;
	// component responsible for drawing the plot
	public Chart dataChart;

	// when creating an object it will setup the frame and top panel only (which
	// contains
	// essential functionality that allow us to open a file), other panels will be
	// created afterwards following sequence of program
	public DataView(DataModel model) {
		this.model = model;
		topPanel();
		buildFrame();
	}

	public void topPanel() {
		// creates the part of the panel that will hold the open, close buttons and the
		// filename text field and add the elements to it
		JPanel topPanel = new JPanel();
		topPanel.add(btnOpen);
		topPanel.add(fileName);
		topPanel.add(btnClose);
		// add top panel too the main panel at the top
		mainPanel.add(topPanel, BorderLayout.NORTH);

	}

	public void middlePanel() {
		// creates the panel that holds the chart
		// define arrays local to the panel for the chart
		ArrayList<Double> xList = new ArrayList<Double>();
		ArrayList<Double> yList = new ArrayList<Double>();
		// add default values from the array of bonds to the defined local arrays
		for (Bond b : model.dataList) {
			xList.add(b.getyield());
			yList.add(b.getdays());
		}
		model.trackingX = 0;
		model.trackingY = 1;
		// create an instance of the middle panel
		JPanel middlePanel = new JPanel(new BorderLayout());
		// create a chart object with the default values defined earlier in the method
		dataChart = new Chart(xList, yList);
		// updates the graph with the values
		dataChart.repaint();
		// add the chart object to the middle panel
		middlePanel.add(dataChart);
		// add the middle panel with the chart to the main panel
		mainPanel.add(middlePanel, BorderLayout.CENTER);
	}

	public void bottomPanel() {
		// creates the panel that will hold the combo boxes to select axis and the bond
		// trades information in a text field
		JPanel bottomPanel = new JPanel();
		// combo boxes xAxis and yAxis will be initialized with the values stores from
		// the csv file's 'titles' attributes
		xCombo = new JComboBox<String>(model.titles);
		yCombo = new JComboBox<String>(model.titles);
		// default selected values for x and y axis in the combo boxes
		xCombo.setSelectedItem(model.titles[0]);
		yCombo.setSelectedItem(model.titles[1]);
		// add the combo boxes to the bottom panel as well as the text field
		bottomPanel.add(xCombo);
		bottomPanel.add(yCombo);
		bottomPanel.add(tradeInfo);
		// add the bottom panel with its contents to the main panel
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
	}

	public void buildFrame() {
		// create a frame and add the main panel to it, set the visibility to true
		final int FRAME_WIDTH = 1000;
		final int FRAME_HEIGHT = 700;
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle("Trades");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(mainPanel);
		this.setVisible(true);

	}

}
