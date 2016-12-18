package CountWordsInTextFile;

import java.io.File;
import java.util.Scanner;

/**
 * @author jonathan
 * 
 *         This program requests a text file from the user and displays the
 *         number of words in the file.
 */
public class WordCounter {

	public static void main(String[] args) {
		FileWordCounter counter = new FileWordCounter();
		System.out.println("Word Count = " + counter.countWordsInFile(new File(getFile())));
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
