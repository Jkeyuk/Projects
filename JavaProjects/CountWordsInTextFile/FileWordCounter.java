package CountWordsInTextFile;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileWordCounter {

	/**
	 * returns the number of words in a given text file. words are any sequence
	 * of characters or numbers, words jointed with apostrophes count as one
	 * word, "It's" would be one word.
	 * 
	 * @return returns number of words in a given text file.
	 */
	protected static int countWordsInFile(File file) {
		int count = 0;
		try {
			List<String> linesFromFile = Files.readAllLines(file.toPath());
			for (String string : linesFromFile) {
				count += countWordsInString(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * returns the number of words in a string, words are any sequence of
	 * characters or numbers, words jointed with apostrophes count as one word,
	 * "It's" would be one word.
	 * 
	 * @param string
	 *            - string to count.
	 * @return - number of words in the string as an integer.
	 */
	protected static int countWordsInString(String string) {
		Pattern wordPattern = Pattern.compile("\\b\\w+\\p{Punct}*\\w*\\p{Punct}*\\b");
		Matcher m = wordPattern.matcher(string);
		int count = 0;
		while (m.find()) {
			count++;
		}
		return count;
	}
}
