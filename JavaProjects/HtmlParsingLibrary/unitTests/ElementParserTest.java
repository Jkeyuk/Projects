package unitTests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import htmlParser.ElementParser;

/**
 * Test case to test ElementParser class.
 *
 */
class ElementParserTest {

	/**
	 * Test getAttributes with element that does not have a closing tag.
	 */
	@Test
	void testGetAttributesNoClosingTag() {
		String element = "<img src=\"smiley.gif\" alt=\"Smiley face\" height=\"32\" width=\"42\"/>";
		HashMap<String, String> attributes = ElementParser.getAttributes(element);
		assertEquals(4, attributes.size());
		assertEquals("smiley.gif", attributes.get("src"));
		assertEquals("Smiley face", attributes.get("alt"));
		assertEquals("32", attributes.get("height"));
		assertEquals("42", attributes.get("width"));
	}

	/**
	 * Test getAttributes with element that has a closing tag.
	 */
	@Test
	void testGetAttributesWithClosingTag() {
		String element = "<i id=\"businessIcon\" class=\"material-icons md-48\">business</i>";
		HashMap<String, String> attributes = ElementParser.getAttributes(element);
		assertEquals(2, attributes.size());
		assertEquals("material-icons md-48", attributes.get("class"));
		assertEquals("businessIcon", attributes.get("id"));
	}

	/**
	 * Test getAttributes with nested elements, assert only the attributes from
	 * the outer tag are parsed.
	 */
	@Test
	void testGetAttributesNestedElements() {
		String element = "<div class=\"project\"><p>BUDGET APP</p>"
				+ "<i class=\"material-icons md-48\">business</i>" + "</div>";
		HashMap<String, String> attributes = ElementParser.getAttributes(element);
		assertEquals(1, attributes.size());
		assertEquals("project", attributes.get("class"));
	}

	/**
	 * Test getAttributes with attributes that use single quotes.
	 * 
	 */
	@Test
	void testGetAttributesSingleQuotes() {
		String element = "<button class='button' id='SkillsButton'>About Me</button>";
		HashMap<String, String> attributes = ElementParser.getAttributes(element);
		assertEquals(2, attributes.size());
		assertEquals("button", attributes.get("class"));
		assertEquals("SkillsButton", attributes.get("id"));
	}

	/**
	 * Test getAttributes with attributes that use double quotes.
	 * 
	 */
	@Test
	void testGetAttributesDoubleQuotes() {
		String element = "<button class=\"button\" id=\"SkillsButton\">About Me</button>";
		HashMap<String, String> attributes = ElementParser.getAttributes(element);
		assertEquals(2, attributes.size());
		assertEquals("button", attributes.get("class"));
		assertEquals("SkillsButton", attributes.get("id"));
	}

	/**
	 * Test getAttributes with attributes that use Unquoted syntax.
	 * 
	 */
	@Test
	void testGetAttributesUnQuoted() {
		String element = "<button class=button id=SkillsButton>About Me</button>";
		HashMap<String, String> attributes = ElementParser.getAttributes(element);
		assertEquals(2, attributes.size());
		assertEquals("button", attributes.get("class"));
		assertEquals("SkillsButton", attributes.get("id"));
	}

	/**
	 * Test getAttributes with attributes that use empty attribute syntax
	 * 
	 */
	@Test
	void testGetAttributesEmptyAttribute() {
		String element = "<button class='' id=\"\" disabled hidden>About Me</button>";
		HashMap<String, String> attributes = ElementParser.getAttributes(element);
		assertEquals(4, attributes.size());
		assertEquals("", attributes.get("class"));
		assertEquals("", attributes.get("id"));
		assertEquals("", attributes.get("disabled"));
		assertEquals("", attributes.get("hidden"));
	}

	/**
	 * Test getAttributes with attributes that use each syntax style but have
	 * been spaced out.
	 * 
	 */
	@ParameterizedTest
	@ValueSource(strings = {
			"<button class   =   button   id   =   SkillsButton  hidden>About Me</button>",
			"<button   class   =   \"  button  \"    id   =    \"SkillsButton\"   hidden  >About Me</button>",
			"<button class   =   '  button  '    hidden     id   =    'SkillsButton'   >About Me</button>" })
	void testGetAttributesSpacedOut(String element) {
		HashMap<String, String> attributes = ElementParser.getAttributes(element);
		assertEquals(3, attributes.size());
		assertEquals("button", attributes.get("class"));
		assertEquals("SkillsButton", attributes.get("id"));
		assertEquals("", attributes.get("hidden"));
	}

	/**
	 * Test getAttributes with attributes with null, empty strings, and
	 * malformed elements.
	 * 
	 */
	@ParameterizedTest
	@ValueSource(strings = { "", "'    '", "''", "fadfadfadaf" })
	void testGetAttributesInvalidInputs(String element) {
		HashMap<String, String> attributes = ElementParser.getAttributes(element);
		assertTrue(attributes.isEmpty());
	}
}
