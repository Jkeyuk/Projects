package texttohtml;

import java.io.File;
import java.util.Scanner;

public class TextToHTMLApp {

    public static void main(String[] args) {
        String sourceDocument = getSourceDoc();
        TextToHTML converter = new TextToHTML(sourceDocument);
        System.out.println("Converting file now....");
        converter.makeHtmlFile();
        System.out.println("Program done.");
    }

    private static String getSourceDoc() {
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.println("");
            System.out.println("Drag and drop, or enter path to text file");
            input = scanner.nextLine().trim();
        } while (!new File(input).isFile());
        return input;
    }
}
