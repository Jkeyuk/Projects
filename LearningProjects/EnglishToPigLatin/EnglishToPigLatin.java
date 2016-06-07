package englishtopiglatin;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnglishToPigLatin {

    private final String STRING;

    public EnglishToPigLatin(String STRING) {
        this.STRING = STRING;
    }

    public void translate() {
        String[] arrayOfWords = STRING.split(" ");
        for (String w : arrayOfWords) {
            String stringMinusPunct = removePunctuation(w);
            System.out.print(convertToPigLating(stringMinusPunct) + " ");
        }
    }

    private String convertToPigLating(String s) {
        String newPigString;
        Pattern vowels = Pattern.compile("[aeiouAEIOU]");
        Matcher m = vowels.matcher(s);
        if (m.find()) {
            int vowelIndex = m.start();
            if (vowelIndex > 0) {
                String startOfString = s.substring(vowelIndex);
                String endOfString = s.substring(0, vowelIndex) + "ay";
                newPigString = startOfString + endOfString;
                return newPigString;
            } else {
                return s + "yay";
            }
        } else {
            return s;
        }
    }

    private String removePunctuation(String s) {
        Pattern punctuation = Pattern.compile("\\p{Punct}");
        Matcher m = punctuation.matcher(s);
        String newString = m.replaceAll(" ");
        return newString.trim();
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the message to translate:");
        String input = scan.nextLine();
        EnglishToPigLatin x = new EnglishToPigLatin(input);
        System.out.println("The message in pig latin is as follows:");
        x.translate();
        System.out.println("");
    }
}
