package CSV_Data_Sorter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class represents a CSV file.
 * 
 * @author Jonathan
 *
 */
public class CSVFile {
	private final File csvFile;

	/**
	 * Constructs a CSV file with a given file.
	 * 
	 * @param csvFile
	 *            - File representing CSV file.
	 */
	public CSVFile(File csvFile) {
		this.csvFile = csvFile;
	}

	/**
	 * Returns the lines of this file as an ArrayList.
	 * 
	 * @return - Returns the lines of this file as an ArrayList.
	 */
	public ArrayList<String> getFileData() {
		ArrayList<String> data = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
			reader.lines().forEach(x -> data.add(x));
			return data;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Writes the elements of a given ArrayList to the CSV file.
	 * 
	 * @param dataa
	 *            - ArrayList to write to file.
	 */
	public void writeDataToFile(ArrayList<String> dataa) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
			for (String string : dataa) {
				writer.write(string);
				writer.newLine();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
