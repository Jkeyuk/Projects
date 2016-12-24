package CSV_Data_Sorter;

import java.io.File;
import java.util.ArrayList;

public class Tests {

	public static void main(String[] args) {

		// --------------CSVFile tests -----------------------------
		CSVFile file = new CSVFile(new File("C:\\Users\\Public\\JavascriptApps\\test.csv"));
		
		// ------------- getFileData test -----------------------------
		ArrayList<String> data = file.getFileData();
		System.out.println("----orginal lines of data----");
		data.forEach(System.out::println);
		
		// ---------writeDataToFile test ---------------------------
		ArrayList<String> newData = new ArrayList<>();
		newData.add("a,b,c,d,e,f");
		newData.add("g,h,i,j,k,l");
		file.writeDataToFile(newData);
		data = null;
		data = file.getFileData();
		System.out.println("-----new lines of data-----");
		data.forEach(System.out::println);

		// ----------------CSVFileSorter tests---------------------------------
		CSVFile file2 = new CSVFile(new File("C:\\Users\\Public\\JavascriptApps\\numbers.csv"));
		CSVFileSorter sorter = new CSVFileSorter(file2);
		
		System.out.println("-----before sorting-----");
		file2.getFileData().forEach(System.out::println);
		sorter.sortNumericalData();
		System.out.println("-----after sorting-----");
		file2.getFileData().forEach(System.out::println);
		
		CSVFile file3 = new CSVFile(new File("C:\\Users\\Public\\JavascriptApps\\words.csv"));
		sorter = new CSVFileSorter(file3);
		
		System.out.println("-----before sorting-----");
		file3.getFileData().forEach(System.out::println);
		sorter.sortTextualData();
		System.out.println("-----after sorting-----");
		file3.getFileData().forEach(System.out::println);

	}
}
