import java.io.File;
import java.util.ArrayList;

// model class contains the information that the file is going to retrieve, keeps track of what column is used to draw/plot the graph
public class DataModel {
	
	// keep track of which x and y values will be used for the axis of the graph
	int trackingX;
	int trackingY;
	// stores the file retrieved by the file chooser method 
	File destnFile;
	// titles of the columns from extracted csv file
	String[] titles;
	// financial information from the csv file
	ArrayList<Bond> dataList = new ArrayList<>();

}
