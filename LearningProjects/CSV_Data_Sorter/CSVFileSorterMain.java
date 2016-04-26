package csvfilesorter;

import java.util.Scanner;

public class CSVFileSorterMain {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println();
        while (true) {
            System.out.println("To exit just enter exit...otherwise");
            System.out.println("Please enter full path to csv file:");
            String csvFile = scan.nextLine();
            if (csvFile.equals("exit")) {
                System.out.println("Shutting down program....");
                break;
            } else {
                CSVFileSorter sorter = new CSVFileSorter(csvFile);
                System.out.println("Enter 1 to sort numerical data, 2 to sort Text data");
                String typeOfSort = scan.nextLine();
                if (typeOfSort.equals("1")) {
                    System.out.println("Sorting...");
                    sorter.sortNumericalData();
                } else if (typeOfSort.equals("2")) {
                    System.out.println("Sorting...");
                    sorter.sortTextualData();
                }
            }
        }
    }
}
