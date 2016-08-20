package wordcounter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WordCounter {

    private final String fileLocation;

    public WordCounter(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public void count() {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileLocation)))) {
            String input;
            String data = "";
            while ((input = reader.readLine()) != null) {
                data += input + " ";
            }
            String[] arrayOfWords = data.split(" ");
            System.out.println("Number of words = " + arrayOfWords.length);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WordCounter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WordCounter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
