package htmlParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class with static methods to parse Html documents.
 *
 */
public class HtmlParser {

	/**
	 * Returns the HTML elements of a given HTML string corresponding with the
	 * given tag name.
	 * 
	 * @param tagName
	 *            - name of the elements to get
	 * @param html
	 *            - html of the document to parse
	 * @return - List of the elements parsed.
	 */
	public static List<String> getElements(String tagName, String html) {
		if (tagName == null || html == null) return new ArrayList<>();
		tagName = tagName.toLowerCase();
		if (hasClosingTag(tagName)) {
			List<Integer[]> openTags = getPositions("<" + tagName + "(\\s|>)", html);
			List<Integer[]> closingTags = getPositions("</" + tagName + ">", html);
			return buildStrings(html, sortNested(openTags, closingTags));
		} else {
			return buildStrings(html, getPositions("<" + tagName + "([\\s\\S]*?)>", html));
		}
	}

	/**
	 * Returns a list of substrings from a given string, built from the given
	 * list of integer arrays which contain the {start, end} indexes of each
	 * substring.
	 * 
	 * @param string
	 *            - string to build substrings strings from
	 * @param positions
	 *            - start and end positions of substrings
	 * @return - ArrayList of strings built from the html.
	 */
	private static List<String> buildStrings(String string, List<Integer[]> positions) {
		ArrayList<String> elements = new ArrayList<>();
		positions.forEach(p -> elements.add(string.substring(p[0], p[1])));
		return elements;
	}

	/**
	 * Returns a list of integer arrays containing the start and end index
	 * positions of the given opening and closing tags..
	 * 
	 * @param openTags
	 *            - start and end positions of a list of opening tags
	 * @param closeTags
	 *            - start and end positions of a list of closing tags
	 * @return final start and end positions of elements.
	 */
	private static List<Integer[]> sortNested(List<Integer[]> openTags, List<Integer[]> closeTags) {
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
	 * Returns a list of index arrays with the start and end index positions of
	 * a matched regular expression on a given string.
	 * 
	 * @param regEx
	 *            - expression to search for
	 * @param string
	 *            - string to search on.
	 * @return list of index arrays containing start and end points of each
	 *         match.
	 */
	private static List<Integer[]> getPositions(String regEx, String string) {
		Matcher matcher = Pattern.compile(regEx).matcher(string);
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
