package CountWordsInTextFile;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class WordCounter {

	private final String fileLocation;

	public WordCounter(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public int count() {
		return getWordsFromFile().size();
	}

	private List<String> getWordsFromFile() {
		List<String> data = new LinkedList<>(Arrays.asList(fileDataToString().split(" ")));
		data.removeIf(w -> w.length() < 1);
		return data;
	}

	private String fileDataToString() {
		String dataString = "";
		try {
			List<String> data = Files.readAllLines(new File(fileLocation).toPath());
			for (String string : data) {
				dataString += string + " ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataString;
	}
}
