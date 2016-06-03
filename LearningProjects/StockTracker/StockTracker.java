package stocktracker;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class StockTracker {

    //public method to search for company information
    public void searchForCompanyInfo(String companyName) {
        String[] array = companyName.split(" ");
        String cName = "";
        for (String string : array) {
            cName += string;
        }
        parseCompanyInfo(cName.trim());
    }

    //public method to get stock quote
    public void getStockQuote(String symb) {
        String[] array = symb.split(" ");
        String cName = "";
        for (String string : array) {
            cName += string;
        }
        parseQuoteData(cName.trim());
    }

    private void parseCompanyInfo(String companyName) {
        //get root of document
        Element root = getData("Lookup?input=", companyName);
        //get list of results
        NodeList nodes = root.getElementsByTagName("LookupResult");
        //make sure nodelist is not null
        if (nodes != null && nodes.getLength() > 0) {
            for (int i = 0; i < nodes.getLength(); i++) {
                Node n = nodes.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    //cast node to element
                    Element e = (Element) n;
                    //get company name
                    String Name
                            = e.getElementsByTagName("Name").item(0).getTextContent().trim();
                    //get company symbol
                    String symbol
                            = e.getElementsByTagName("Symbol").item(0).getTextContent().trim();
                    //get company exchange
                    String exchange
                            = e.getElementsByTagName("Exchange").item(0).getTextContent().trim();
                    //display results
                    displayCompanyInfo(Name, symbol, exchange);
                }
            }
        } else {
            System.out.println("Search could not find anything matching that name");
            System.out.println("Please try again with different name");
        }
    }

    private void parseQuoteData(String symb) {
        try {
            //get root element of document
            Element e = getData("Quote?symbol=", symb);
            //get company name
            String Name
                    = e.getElementsByTagName("Name").item(0).getTextContent().trim();
            //get company last price
            String lastPrice
                    = e.getElementsByTagName("LastPrice").item(0).getTextContent().trim();
            //get company change in price
            String change
                    = e.getElementsByTagName("Change").item(0).getTextContent().trim();
            //get company selling high for trading session
            String high
                    = e.getElementsByTagName("High").item(0).getTextContent().trim();
            //get company low for trading session
            String low
                    = e.getElementsByTagName("Low").item(0).getTextContent().trim();
            //get company opening price
            String open
                    = e.getElementsByTagName("Open").item(0).getTextContent().trim();
            //display results
            displayQuoteData(Name, lastPrice, change, high, low, open);
        } catch (NullPointerException e) {
            System.out.println("This ticker symbol is not correct");
            System.out.println("Please use company search function for proper symbol name");
        }
    }

    private Element getData(String searchType, String queryInput) {
        Element root = null;
        try {//create url object with address 
            URL url = new URL("http://dev.markitondemand.com/Api/v2/" + searchType + queryInput);
            //build document factory
            DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
            //make document builder
            DocumentBuilder build = fac.newDocumentBuilder();
            //build document with builder and url stream
            Document doc = build.parse(url.openStream());
            //normalize document
            doc.getDocumentElement().normalize();
            //extract root element from document
            root = doc.getDocumentElement();
        } catch (IOException ex) {
            Logger.getLogger(StockTracker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException | SAXException ex) {
            Logger.getLogger(StockTracker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return root;
    }

    private void displayQuoteData(
            String name, String lastPrice, String change, String high, String low, String open) {
        System.out.println("");
        System.out.println("Company name: " + name);
        System.out.println("Last price of company's stock: " + lastPrice);
        System.out.println("Change in price since previous days close: " + change);
        System.out.println("High price of stock in trading session: " + high);
        System.out.println("Low price of stock in trading session: " + low);
        System.out.println("Opening price at start of trading session: " + open);
    }

    private void displayCompanyInfo(String name, String symbol, String exchange) {
        System.out.println("");
        System.out.println("Company name: " + name);
        System.out.println("Company stock ticker: " + symbol);
        System.out.println("Company exchange: " + exchange);
    }
}
