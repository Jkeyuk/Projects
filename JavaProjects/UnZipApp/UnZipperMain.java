package unzipper;

import java.io.File;
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
        //get files to unzip from user
        String fileToUnzip = promptForFileToUnzip();
        //unzip files
        unzipper.unzip(fileToUnzip);
        System.out.println("File Unzipped...Program shutting down");
    }

    //prompt user for path to output folder
    private static String promptForOutputFolder() {
        String outFolder;
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("");
            System.out.println("Please enter path to output folder:");
            outFolder = scan.nextLine().trim();
        } while (!new File(outFolder).isDirectory());
        return outFolder;
    }

    //prompt user for files to unzip
    private static String promptForFileToUnzip() {
        String fileToUnzip;
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("");
            System.out.println("Please enter full pathname to zip file to unzip:");
            fileToUnzip = scan.nextLine().trim();
        } while (!new File(fileToUnzip).isFile() && !fileToUnzip.endsWith(".zip"));
        return fileToUnzip;
    }
}
