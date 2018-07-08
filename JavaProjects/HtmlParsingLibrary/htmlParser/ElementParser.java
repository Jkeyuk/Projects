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
	 * Returns a hash map of attributes from a given element with corresponding
	 * key: value pairs. Empty attributes have a value that is an empty string.
	 * 
	 * @param element
	 *            - Element to parse
	 * @return - HashMap of attributes with corresponding key: value pairs.
	 */
	public static HashMap<String, String> getAttributes(String element) {
		HashMap<String, String> attributes = new HashMap<>();
		if (element == null) return attributes;
		Matcher outerTagMatcher = Pattern.compile("<[\\s\\S]+?>").matcher(element);
		if (outerTagMatcher.find()) {
			String outerTag = outerTagMatcher.group();
			String attrPattern = "\\s\\w+(\\s*=\\s*((['\"][\\s\\S]*?['\"])|[^'\"=><`\\s]+))?";
			Matcher attrMatcher = Pattern.compile(attrPattern).matcher(outerTag);
			while (attrMatcher.find()) {
				String attribute = attrMatcher.group();
				int delimeter = attribute.indexOf('=');
				if (delimeter != -1) {
					String key = attribute.substring(0, delimeter);
					String value = attribute.substring(delimeter + 1).replaceAll("['\"]", "");
					attributes.put(key.trim(), value.trim());
				} else {
					attributes.put(attribute.trim(), "");
				}
			}
		}
		return attributes;
	}
}
