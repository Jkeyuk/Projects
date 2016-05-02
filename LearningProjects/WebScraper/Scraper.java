package webscraper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {

    private final URL url;
    private final String html;

    public Scraper(String url) throws MalformedURLException, IOException {
        //create url object with string as arg
        this.url = new URL(url);
        //get html from URL
        this.html = rawHTML();
    }

    //return raw HTML as string
    private String rawHTML() throws IOException {
        //String to be returned
        String returnString = "";
        //try to get input stream and read lines to String
        BufferedReader in = new BufferedReader(new InputStreamReader(
                url.openStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            returnString += " " + inputLine;
        }
        //return String
        return returnString;
    }

    //return array of links from the html
    private ArrayList<String> getLinks() {
        //array to be returned
        ArrayList<String> returnArray = new ArrayList<>();
        //build document object
        Document doc = Jsoup.parse(html);
        //get links from doc
        Elements links = doc.getElementsByTag("a");
        //add links to array
        for (Element link : links) {
            String linkText = link.attr("href");
            if (linkText.charAt(0) == '/') {
                returnArray.add("http://" + url.getHost() + linkText);
            } else {
                returnArray.add(linkText);
            }
        }//return array 
        return returnArray;
    }

    //save links to text file
    public void saveLinks() {
        //get links from website
        ArrayList<String> links = getLinks();
        //create file
        File file = new File(url.getHost() + ".txt");
        try {//write array of links to file
            try (FileWriter writer = new FileWriter(file)) {
                for (String link : links) {
                    writer.write(link + System.lineSeparator());
                }//flush buffer to file
                writer.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(Scraper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
