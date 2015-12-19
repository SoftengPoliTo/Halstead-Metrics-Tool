package specksboy.halstead.metrics;

public class KeywordAnalyzer {
	public void analyzeKeywords() {
		switch (Tokenizer.fileType.toLowerCase()) {
		case "java":
			for (int i = 0; i < Tokenizer.getInstance().tokens.size(); i++) {
				if (Java8Keywords.isKeyword(Tokenizer.getInstance().tokens.get(i).toString())) {
					Operators.getInstance().insert(Tokenizer.getInstance().tokens.get(i).toString());
				} else if (Tokenizer.getInstance().tokens.get(i).toString().charAt(0) == '"') {
					Operands.getInstance().insert(Tokenizer.getInstance().tokens.get(i).toString());
				}
			}
			break;
		case "c":
			for (int i = 0; i < Tokenizer.getInstance().tokens.size(); i++) {
				if (CKeywords.isKeyword(Tokenizer.getInstance().tokens.get(i).toString())) {
					Operators.getInstance().insert(Tokenizer.getInstance().tokens.get(i).toString());
				} else if (Tokenizer.getInstance().tokens.get(i).toString().charAt(0) == '"') {
					Operands.getInstance().insert(Tokenizer.getInstance().tokens.get(i).toString());
				}
			}
			break;
		case "cpp":
			for (int i = 0; i < Tokenizer.getInstance().tokens.size(); i++) {
				if (CPP14Keywords.isKeyword(Tokenizer.getInstance().tokens.get(i).toString())) {
					Operators.getInstance().insert(Tokenizer.getInstance().tokens.get(i).toString());
				} else if (Tokenizer.getInstance().tokens.get(i).toString().charAt(0) == '"') {
					Operands.getInstance().insert(Tokenizer.getInstance().tokens.get(i).toString());
				}
			}
			break;
		}
	}
}
