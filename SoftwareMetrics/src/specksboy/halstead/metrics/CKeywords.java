package specksboy.halstead.metrics;

public class CKeywords {
	private static String[] keywords = { "auto", "break", "case", "char", "const", "continue", "default", "do",
			"double", "else", "enum", "extern", "float", "for", "goto", "if", "int", "long", "register", "return",
			"short", "signed", "sizeof", "static", "struct", "switch", "typedef", "union", "unsigned", "void",
			"volatile", "while" };

	private CKeywords() {

	}

	public static int size() {
		return keywords.length;
	}

	public static boolean isKeyword(String keyword) {
		boolean result = false;
		for (int i = 0; i < size(); ++i) {
			if (keywords[i].equals(keyword)) {
				result = true;
				break;
			}
		}
		return result;
	}
}
