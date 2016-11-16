package StockTracker;

import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class StockTracker {

	// displays company quote data to terminal
	public void displayQuoteData(String symbol) {
		String[] data = parseQuoteData(symbol);
		System.out.println("");
		System.out.println("Company name: " + data[0]);
		System.out.println("Last price of company's stock: " + data[1]);
		System.out.println("Change in price since previous days close: " + data[2]);
		System.out.println("High price of stock in trading session: " + data[3]);
		System.out.println("Low price of stock in trading session: " + data[4]);
		System.out.println("Opening price at start of trading session: " + data[5]);
	}

	// displays company info to terminal
	public void displayCompanyInfo(String cName) {
		ArrayList<String[]> data = parseCompanyData(cName);
		for (String[] strings : data) {
			System.out.println("");
			System.out.println("Company name: " + strings[0]);
			System.out.println("Company stock ticker: " + strings[1]);
			System.out.println("Company exchange: " + strings[2]);
		}
	}

	// returns array with company name, symbol, and exchange.
	private ArrayList<String[]> parseCompanyData(String companyName) {
		ArrayList<String[]> data = new ArrayList<>();
		Element root = requestData("Lookup?input=", companyName);
		NodeList nodes = root.getElementsByTagName("LookupResult");
		for (int i = 0; i < nodes.getLength(); i++) {
			Node n = nodes.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				String[] nodeItems = new String[3];
				Element e = (Element) n;
				nodeItems[0] = e.getElementsByTagName("Name").item(0).getTextContent().trim();
				nodeItems[1] = e.getElementsByTagName("Symbol").item(0).getTextContent().trim();
				nodeItems[2] = e.getElementsByTagName("Exchange").item(0).getTextContent().trim();
				data.add(nodeItems);
			}
		}
		return data;
	}

	// returns array with company stock quote info.
	private String[] parseQuoteData(String symb) {
		String[] data = new String[6];
		Element e = requestData("Quote?symbol=", symb);
		data[0] = e.getElementsByTagName("Name").item(0).getTextContent().trim();
		data[1] = e.getElementsByTagName("LastPrice").item(0).getTextContent().trim();
		data[2] = e.getElementsByTagName("Change").item(0).getTextContent().trim();
		data[3] = e.getElementsByTagName("High").item(0).getTextContent().trim();
		data[4] = e.getElementsByTagName("Low").item(0).getTextContent().trim();
		data[5] = e.getElementsByTagName("Open").item(0).getTextContent().trim();
		return data;
	}

	// returns data requested from server.
	private Element requestData(String searchType, String queryInput) {
		Element root = null;
		try {
			URL url = new URL("http://dev.markitondemand.com/Api/v2/" + searchType + queryInput);
			DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = fac.newDocumentBuilder();
			Document doc = build.parse(url.openStream());
			doc.getDocumentElement().normalize();
			root = doc.getDocumentElement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return root;
	}
}
