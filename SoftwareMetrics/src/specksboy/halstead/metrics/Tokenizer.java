package specksboy.halstead.metrics;

import java.io.IOException;
import java.util.ArrayList;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.apache.commons.io.FilenameUtils;

public class Tokenizer {
	public ArrayList<String> tokens;
	protected static Tokenizer instance;
	public static String fileType = null;

	private Tokenizer() {
		this.tokens = new ArrayList<String>();
	}

	public static Tokenizer getInstance() {
		if (instance == null) {
			instance = new Tokenizer();
		}
		return instance;
	}

	public void tokenize(String fileURL) throws IOException {
		TokenStream token = null;
		ANTLRFileStream stream = new ANTLRFileStream(fileURL);
		fileType = FilenameUtils.getExtension(fileURL);
		switch (fileType.toLowerCase()) {
		case "c":
			CLexer clexer = new CLexer(stream);
			CommonTokenStream ctokenStream = new CommonTokenStream(clexer);
			CParser cparser = new CParser(ctokenStream);
			cparser.compilationUnit();
			token = cparser.getTokenStream();
			break;
		case "cpp":
			CPP14Lexer cpplexer = new CPP14Lexer(stream);
			CommonTokenStream cpptokenStream = new CommonTokenStream(cpplexer);
			CPP14Parser cppparser = new CPP14Parser(cpptokenStream);
			cppparser.translationunit();
			token = cppparser.getTokenStream();
			break;
		case "java":
			Java8Lexer jlexer = new Java8Lexer(stream);
			CommonTokenStream jtokenStream = new CommonTokenStream(jlexer);
			Java8Parser jparser = new Java8Parser(jtokenStream);
			jparser.compilationUnit();
			token = jparser.getTokenStream();
			break;
		}
		for (int i = 0; i < token.size(); i++) {
			this.tokens.add(token.get(i).getText().toString());
		}
	}
}
