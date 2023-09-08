package mod.elfilibustero.sketch.lib.utils;

public class StringEscapeUtil {
	public static String escapeXML(String input) {
		if (input == null) {
			return null;
		}
		StringBuilder output = new StringBuilder();
		for (int ii = 0; ii < input.length(); ii++)  {
			char c = input.charAt(ii);
			switch (c) {
				case '&' -> output.append("&amp;");
				case '<' -> output.append("&lt;");
				case '>' -> output.append("&gt;");
				case '"' -> output.append("\\\"");
				case '\'' -> output.append("\\\'");
				default -> output.append(c);
			}
		}
		return output.toString();
	}

	public static String unescapeXML(String input) {
		if (input == null) {
			return null;
		}
		return input.replace("&amp;", "&")
			.replace("&lt;", "<")
			.replace("&gt;", ">")
			.replace("\\\"", "\"")
			.replace("\\\'", "\'");
	}
}
