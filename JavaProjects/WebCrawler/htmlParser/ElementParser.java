package htmlParser;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class with static methods for parsing elements.
 *
 */
public class ElementParser {

	/**
	 * Returns hash map of attributes from a given element.
	 * 
	 * @param element
	 *            - Element to parse
	 * @return - HashMap of attributes with corresponding key: value pairs.
	 */
	public static HashMap<String, String> getAttributes(String element) {
		HashMap<String, String> attributes = new HashMap<>();
		Matcher firstTagMatcher = Pattern.compile("<[\\s\\S]+?>").matcher(element);
		if (firstTagMatcher.find()) {
			String frontTag = firstTagMatcher.group();
			Matcher attrMatcher = Pattern.compile("\\w+?=\\s*?['\"][\\s\\S]+?['\\\"]").matcher(frontTag);
			while (attrMatcher.find()) {
				String attribute = attrMatcher.group();
				int delimeter = attribute.indexOf('=');
				String key = attribute.substring(0, delimeter);
				String value = attribute.substring(delimeter + 1).replaceAll("['\"]", "");
				attributes.put(key, value.trim());
			}
		}
		return attributes;
	}
}
