package EnglishToPigLatin;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnglishToPigLatin {

	private final String STRING;

	public EnglishToPigLatin(String STRING) {
		this.STRING = STRING;
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the message to translate:");
		EnglishToPigLatin x = new EnglishToPigLatin(scan.nextLine());
		System.out.println("The message in pig latin is as follows:");
		System.out.println(x.translate());
		scan.close();
	}

	// returns a string that has been translated to pig Latin.
	private String translate() {
		String returnString = "";
		for (String w : STRING.split(" ")) {
			returnString += convertToPigLating(removePunctuation(w)) + " ";
		}
		return returnString;
	}

	// converts a single word to pig Latin. returns string.
	private String convertToPigLating(String s) {
		Matcher m =  Pattern.compile("[aeiouAEIOU]").matcher(s);
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

	// removes punctuation from a single word, returns word as string.
	private String removePunctuation(String s) {
		Pattern punctuation = Pattern.compile("\\p{Punct}");
		String newString = punctuation.matcher(s).replaceAll(" ");
		return newString.trim();
	}
}
