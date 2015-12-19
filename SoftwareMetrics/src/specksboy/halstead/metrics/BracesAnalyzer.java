package specksboy.halstead.metrics;

public class BracesAnalyzer {
	String[][] braces={{"{","}"},{"(",")"},{"[","]"},{"<",">"}};
	public int countbraces(int index){
		int counter=0,startCount=0,endCount=0;
		for(int i=0;i<Tokenizer.getInstance().tokens.size();i++){
			if(Tokenizer.getInstance().tokens.get(i).toString().equals(this.braces[index][0])){
				++startCount;
			}
			else if(Tokenizer.getInstance().tokens.get(i).toString().equals(this.braces[index][1])){
				++endCount;
			}
		}
		if(startCount==endCount){
			counter=startCount;
		}
		return counter;
	}
	public void analyzeBraces(){
		for(int i=0;i<this.braces.length;i++){
			int count=this.countbraces(i);
			if(count>0)
				Operators.getInstance().insert(this.braces[i][0]+" "+this.braces[i][1],count);
		}
	}
}
