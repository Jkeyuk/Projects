
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVFileSorter {

	private final File file;
	private int rowSize = 0;

	public CSVFileSorter(String fileName) {
		this.file = new File(fileName);
	}

	public void sortNumericalData() {
		ArrayList<String> fileData = returnFileData();
		ArrayList<Double> numberList = new ArrayList<>();
		for (String string : fileData) {
			numberList.add(Double.parseDouble(string));
		}
		numberList.sort(null);
		fileData.clear();
		for (Double y : numberList) {
			fileData.add(y.toString());
		}	
		writeDataToFile(fileData);
	}

	public void sortTextualData() {
		ArrayList<String> fileData = returnFileData();
		fileData.sort(null);
		writeDataToFile(fileData);
	}

	private ArrayList<String> returnFileData() {
		String data = "";
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String input;
			while ((input = reader.readLine()) != null) {
				if (rowSize == 0) {//used to measure size of the row.
					rowSize = input.split(",").length;
				}
				data += input + ",";
			}
		} catch (IOException ex) {
			Logger.getLogger(CSVFileSorter.class.getName()).log(Level.SEVERE, null, ex);
		}
		return new ArrayList<String>(Arrays.asList(data.split(",")));
	}

	private void writeDataToFile(ArrayList<String> dataa) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			for (int i = 0; i < dataa.size(); i++) {
				if (i % rowSize == 0 && i != 0) {
					writer.newLine();
					writer.write(dataa.get(i) + ",");
				} else {
					writer.write(dataa.get(i) + ",");
				}
			}
		} catch (IOException ex) {
			Logger.getLogger(CSVFileSorter.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
