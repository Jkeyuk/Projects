HTML parsing library to extract elements and attributes from elements.

General usage:

Get elements:

ArrayList<String> elements = HtmlParser.getElements("div", html);

Returns a list of strings of the <div> elements of a given HTML string.

Get attributes from an element:

HashMap<String, String> attributes = ElementParser.getAttributes("<meta charset='utf-8'>");

attributes.get("charset") = "uft-8";

Will return a hash map of strings representing the attributes as key: value pairs.
