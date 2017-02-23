package CountWordsInTextFile;

import java.io.File;
import java.util.Scanner;

/**
 * @author jonathan
 * 
 *         This program requests a text file from the user and displays the
 *         number of words in the file.
 */
public class Main {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		File file = null;
		do {
			System.out.println("");
			System.out.println("Please enter path to text file to count:");
			file = new File(s.nextLine().trim());
		} while (!file.isFile());
		s.close();
		System.out.println("Word Count = " + FileWordCounter.countWordsInFile(file));
	}

}
