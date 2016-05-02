package unzipper;

import java.util.Scanner;

public class UnZipperMain {

    public static void main(String[] args) {
        promptUser();
    }

    public static void promptUser() {
        Scanner scan = new Scanner(System.in);
        System.out.println("*************************************************");
        System.out.println("Please enter path to output folder:");
        String outFolder = scan.nextLine();
        UnZipper unzipper = new UnZipper(outFolder);
        while (true) {
            System.out.println("*************************************************");
            System.out.println("To Exit program just enter exit...otherwise");
            System.out.println("Please enter full pathname to zip file to unzip:");
            String fileToUnzip = scan.nextLine();
            if (fileToUnzip.equalsIgnoreCase("exit")) {
                System.out.println("Shutting down program...");
                break;
            } else {
                System.out.println("Unzipping...");
                unzipper.unzip(fileToUnzip);
            }
        }
    }
}
