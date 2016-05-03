package csvfilesorter;

import java.util.Scanner;

public class CSVFileSorterMain {

    public static void main(String[] args) {
        while (true) {
            //prompt user for csv file to sort
            String csvFile = promptForCsvFile();
            //if input is exit shut down program
            if (csvFile.equalsIgnoreCase("exit")) {
                System.out.println("Shutting down program....");
                break;
            } else {//else prompt user for data type to sort
                promptForSortType(csvFile);
            }
        }
    }

    //ask user for pathname to csv file, return string
    private static String promptForCsvFile() {
        //scanner to get input from terminal
        Scanner scan = new Scanner(System.in);
        //display prompt message
        System.out.println();
        System.out.println("To exit just enter exit...otherwise");
        System.out.println("Please enter full path to csv file:");
        String csvFile = scan.nextLine();
        //return pathname as string
        return csvFile;
    }

    //prompt user for type of data to sort, then sort data
    private static void promptForSortType(String csvFile) {
        //scanner to read terminal input
        Scanner scan = new Scanner(System.in);
        //create sorter object with path to csv file
        CSVFileSorter sorter = new CSVFileSorter(csvFile);
        //prompt message for user
        System.out.println("Enter 1 to sort numerical data, 2 to sort Text data");
        String typeOfSort = scan.nextLine();
        //if input is 1 sort as numerical data
        if (typeOfSort.equals("1")) {
            System.out.println("Sorting...");
            sorter.sortNumericalData();
            //or if input is 2 sort as textual data
        } else if (typeOfSort.equals("2")) {
            System.out.println("Sorting...");
            sorter.sortTextualData();
        }
    }
}
