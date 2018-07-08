package unitTests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import htmlParser.HtmlParser;

/**
 * Tests to evaluate the integrity of the HtmlParser Class.
 *
 */
class HtmlParserTest {

	private String testHtmlNoClosingTag = "<head><meta charset=\"utf-8\">"
			+ "<link href=\"index.css\" rel=\"stylesheet\" type=\"text/css\">"
			+ "<img src=\"/media/examples/frog.png\"/>"
			+ "<area shape=\"rect\" coords='03344126' href=\"sun.htm\" alt=\"Sun\">"
			+ "<input type=\"text\" name=\"fname\">" + "<br></head>";

	/**
	 * Test getElements parsing nested elements with div 1 containing divs 2 and
	 * 3.
	 */
	@Test
	void testGetElementsNested() {
		String html = "<div id='1'><div id='2'></div><div id='3'></div></div>";
		List<String> elements = HtmlParser.getElements("div", html);
		assertTrue(elements.contains("<div id='1'><div id='2'></div><div id='3'></div></div>"));
		assertTrue(elements.contains("<div id='2'></div>"));
		assertTrue(elements.contains("<div id='3'></div>"));
		assertTrue(elements.size() == 3);
	}

	/**
	 * Test getElements parsing nested elements with div 1 containing div 2
	 * which contains div 3.
	 */
	@Test
	void testGetElementsNested2() {
		String html = "<div id='1'><div id='2'><div id='3'></div></div></div>";
		List<String> elements = HtmlParser.getElements("div", html);
		assertTrue(elements.contains("<div id='1'><div id='2'><div id='3'></div></div></div>"));
		assertTrue(elements.contains("<div id='2'><div id='3'></div></div>"));
		assertTrue(elements.contains("<div id='3'></div>"));
		assertTrue(elements.size() == 3);
	}

	/**
	 * Test getElements parsing elements without closing tags.
	 */
	@ParameterizedTest
	@CsvSource({ "meta, <meta charset=\"utf-8\">",
			"link, <link href=\"index.css\" rel=\"stylesheet\" type=\"text/css\">", "br, <br>",
			"img, <img src=\"/media/examples/frog.png\"/>",
			"area, <area shape=\"rect\" coords='03344126' href=\"sun.htm\" alt=\"Sun\">",
			"input, <input type=\"text\" name=\"fname\">" })
	void testGetElementsNoClosingTag(String tag, String expected) {
		List<String> metaEles = HtmlParser.getElements(tag, testHtmlNoClosingTag);
		assertTrue(metaEles.size() == 1);
		assertTrue(metaEles.contains(expected));
	}

	/**
	 * Test getElements parsing elements that do not exsist in html.
	 */
	@ParameterizedTest
	@ValueSource(strings = { "", "''", "'   '", "dfadfadf" })
	void testGetElementsInvalidTags(String tag) {
		List<String> metaEles = HtmlParser.getElements(tag, testHtmlNoClosingTag);
		assertTrue(metaEles.isEmpty());
	}

	/**
	 * Test getElements with null inputs.
	 */
	@Test
	void testGetElementsNullInputs() {
		List<String> metaEles = HtmlParser.getElements(null, testHtmlNoClosingTag);
		assertTrue(metaEles.isEmpty());
		metaEles = HtmlParser.getElements("img", null);
		assertTrue(metaEles.isEmpty());
	}
}
