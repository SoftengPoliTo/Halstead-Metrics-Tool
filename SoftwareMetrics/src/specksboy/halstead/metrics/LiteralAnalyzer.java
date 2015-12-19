package specksboy.halstead.metrics;

import java.util.ArrayList;
import java.util.Iterator;

public class LiteralAnalyzer {
	public void analyzeLiterals(){
		ArrayList<String> cpTokens=Tokenizer.getInstance().tokens;
		for(Iterator<String> iterator=cpTokens.iterator();iterator.hasNext();){
			String token=iterator.next().toString();
			if(Operators.getInstance().name.contains(token)){
				iterator.remove();
			}
			else if(Operands.getInstance().name.contains(token)){
				iterator.remove();
			}
			else if(token.equals("<EOF>")){
				iterator.remove();
			}
		}
		for(int i=0;i<cpTokens.size();i++){
			Operands.getInstance().insert(cpTokens.get(i).toString());
		}
	}
}
