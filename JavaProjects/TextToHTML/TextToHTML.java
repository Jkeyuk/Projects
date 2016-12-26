package TextToHTML;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class TextToHTML {

	private final File SOURCE_DOC;

	public TextToHTML(String sourceDocument) {
		this.SOURCE_DOC = new File(sourceDocument);
	}

	/**
	 * Creates an HTML file which displays the contents of the source document.
	 */
	public void makeHtmlFile() {
		File newFile = new File(SOURCE_DOC.getParentFile().getAbsolutePath()
				+ File.separator + getFileName(SOURCE_DOC) + ".html");
		String htmlDocument = buildHTML(getFileData(SOURCE_DOC));
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {
			writer.write(htmlDocument);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Takes the given lines from a file and writes them into a HTML String.
	 * 
	 * @param fileData
	 *            - lines from the file to build string
	 * @return - String representing the lines in HTML format.
	 */
	private String buildHTML(Object[] fileData) {
		String htmlHead = "<!DOCTYPE html><html>" + "<head><title>"
				+ getFileName(SOURCE_DOC) + "</title>" + "<meta charset='utf-8'></head>";
		String htmlBody = "<body>";
		for (Object string : fileData) {
			if (string.toString().length() > 0) {
				htmlBody += "<p>" + string.toString() + "</p>";
			}
		}
		htmlBody += "</body></html>";
		return htmlHead + htmlBody;
	}

	/**
	 * Returns the lines from a file as an Object[].
	 * 
	 * @param file-
	 *            file to read lines.
	 * @return lines of the file as an object[].
	 */
	private Object[] getFileData(File file) {
		Object[] data = null;
		try {
			data = Files.lines(file.toPath()).toArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Returns a given files name without the extension
	 * 
	 * @param file
	 *            - file name to return
	 * @return files name without extentsion.
	 */
	private String getFileName(File file) {
		return file.getName().split("\\.")[0];
	}
}
