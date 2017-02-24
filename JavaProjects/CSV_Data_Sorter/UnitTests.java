package CSV_Data_Sorter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class UnitTests {

	public static void main(String[] args) throws IOException {
		if (getFileDataTests() && writeDataToFileTests()) {
			System.out.println("All CSVFile Tests Passed");
		} else {
			System.out.println("CSVFile tests Failed");
		}
		if (parseNumbersTests() && buildFileDataTests()) {
			System.out.println("ALL sorter tests passed");
		} else {
			System.out.println("Sorter tests failed");
		}

	}

	private static boolean getFileDataTests() {
		CSVFile csvFile1 = new CSVFile(
				new File(".\\src\\CSV_Data_Sorter\\TestData\\numTest2.csv"));

		ArrayList<String> expectedResult1 = new ArrayList<>();
		expectedResult1.add("25,24,23,22,21,20");
		expectedResult1.add("19,18,17,16,15,14");
		expectedResult1.add("13,12,11,10,9,8");
		expectedResult1.add("7,6,5,4,3,2");

		CSVFile csvFile2 = new CSVFile(
				new File(".\\src\\CSV_Data_Sorter\\TestData\\numTest1.csv"));

		ArrayList<String> expectedResult2 = new ArrayList<>();
		expectedResult2.add("35.2,26,12,254.21,255");
		expectedResult2.add("45,487,9,85,-21");
		expectedResult2.add("3,1,2,5,4");

		CSVFile csvFile3 = new CSVFile(
				new File(".\\src\\CSV_Data_Sorter\\TestData\\textTest1.csv"));

		ArrayList<String> expectedResult3 = new ArrayList<>();
		expectedResult3.add("jacob,zebra,delta,charlie,l");
		expectedResult3.add("m,n,o,p,zz");
		expectedResult3.add("Monday,alpha ,roman,kite,bob");

		if (!expectedResult1.equals(csvFile1.getFileData())) {
			return false;
		}
		if (!expectedResult2.equals(csvFile2.getFileData())) {
			return false;
		}
		if (!expectedResult3.equals(csvFile3.getFileData())) {
			return false;
		}
		return true;
	}

	private static boolean writeDataToFileTests() throws IOException {
		ArrayList<String> expectedOutput1 = new ArrayList<>();
		expectedOutput1.add("1,2,3,4,5");
		expectedOutput1.add("6,7,8,9,10");
		expectedOutput1.add("11,12,13,14,15");

		ArrayList<String> expectedOutput2 = new ArrayList<>();
		expectedOutput2.add("a,a,a,a");
		expectedOutput2.add("b,b,b,b");
		expectedOutput2.add("c,c,c,c");

		File file1 = new File(".\\src\\CSV_Data_Sorter\\TestData\\output1.csv");
		File file2 = new File(".\\src\\CSV_Data_Sorter\\TestData\\output2.csv");
		file1.createNewFile();
		file2.createNewFile();
		CSVFile csvFile1 = new CSVFile(file1);
		CSVFile csvFile2 = new CSVFile(file2);

		csvFile1.writeDataToFile(expectedOutput1);
		csvFile2.writeDataToFile(expectedOutput2);

		List<String> out1 = Files.readAllLines(file1.toPath());
		List<String> out2 = Files.readAllLines(file2.toPath());

		if (!out1.equals(expectedOutput1)) {
			return false;
		}
		if (!out2.equals(expectedOutput2)) {
			return false;
		}
		return true;
	}

	private static boolean parseNumbersTests() {
		CSVFileSorter s = new CSVFileSorter(new CSVFile(new File("")));

		ArrayList<String> test1 = new ArrayList<>();
		test1.add("9,8,7,6,5");
		test1.add("4,3,2,1,0");
		test1.add("-1,-2,-3,-4,-5");

		ArrayList<Double> expected1 = new ArrayList<>();
		for (double i = 9; i >= -5; i--) {
			expected1.add(i);
		}

		ArrayList<String> test2 = new ArrayList<>();
		String temp = "";
		for (double j = -100; j <= 300; j++) {
			if (j != 0 && j % 6 == 0) {
				temp += j;
				test2.add(temp);
				temp = "";
			} else {
				temp += j + ",";
			}
		}

		ArrayList<Double> expected2 = new ArrayList<>();
		for (double y = -100; y <= 300; y++) {
			expected2.add(y);
		}

		if (!s.parseNumbers(test1).equals(expected1)) {
			return false;
		}
		if (!s.parseNumbers(test2).equals(expected2)) {
			return false;
		}

		return true;
	}

	private static boolean buildFileDataTests() {
		CSVFileSorter s = new CSVFileSorter(new CSVFile(new File("")));

		ArrayList<String> t1 = new ArrayList<>();
		for (int j = 0; j <= 19; j++) {
			t1.add(Integer.toString(j));
		}

		ArrayList<String> expected1 = new ArrayList<>();
		expected1.add("0,1,2,3,4,5,6,7,8,9");
		expected1.add("10,11,12,13,14,15,16,17,18,19");

		ArrayList<String> t2 = new ArrayList<>();
		for (int i = 0; i <= 999; i++) {
			t2.add(Integer.toString(i));
		}

		ArrayList<String> expected2 = new ArrayList<>();
		String temp = "";
		for (int i = 0; i <= 999; i++) {
			String z = Integer.toString(i);
			if (i != 0 && i % 10 == 0) {
				expected2.add(temp.substring(0, temp.length() - 1));
				temp = z + ",";
			} else {
				temp += z + ",";
			}
		}
		expected2.add(temp.substring(0, temp.length() - 1));

		if (!expected1.equals(s.buildFileData(t1, 10))) {
			return false;
		}

		if (!expected2.equals(s.buildFileData(t2, 10))) {
			return false;
		}
		return true;
	}

}
