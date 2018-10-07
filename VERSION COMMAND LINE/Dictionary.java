package dictionary;

import java.util.ArrayList;
import java.util.Collections;

public class Dictionary {
	ArrayList<Word> words = new ArrayList<Word>();

	public void addWord(Word w) {
		words.add(w);
	}
	
	public void addWord(Word w, int i) {
		words.add(i,w);
	}
	
	public Word getWord(int i) {
		return words.get(i);
	}
	
	public void sortDic() {
		Collections.sort(words);
	}
}
