package htmlParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class with static methods to parse Html documents.
 *
 */
public class HtmlParser {

	/**
	 * Returns the elements of a given html string corresponding with the given
	 * tag name.
	 * 
	 * @param tagName
	 *            - name of the elements to get
	 * @param html
	 *            - html of the document to parse
	 * @return - ArrayList of Strings of the elements parsed.
	 */
	public static ArrayList<String> getElements(String tagName, String html) {
		tagName = tagName.toLowerCase();
		if (hasClosingTag(tagName)) {
			ArrayList<Integer[]> openTags = getPositions("<" + tagName + "(\\s|>)", html);
			ArrayList<Integer[]> closingTags = getPositions("</" + tagName + ">", html);
			return buildStrings(html, sortNested(openTags, closingTags));
		} else {
			return buildStrings(html, getPositions("<" + tagName + "([\\s\\S]*?)>", html));
		}
	}

	/**
	 * Returns a list of substrings from the given index positions of a given
	 * html string.
	 * 
	 * @param html
	 *            - html to build strings from
	 * @param positions
	 *            - start and end positions
	 * @return - ArrayList of strings built from the html.
	 */
	private static ArrayList<String> buildStrings(String html, ArrayList<Integer[]> positions) {
		ArrayList<String> elements = new ArrayList<>();
		positions.forEach(p -> elements.add(html.substring(p[0], p[1])));
		return elements;
	}

	/**
	 * Returns the final index positions of nested or non nested elements
	 * represented by the given index poistions of the opening and closing tags.
	 * 
	 * @param openTags
	 *            - start and end positions of a list of opening tags
	 * @param closeTags
	 *            - start and end positions of a list of closing tags
	 * @return final positions of elements represented by the opening and
	 *         closing tags.
	 */
	private static ArrayList<Integer[]> sortNested(ArrayList<Integer[]> openTags,
			ArrayList<Integer[]> closeTags) {
		ArrayList<Integer[]> finalPositions = new ArrayList<>();
		if (openTags.size() == closeTags.size()) {
			Collections.reverse(openTags);
			while (!openTags.isEmpty() && !closeTags.isEmpty()) {
				Integer[] close = closeTags.get(0);
				Integer[] open = null;
				for (Integer[] integers : openTags) {
					open = integers;
					if (open[0] < close[0]) {
						Integer[] finalPos = { open[0], close[1] };
						finalPositions.add(finalPos);
						break;
					}
				}
				openTags.remove(open);
				closeTags.remove(close);
			}
		}
		return finalPositions;
	}

	/**
	 * Returns the start and end index positions of a given html string matched
	 * by the given regular expression
	 * 
	 * @param regEx
	 *            - expression to search for
	 * @param html
	 *            - html scan
	 * @return index positions containing start and end points of each match.
	 */
	private static ArrayList<Integer[]> getPositions(String regEx, String html) {
		Matcher matcher = Pattern.compile(regEx).matcher(html);
		ArrayList<Integer[]> positions = new ArrayList<>();
		while (matcher.find()) {
			Integer[] pos = { matcher.start(), matcher.end() };
			positions.add(pos);
		}
		return positions;
	}

	/**
	 * Returns true if the given tag name has a corresponding closing tag, false
	 * otherwise.
	 * 
	 * @param tagName
	 *            - element to check
	 * @return true if the given tag has a closing tag, false otherwise.
	 */
	private static boolean hasClosingTag(String tagName) {
		String[] noClosingTags = { "area", "base", "br", "col", "command", "embed", "hr", "img",
				"input", "keygen", "link", "menuitem", "meta", "param", "source", "track", "wbr" };
		return !Arrays.asList(noClosingTags).contains(tagName.toLowerCase());
	}
}
