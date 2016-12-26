package TextToHTML;

import java.io.File;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		TextToHTML converter = new TextToHTML(getFile());
		System.out.println("Converting file now....");
		converter.makeHtmlFile();
		System.out.println("Program done.");
	}

	private static String getFile() {
		Scanner scanner = new Scanner(System.in);
		String input;
		do {
			System.out.println("");
			System.out.println("Enter full path to text file:");
			input = scanner.nextLine().trim();
		} while (!new File(input).isFile());
		scanner.close();
		return input;
	}
}
