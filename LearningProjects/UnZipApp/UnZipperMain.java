package unzipper;

import java.util.Scanner;

public class UnZipperMain {

    public static void main(String[] args) {
        promptUser();
    }

    //prompt user for inputs
    public static void promptUser() {
        //get output folder from user
        String outFolder = promptForOutputFolder();
        //create unzipper object with out folder
        UnZipper unzipper = new UnZipper(outFolder);
        while (true) {
            //get files to unzip from user
            String fileToUnzip = promptForFileToUnzip();
            //if input is exit shutdown program, else unzip files or directory
            if (fileToUnzip.equalsIgnoreCase("exit")) {
                System.out.println("Shutting down program...");
                break;
            } else {
                System.out.println("Unzipping...");
                unzipper.unzip(fileToUnzip);
            }
        }
    }

    //prompt user for path to output folder
    private static String promptForOutputFolder() {
        Scanner scan = new Scanner(System.in);
        System.out.println("*************************************************");
        System.out.println("Please enter path to output folder:");
        String outFolder = scan.nextLine();
        return outFolder;
    }

    //prompt user for files to unzip
    private static String promptForFileToUnzip() {
        Scanner scan = new Scanner(System.in);
        System.out.println("*************************************************");
        System.out.println("To Exit program just enter exit...otherwise");
        System.out.println("Please enter full pathname to zip file to unzip:");
        String fileToUnzip = scan.nextLine();
        return fileToUnzip;
    }
}
