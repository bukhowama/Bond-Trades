import javax.swing.*;

// where the program runs from, created three objects of the classes model, view and controller to start 
public class DataMVC {

	public static void main(String[] args) {

		DataModel model = new DataModel();
		DataView view = new DataView(model);
		DataController controller = new DataController(view, model);

	}

}
