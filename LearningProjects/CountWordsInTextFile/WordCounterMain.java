package wordcounter;

import java.io.File;
import java.util.Scanner;

public class WordCounterMain {

    public static void main(String[] args) {
        WordCounter c = new WordCounter(getFile());
        c.count();
    }

    private static String getFile() {
        Scanner s = new Scanner(System.in);
        String returnS;
        do {
            System.out.println("");
            System.out.println("Please enter path to text file to count:");
            returnS = s.nextLine();
        } while (!new File(returnS).isFile());
        return returnS;
    }
}
