package zipmakerapp;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ZipMakerAppMain {

    public static void main(String[] args) {
        //sentinel for control flow
        boolean sentinel = true;
        //zipmaker object to send to method
        ZipMaker zipper = null;
        while (sentinel) {//loop untill there are no errors
            try {//get path to zip from user and turn sentinel off
                zipper = askForPathToZip();
                sentinel = false;
            } catch (FileNotFoundException ex) {//if file not found re loop and show error
                Logger.getLogger(ZipMakerAppMain.class.getName()).log(Level.SEVERE, null, ex);
                errorMessage();
            }
        }//prompt user for files to zip
        askForFileToZip(zipper);
    }

    private static ZipMaker askForPathToZip() throws FileNotFoundException {
        //string to make zipmaker
        String dest = null;
        //sentinel to control flow
        boolean sentinel = true;
        //scanner to read input from terminal
        Scanner scan = new Scanner(System.in);
        while (sentinel) {
            System.out.println("***************************************************");
            System.out.println("Enter path where you want to save your zip file...");
            System.out.println("must end with zipName.zip where zipName can be any name:");
            dest = scan.nextLine();
            //if file path does not end with .zip re ask for pathname
            sentinel = checkForZipFile(dest);
        }//make zip maker with proper dest
        ZipMaker zipper = new ZipMaker(dest);
        //return zip maker
        return zipper;
    }

    private static void askForFileToZip(ZipMaker zipper) {
        //scanner to read terminal input
        Scanner scan = new Scanner(System.in);
        System.out.println("**************************************************");
        System.out.println("To exit program just type exit otherwise");
        System.out.println("Enter or drag and drop the file or Directory to zip");
        //scan terminal input
        String toZip = scan.nextLine();
        if (toZip.equalsIgnoreCase("exit")) {
            System.out.println("Shutting Down...");
        } else {
            System.out.println("Now Zipping...");
            zipper.zip(toZip, toZip);
            zipper.closeZipStream();
            System.out.println("File zipped program is shutting down...");
        }

    }

    private static void errorMessage() {
        System.out.println("*********************************************");
        System.out.println("That pathname is incorrect");
        System.out.println("A example would be c:\\users\\newzip.zip");
        System.out.println("please try again");
        System.out.println("************************************************");
    }

    private static boolean checkForZipFile(String dest) {
        //make sure destination file ends with .zip
        boolean test = dest.endsWith(".zip");
        if (test) {
            return false;
        } else {
            errorMessage();
            return true;
        }
    }
}
