import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;

public class DataController implements ActionListener, ItemListener {

	private DataView view;
	private DataModel model;

	public DataController(DataView view, DataModel model) {
		this.view = view;
		this.model = model;
		// action listeners assigned to buttons and text field to execute
		view.btnOpen.addActionListener(this);
		view.btnClose.addActionListener(this);
		view.fileName.addActionListener(this);
		run();

	}

	// will keep the application running to infinitely in constant state, change is
	// triggered by a File being saved into the destnFile attribute in the model.
	// Once activated, it will read the contents of the File, create a middle panel
	// with the chart area and assign listeners to the various elements of the chart
	public void run() {
		while (true) {
			System.out.print("");
			if (model.destnFile != null) {
				fileReading();
				view.middlePanel();
				view.dataChart.addMouseListener(listener);
				view.bottomPanel();
				view.xCombo.addItemListener(this);
				view.yCombo.addItemListener(this);
				view.setVisible(true);

				break;
			}
		}
	}

	// this method creates a selectedFile object that only allows us to choose CSV
	// files, pass value of file selected to destnFile variable in model object, and
	// change the text box display to the file name
	public void fileChooser() {
		String location = FileSystems.getDefault().getPath(".").toString();
		JFileChooser fileChooser = new JFileChooser(location);
		FileNameExtensionFilter type = new FileNameExtensionFilter("CSV Files", "csv");
		fileChooser.setFileFilter(type);
		fileChooser.showOpenDialog(view.getParent());
		File selectedFile = fileChooser.getSelectedFile();
		view.fileName.setText(fileChooser.getSelectedFile().getName());
		model.destnFile = selectedFile;
	}

	// method to read scan and read contents of file from the fileChooser, save the
	// first row (titles of columns) separately in an array to be used to determine
	// values of combo boxes later, then keep reading through the file until
	// there's no content left, taking in each row, splitting, converting and
	// creating a bond object using the double array values, finally the bond object
	// (as an array) is added to the arrayList of the model
	public void fileReading() {
		Scanner keyboard = null;
		try {
			keyboard = new Scanner(model.destnFile);
			String titles = keyboard.nextLine();
			model.titles = titles.split(",");
			while (keyboard.hasNext()) {
				String dataRaw = keyboard.next();
				String[] dataSplit = dataRaw.split(",");
				double[] dataDouble = new double[dataSplit.length];
				for (int i = 0; i < dataDouble.length; i++) {
					dataDouble[i] = Double.parseDouble(dataSplit[i]);
				}
				Bond bond = new Bond(dataDouble[0], dataDouble[1], dataDouble[2]);
				model.dataList.add(bond);

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			keyboard.close();
		}
	}

	public void actionPerformed(ActionEvent e) {
		// listeners to execute methods when an event is triggered on buttons
		if (e.getSource() == view.btnOpen) {
			fileChooser();
		}
		if (e.getSource() == view.btnClose) {
			System.exit(0);
		}

	}

	// execute selectedTrade with the values extracted from the mouse event upon
	// clicking on any single point on the chart
	public MouseAdapter listener = new MouseAdapter() {

		public void mouseClicked(MouseEvent e) {
			selectedTrade(e.getX(), e.getY());
		}
	};

	// method to change which columns are used for the graph axis identified by the
	// numbers stored in the variables of the model to keep track of which columns
	// were selected into the combo boxes
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == view.xCombo && e.getStateChange() == ItemEvent.SELECTED) {
			String item = (String) e.getItem();

			for (int i = 0; i < model.titles.length; i++) {
				if (item.equals(model.titles[i])) {
					model.trackingX = i;
					Bond.setXBondDrawing(i);
				}
			}

			updateGraph();
		}

		if (e.getSource() == view.yCombo && e.getStateChange() == ItemEvent.SELECTED) {
			String item = (String) e.getItem();
			for (int i = 0; i < model.titles.length; i++) {
				if (item.equals(model.titles[i])) {
					model.trackingY = i;
					Bond.setYBondDrawing(i);
				}
			}

			updateGraph();
		}
	}

	// method that uses the xValue and yValue trackers from the model to determine
	// which list of values to use for the chart's axis and will repaint whenever
	// triggered by calling the method from within other methods of the program
	// after change of values to the columns
	public void updateGraph() {
		ArrayList<Double> yieldList = new ArrayList<Double>();
		ArrayList<Double> maturityList = new ArrayList<Double>();
		ArrayList<Double> amountList = new ArrayList<Double>();

		for (Bond b : model.dataList) {
			yieldList.add(b.getyield());
			maturityList.add(b.getdays());
			amountList.add(b.getamount());
		}

		if (model.trackingX == 0) {
			view.dataChart.setAxisX(yieldList);
		} else if (model.trackingX == 1) {
			view.dataChart.setAxisX(maturityList);
		} else if (model.trackingX == 2) {
			view.dataChart.setAxisX(amountList);
		}
		if (model.trackingY == 0) {
			view.dataChart.setAxisY(yieldList);
		} else if (model.trackingY == 1) {
			view.dataChart.setAxisY(maturityList);
		} else if (model.trackingY == 2) {
			view.dataChart.setAxisY(amountList);
		}

		view.dataChart.repaint();
	}

	// compares parameter values input with all values on chart. Upon finding a
	// match, it displays the related data of that bond in the text box of the
	// bottom
	// panel of the model
	public void selectedTrade(int x, int y) {
		double[] values = new double[3];
		for (Bond bond : model.dataList) {
			double X = bond.getXBondDrawing() * 680 / (view.dataChart.getMaxX() - view.dataChart.getMinX());
			double Y = bond.getYBondDrawing() * 430 / (view.dataChart.getMaxY() - view.dataChart.getMinY());
			if (view.dataChart.getMinX() < 0) {
				X = X - view.dataChart.getMinX() * 680 / (view.dataChart.getMaxX() - view.dataChart.getMinX());
			}
			if (view.dataChart.getMinY() < 0) {
				Y = Y - view.dataChart.getMinY() * 430 / (view.dataChart.getMaxY() - view.dataChart.getMinY());
			}
			if (x > (X + 129) && x < (X + 137) && y < (457 - Y) && y > (449 - Y)) {

				String bondInfo = "";
				values[0] = bond.getyield();
				values[1] = bond.getdays();
				values[2] = bond.getamount();
				for (int j = 0; j < values.length; j++) {
					bondInfo += model.titles[j] + ": " + values[j] + " ";
				}

				view.tradeInfo.setText(bondInfo);
			}
		}
	}

}
