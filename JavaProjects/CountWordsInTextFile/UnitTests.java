package CountWordsInTextFile;

import java.io.File;

public class UnitTests {
	public static void main(String[] args) {
		System.out.println("Count words in String Test:");
		System.out.println(countWordsInStringTests());
		System.out.println("Count words in File Test:");
		System.out.println(countWordsInFileTests());
	}

	private static boolean countWordsInStringTests() {
		String s1 = "";
		String s2 = "one";
		String s3 = "Two Words";
		String s4 = "This string has 5 words.";
		String s5 = "It's another string. This time with 11 words and 2 sentences.";
		String s6 = "When I first brought my cat home from the humane society she was"
				+ " a mangy, pitiful animal. It cost a lot to adopt her: forty dollars."
				+ " And then I had to buy litter, a litterbox, food, and dishes for her "
				+ "to eat out of. Two days after she came home with me she got taken to the"
				+ " pound by the animal warden. There's a leash law for cats in Fort Collins."
				+ " If they're not in your yard they have to be on a leash. Anyway, my cat "
				+ "is my best friend. I'm glad I got her. She sleeps under the covers with me"
				+ " when it's cold. Sometimes she meows a lot in the middle of the night and"
				+ " wakes me up, though.The Java Tutorials are continuously updated to keep "
				+ "up with changes to the Java Platform and to incorporate feedback from our readers.";

		if (FileWordCounter.countWordsInString(s1) != 0) {
			System.out.println("s1 failed");
			return false;
		}
		if (FileWordCounter.countWordsInString(s2) != 1) {
			System.out.println("s2 failed");
			return false;
		}
		if (FileWordCounter.countWordsInString(s3) != 2) {
			System.out.println("s3 failed");
			return false;
		}
		if (FileWordCounter.countWordsInString(s4) != 5) {
			System.out.println("s4 failed");
			return false;
		}
		if (FileWordCounter.countWordsInString(s5) != 11) {
			System.out.println("s5 failed");
			return false;
		}
		if (FileWordCounter.countWordsInString(s6) != 143) {
			System.out.println("s6 failed");
			return false;
		}
		return true;
	}

	private static boolean countWordsInFileTests() {
		File file1 = new File(".\\src\\CountWordsInTextFile\\Tests\\test1.txt");
		File file2 = new File(".\\src\\CountWordsInTextFile\\Tests\\test2.txt");
		File file3 = new File(".\\src\\CountWordsInTextFile\\Tests\\test3.txt");
		File file4 = new File(".\\src\\CountWordsInTextFile\\Tests\\test4.txt");

		if (FileWordCounter.countWordsInFile(file1) != 143) {
			return false;
		}
		if (FileWordCounter.countWordsInFile(file2) != 26) {
			return false;
		}
		if (FileWordCounter.countWordsInFile(file3) != 9150) {
			return false;
		}
		if (FileWordCounter.countWordsInFile(file4) != 0) {
			return false;
		}
		return true;
	}
}
