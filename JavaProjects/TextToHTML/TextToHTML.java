package texttohtml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextToHTML {

    private final String SOURCE_DOC;
    private final ArrayList<String> FILE_DATA;

    public TextToHTML(String sourceDocument) {
        this.SOURCE_DOC = sourceDocument;
        this.FILE_DATA = readFile();
    }

    private ArrayList<String> readFile() {
        ArrayList<String> data = new ArrayList<>();
        try {
            File sourceDoc = new File(SOURCE_DOC);
            BufferedReader reader = new BufferedReader(new FileReader(sourceDoc));
            String input;
            while ((input = reader.readLine()) != null) {
                data.add(input);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TextToHTML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TextToHTML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public void makeHtmlFile() {
        try {
            String pathName = new File(SOURCE_DOC).getParentFile().getAbsolutePath();
            pathName += File.separator + getFileName() + ".html";
            File newFile = new File(pathName);
            String htmlDocument = buildHTML();
            BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
            writer.write(htmlDocument);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(TextToHTML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String buildHTML() {
        String htmlHead = "<!DOCTYPE html><html>"
                + "<head><title>" + getFileName() + "</title>"
                + "<meta charset='utf-8'></head>";
        String htmlBody = "<body>";
        for (String string : FILE_DATA) {
            if (string.length() > 0) {
                htmlBody += "<p>" + string + "</p>";
            }
        }
        htmlBody += "</body></html>";
        String document = htmlHead + htmlBody;
        return document;
    }

    private String getFileName() {
        File sourceDoc = new File(SOURCE_DOC);
        String[] array = sourceDoc.getName().split("\\.");
        return array[0];
    }
}
