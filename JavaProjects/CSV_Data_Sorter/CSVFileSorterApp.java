
import java.io.File;
import java.util.Scanner;

public class CSVFileSorterApp {

	public static void main(String[] args) {
		if (args.length != 1) {// check proper usage
			System.out.println("Proper usage is:");
			System.out.println("java CSVFileSorterApp [Path To CSV file]");
			System.out.println("example: java CSVFileSorterApp test.csv");
		} else if (!new File(args[0]).isFile()) { //check valid file
			System.out.println("You did not enter a valid file");
		} else {
			CSVFileSorter sorter = new CSVFileSorter(args[0]);
			Scanner scan = new Scanner(System.in);
			System.out.println("Enter the number to choose sorting type.");
			System.out.println("1) To sort numerical data");
			System.out.println("2) To sort textual data");
			String userChoice = scan.nextLine();
			if (userChoice.equals("1")) {
				sorter.sortNumericalData();
			} else if (userChoice.equals("2")) {
				sorter.sortTextualData();
			}
			scan.close();
		}
	}
}
