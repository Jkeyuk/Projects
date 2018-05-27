package unitTests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import htmlParser.ElementParser;

/**
 * Tests to evaluate the integrity of the ElementParser Class.
 *
 */
class ElementParserTest {

	/**
	 * Tests to evaluate the getAttributes method. focusing on elements with and
	 * without closing tags and nested elements. Method in test will return a
	 * hash map of strings representing the attributes of the element.
	 */
	@Test
	void testGetAttributes() {
		// Simple  element with one attribute and and no closing tag
		String element1 = "<meta charset='utf-8'>";
		HashMap<String, String> attributes1 = ElementParser.getAttributes(element1);
		assertEquals(1, attributes1.size());
		assertEquals("utf-8", attributes1.get("charset"));
		// Malformed element with closing tag and two attributes
		String element2 = "<button class=      \"button\" id=\"SkillsButton\">About Me</button>";
		HashMap<String, String> attributes2 = ElementParser.getAttributes(element2);
		assertEquals(2, attributes2.size());
		assertEquals("button", attributes2.get("class"));
		assertEquals("SkillsButton", attributes2.get("id"));
		// Element with no closing tag and 4 attributes
		String element3 = "<img src=\"smiley.gif\" alt=\"Smiley face\" height=\"42\" width=\"42\">";
		HashMap<String, String> attributes3 = ElementParser.getAttributes(element3);
		assertEquals(4, attributes3.size());
		assertEquals("smiley.gif", attributes3.get("src"));
		assertEquals("Smiley face", attributes3.get("alt"));
		assertEquals("42", attributes3.get("height"));
		assertEquals("42", attributes3.get("width"));
		// Element with nested element
		String element4 = "<div class=\"project\">\r\n"
				+			 "                    <p>BUDGET APP</p>\r\n"
				+			 "                    <i class=\"material-icons md-48\">business</i>\r\n"
				+ 		"                </div>";
		HashMap<String, String> attributes4 = ElementParser.getAttributes(element4);
		assertEquals(1, attributes4.size());
		assertEquals("project", attributes4.get("class"));
	}
}
