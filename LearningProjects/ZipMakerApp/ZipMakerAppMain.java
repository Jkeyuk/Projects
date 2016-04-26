package zipmakerapp;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class ZipMakerAppMain {

    public static void main(String[] args) {
        askForDirectory();
    }

    public static void askForDirectory() {
        Scanner scan = new Scanner(System.in);
        System.out.println();
        System.out.println("***Enter path where you want to save your zip file...");
        System.out.println("***must end with zipName.zip where zipname can be any name:");
        String dest = scan.nextLine();
        try {
            askForFileToZip(dest);
        } catch (FileNotFoundException ex) {
            System.out.println("That pathname incorrect restart and try again....");
            System.exit(0);
        }
    }

    public static void askForFileToZip(String destination) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);
        ZipMaker zipper = new ZipMaker(destination);
        System.out.println();
        System.out.println("****To exit program just type exit otherwise****");
        while (true) {
            System.out.println("Enter or drag and drop the file or Directory to zip");
            String toZip = scan.nextLine();
            if (toZip.equals("exit") || toZip.equals("EXIT")) {
                System.out.println("Shutting Down...");
                break;
            } else {
                System.out.println("Now Zipping...");
                zipper.zip(toZip, toZip);
                zipper.closeZipStream();
            }
        }
    }
}
