package CSV_Data_Sorter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is used to sort numerical or textual data in a CSV file. sorted
 * data is written back to the file.
 * 
 * @author Jonathan
 *
 */
public class CSVFileSorter {

	private final CSVFile file;

	/**
	 * Constructs file sorter with CSV file.
	 * 
	 * @param file-
	 *            file to sort
	 */
	public CSVFileSorter(CSVFile file) {
		this.file = file;
	}

	/**
	 * Sorts numerical data from a CSV file and writes data back to file.
	 */
	public void sortNumericalData() {
		ArrayList<String> data = file.getFileData();
		int rowSize = data.get(0).split(",").length;
		ArrayList<Double> numbers = parseNumbers(data);
		numbers.sort(null);
		file.writeDataToFile(buildFileData(numbers, rowSize));
	}

	/**
	 * Parses an ArrayList of strings with comma separated values, and returns
	 * the values as an ArrayList of doubles.
	 * 
	 * @param data
	 *            - ArrayList of strings to parse
	 * @return An ArrayList of doubles with the values that were parsed.
	 */
	private ArrayList<Double> parseNumbers(ArrayList<String> data) {
		ArrayList<String> values = new ArrayList<>();
		data.forEach(x -> values.addAll(Arrays.asList(x.split(","))));
		ArrayList<Double> numbers = new ArrayList<>();
		values.forEach(x -> numbers.add(Double.parseDouble(x)));
		return numbers;
	}

	/**
	 * Sorts textual data in the CSV file and writes data back to file.
	 */
	public void sortTextualData() {
		ArrayList<String> data = file.getFileData();
		int rowSize = data.get(0).split(",").length;
		ArrayList<String> values = new ArrayList<>();
		data.forEach(x -> values.addAll(Arrays.asList(x.split(","))));
		values.replaceAll(y -> y.trim());
		values.sort(null);
		file.writeDataToFile(buildFileData(values, rowSize));
	}

	/**
	 * Builds an ArrayList of data into an ArrayList of strings that can be
	 * writted to the file.
	 * 
	 * @param data
	 *            - data to build
	 * @param rowSize
	 *            - determines size of strings in the ArrayList.
	 * @return An ArrayList of strings to be written back into CSV file.
	 */
	private <T> ArrayList<String> buildFileData(ArrayList<T> data, int rowSize) {
		ArrayList<String> returnData = new ArrayList<>();
		String dataString = "";
		for (int i = 0; i < data.size(); i++) {
			if (i % rowSize == 0 && i != 0) {
				returnData.add(dataString.substring(0, dataString.length() - 1));
				dataString = data.get(i).toString() + ",";
			} else {
				dataString += data.get(i).toString() + ",";
			}
		}
		returnData.add(dataString.substring(0, dataString.length() - 1));
		return returnData;
	}
}
