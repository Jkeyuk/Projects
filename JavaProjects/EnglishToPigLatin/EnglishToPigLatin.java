package EnglishToPigLatin;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnglishToPigLatin {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the message to translate:");
		System.out.println("Translating..." + System.lineSeparator()
				+ new EnglishToPigLatin().translate(scan.nextLine()));
		scan.close();
	}

	/**
	 * Converts and returns a given string into pig Latin.
	 * 
	 * @param s
	 *            - string to convert.
	 * @return - string translated to pig Latin.
	 */
	private String translate(String s) {
		String returnString = "";
		for (String words : s.split(" ")) {
			returnString += convertToPigLating(removePunctuation(words)) + " ";
		}
		return returnString;
	}

	/**
	 * Converts a given word to pig Latin and returns it as a string.
	 * 
	 * @param s
	 *            - Word to convert.
	 * @return - word converted to pig Latin as string.
	 */
	private String convertToPigLating(String s) {
		Matcher m = Pattern.compile("[aeiouAEIOU]").matcher(s);
		if (m.find()) {
			if (m.start() > 0) {
				return s.substring(m.start()) + s.substring(0, m.start()) + "ay";
			} else {
				return s + "yay";
			}
		} else {
			return s;
		}
	}

	/**
	 * Removes punctuation from a given word, and returns it as a string.
	 * 
	 * @param word
	 *            - word to remove punctuation from.
	 * @return new word as string.
	 */
	private String removePunctuation(String word) {
		Pattern punctuation = Pattern.compile("\\p{Punct}");
		String newString = punctuation.matcher(word).replaceAll("");
		return newString.trim();
	}
}
