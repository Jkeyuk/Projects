package thumbnailcreator;

import java.io.File;
import java.util.Scanner;

public class ThumbNailCreatorMain {

    public static void main(String[] args) {
        //Get destination folder to save images too
        String destination = getDestination();
        //get source of image files
        String source = getSource();
        //create thumb nail creator object
        ThumbNailCreator c = new ThumbNailCreator(destination);
        //start thumb creator with source
        c.start(source);
    }

    private static String getDestination() {
        String pathToDest;
        Scanner scan = new Scanner(System.in);
        do {//keeps looping until user enters valid directory
            System.out.println("");
            System.out.println("Drag and drop, or enter pathname to the folder you wish to save to:");
            pathToDest = scan.nextLine().trim();
        } while (!new File(pathToDest).isDirectory());
        return pathToDest;
    }

    private static String getSource() {
        String pathToSource;
        Scanner scan = new Scanner(System.in);
        do {//keeps looping untill user enters valid directory or file
            System.out.println("");
            System.out.println("Drag and drop, or enter pathname to image file or folder of images:");
            pathToSource = scan.nextLine().trim();
        } while (!checkSource(pathToSource));
        return pathToSource;
    }

    private static boolean checkSource(String s) {
        if (new File(s).isDirectory()) {
            return true;
        } else {
            return new File(s).isFile();
        }
    }
}
