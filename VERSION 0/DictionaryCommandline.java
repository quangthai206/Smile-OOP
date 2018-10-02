package dictionary;

public class DictionaryCommandline {
	public void showAllWords(Dictionary dic) {
		System.out.printf("%-6s%c %-15s%c %-20s%n","No", '|' ,"English", '|', "Vietnamese");
		for(int i=0; i<dic.words.size(); i++) {
			System.out.printf("%-6d%c %-15s%c %-15s%n",i+1,'|',dic.getWord(i).getWord_target(),'|',dic.getWord(i).getWord_explain());
		}
	}
	
	public void dictionaryBasic() {
		Dictionary dic = new Dictionary();
		new DictionaryManagement().insertFromCommandline(dic);
		dic.sortDic();
		System.out.println("DICTIONARY: ");
		showAllWords(dic);
	}
}
