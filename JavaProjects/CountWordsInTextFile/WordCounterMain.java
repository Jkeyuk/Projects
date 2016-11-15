package CountWordsInTextFile;

import java.io.File;
import java.util.Scanner;

/**
 * @author jonathan
 * 	This program requests a .txt file from the user and displays the number of
 * words in the file.
 */
public class WordCounterMain {

	public static void main(String[] args) {
		WordCounter c = new WordCounter(getFile());
		System.out.println("Word Count = " + c.count());
	}

	private static String getFile() {
		Scanner s = new Scanner(System.in);
		String returnS;
		do {
			System.out.println("");
			System.out.println("Please enter path to text file to count:");
			returnS = s.nextLine();
		} while (!new File(returnS).isFile());
		s.close();
		return returnS;
	}
}
