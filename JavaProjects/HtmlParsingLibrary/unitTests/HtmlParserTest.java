package unitTests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import htmlParser.HtmlParser;

/**
 * Tests to evaluate the integrity of the HtmlParser Class.
 *
 */
class HtmlParserTest {

	/**
	 * HtmlParser.getElements test parsing nested elements with div 1 containing
	 * divs 2 and 3.
	 */
	@Test
	void testGetElements() {
		String html = "<div id='1'>\r\n" + 
									"  <div id='2'>\r\n" + 
									"  </div>\r\n" + 
									"  <div id='3'>\r\n" + 
									"  </div>\r\n" + 
								  "</div>";
		ArrayList<String> elements = HtmlParser.getElements("div", html);
		assertTrue(elements.contains("<div id='1'>\r\n" + 
															"  <div id='2'>\r\n" + 
															"  </div>\r\n" + 
															"  <div id='3'>\r\n" + 
															"  </div>\r\n" + 
								  							"</div>"));
		assertTrue(elements.contains("<div id='2'>\r\n" + 
															"  </div>"));
		assertTrue(elements.contains("<div id='3'>\r\n" + 
															"  </div>"));
		assertTrue(elements.size() == 3);
	}
	
	/**
	 * HtmlParser.getElements test parsing elements with div 1 containing div 2
	 * which contains div 3.
	 */
	@Test
	void test2GetElements() {
		String html = "<div id='1'>\r\n" + 
								  "  <div id='2'>\r\n" + 
								  "    <div id='3'>\r\n" + 
								  "    </div>\r\n" + 
								  "  </div>\r\n" + 
								  "</div>";
		ArrayList<String> elements = HtmlParser.getElements("div", html);
		assertTrue(elements.contains( "<div id='1'>\r\n" + 
														    "  <div id='2'>\r\n" + 
														    "    <div id='3'>\r\n" + 
														    "    </div>\r\n" + 
														    "  </div>\r\n" + 
															"</div>"));
		assertTrue(elements.contains( "<div id='2'>\r\n" + 
														    "    <div id='3'>\r\n" + 
														    "    </div>\r\n" + 
														    "  </div>"));
		assertTrue(elements.contains( "<div id='3'>\r\n" + 
														    "    </div>"));
		assertTrue(elements.size() == 3);
	}
	
	/**
	 * HtmlParser.getElements test parsing elements without closing tags.
	 */
	@Test
	void test3GetElements() {
		String html = "<head>\r\n" + 
									"  <meta charset=\"utf-8\">\r\n" + 
									"  <meta content=\"width=device-width, initial-scale=1.0\" name=\"viewport\">\r\n" + 
									"  <meta content=\"The web development portfolio of Jonathan Keyuk\" name=\"description\">\r\n" + 
									"  <link href=\"index.css\" rel=\"stylesheet\" type=\"text/css\">\r\n" + 
									"  <link href=\"index2.css\" rel=\"stylesheet\" type=\"text/css\">\r\n" + 
									"  <link href=\"index3.css\" rel=\"stylesheet\" type=\"text/css\">\r\n" + 
									"  <br>\r\n" + 
								"</head>";
		// meta tag tests
		ArrayList<String> metaEles = HtmlParser.getElements("meta", html);
		assertTrue(metaEles.size() == 3);
		assertTrue(metaEles.contains("<meta charset=\"utf-8\">"));
		assertTrue(metaEles.contains(
				"<meta content=\"width=device-width, initial-scale=1.0\" name=\"viewport\">"));
		assertTrue(metaEles.contains(
				"<meta content=\"The web development portfolio of Jonathan Keyuk\" name=\"description\">"));
		// link tag tests
		ArrayList<String> linkEles = HtmlParser.getElements("link", html);
		assertTrue(linkEles.size() == 3);
		assertTrue(linkEles.contains(
				"<link href=\"index.css\" rel=\"stylesheet\" type=\"text/css\">"));
		assertTrue(linkEles.contains(
				"<link href=\"index2.css\" rel=\"stylesheet\" type=\"text/css\">"));
		assertTrue(linkEles.contains(
				"<link href=\"index3.css\" rel=\"stylesheet\" type=\"text/css\">"));
		// single br tag tests
		ArrayList<String> brEle = HtmlParser.getElements("br", html);
		assertTrue(brEle.size() == 1);
		assertTrue(brEle.contains("<br>"));
	}
}
