package zipmakerapp;

import java.io.File;
import java.util.Scanner;

public class ZipMakerAppMain {

    public static void main(String[] args) {
        //get name of zip file
        String zipFileName = askUserNameOfZipFile();
        //get directory to save zip to
        String directory = askUserForDirectory();
        //get file or folder to zip
        String toZip = askUserForFilesToZip();
        //create zip maker object
        ZipMaker zipper = new ZipMaker(directory + File.separator + zipFileName);
        //zip input file or folder
        zipper.zip(toZip, toZip);
        //close zip stream
        zipper.closeZipStream();
    }

    private static String askUserNameOfZipFile() {
        //string to return
        String returnString = "";
        //scanner to scan terminal
        Scanner scanner = new Scanner(System.in);
        do {//keep looping until user inputs proper name
            System.out.println("");
            System.out.println("What would you like to name your zip file?");
            System.out.println("Must end with .zip, example: something.zip");
            returnString = scanner.nextLine();
        } while (!returnString.endsWith(".zip"));
        return returnString;
    }

    private static String askUserForDirectory() {
        //string to return
        String returnString = "";
        //scanner to scan terminal
        Scanner scanner = new Scanner(System.in);
        do {//keep looping until proper directory is inputed
            System.out.println("");
            System.out.println("Please enter path of where you want to save your zip");
            System.out.println("example: c:\\users\\someFolder");
            returnString = scanner.nextLine();
        } while (!new File(returnString).isDirectory());
        return returnString;
    }

    private static String askUserForFilesToZip() {
        //string to return
        String returnString = "";
        //scanner to scan terminal
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("");
            System.out.println("Please enter Path to file or folder to zip");
            returnString = scanner.nextLine();
        } while (!new File(returnString).isDirectory());
        return returnString;
    }
}
