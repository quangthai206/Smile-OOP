package dictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Dictionary {
	ArrayList<Word> words = new ArrayList<Word>();
	
	public void addWord(Word w) {
		words.add(w);
	}
	
	public Word getWord(int i) {
		return words.get(i);
	}
	
	public void sortDic() {
		Collections.sort(words, new Comparator<Word>() {
			@Override
			public int compare(Word o1, Word o2) {
				return o1.getWord_target().compareTo(o2.getWord_target());
			}		
		});
	}
}
