package sandbox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XMLStaxParse {

    public static void main(String[] args)
            throws FileNotFoundException, XMLStreamException {
        //create file input stream
        FileInputStream filee = new FileInputStream(
                "C:\\Users\\Public\\JavascriptApps\\Temp\\Netbeans\\test.xml");
        //create input factory
        XMLInputFactory factory = XMLInputFactory.newFactory();
        //create stream reader
        XMLStreamReader xmlReader = factory.createXMLStreamReader(filee);

        //iterate cursor through xml document
        while (xmlReader.hasNext()) {
            //if a starting tag do
            if (xmlReader.isStartElement()) {
                System.out.print(xmlReader.getName() + ": ");
                //if a text node which is not empty white space do
            } else if (xmlReader.isCharacters() && !xmlReader.isWhiteSpace()) {
                System.out.println(xmlReader.getText().trim());
            }//move cursor forward with next()
            xmlReader.next();
        }
    }
}
