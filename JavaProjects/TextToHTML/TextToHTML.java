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

	public void makeHtmlFile() {
		File newFile = new File(SOURCE_DOC.getParentFile().getAbsolutePath()
				+ File.separator + getFileName() + ".html");
		String htmlDocument = buildHTML();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {
			writer.write(htmlDocument);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private String buildHTML() {
		Object[] fileData = readFile();
		String htmlHead = "<!DOCTYPE html><html>" + "<head><title>" + 
				getFileName() + "</title>" + "<meta charset='utf-8'></head>";
		String htmlBody = "<body>";
		for (Object string : fileData) {
			if (string.toString().length() > 0) {
				htmlBody += "<p>" + string.toString() + "</p>";
			}
		}
		htmlBody += "</body></html>";
		return htmlHead + htmlBody;
	}

	private Object[] readFile() {
		Object[] data = null;
		try {
			data = Files.lines(SOURCE_DOC.toPath()).toArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	private String getFileName() {
		return SOURCE_DOC.getName().split("\\.")[0];
	}
}
