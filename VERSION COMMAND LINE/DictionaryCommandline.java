package dictionary;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class DictionaryCommandline {
	private Scanner sc = new Scanner(System.in);
	
	public void showAllWords(Dictionary dic) {
		System.out.printf("%-6s%c %-20s%c %-20s%n","No", '|' ,"English", '|', "Vietnamese");
		for(int i=0; i<dic.words.size(); i++) {
			System.out.printf("%-6d%c %-20s%c %-20s%n",i+1,'|',dic.getWord(i).getWord_target(),'|',dic.getWord(i).getWord_explain());
		}
	}
	
	public void dictionaryBasic() {
		Dictionary dic = new Dictionary();
		//new DictionaryManagement().insertFromCommandline(dic);
		new DictionaryManagement().insertFromFile(dic);
		System.out.println("DICTIONARY: ");
		showAllWords(dic);
		new DictionaryManagement().dictionaryLookup(dic);
	}
	
	public void dictionaryAdvanced() {
		Scanner sc = new Scanner(System.in);
		Dictionary dic = new Dictionary();
		DictionaryManagement dm = new DictionaryManagement();
		dm.insertFromFile(dic);
		int choice;
		while(true) {
			System.out.println("+----------------------------+");
			System.out.println("|    ^ SMILE DICTIONARY ^    |");
			System.out.println("|                            |");
			System.out.println("|   1) Seach by word         |");
			System.out.println("|   2) Seach by prefix       |");
			System.out.println("|   3) Add new word          |");
			System.out.println("|   4) Modify word           |");
			System.out.println("|   5) Delete word           |");
			System.out.println("|   6) Show all words        |");
			System.out.println("|   7) Export to file        |");
			System.out.println("|   0) Exit                  |");
			System.out.println("|                            |");
			System.out.println("+----------------------------+");
			System.out.println("Enter your choice(0-6): ");
			choice = Integer.parseInt(sc.nextLine());
			switch(choice) {
			case 1:
				dm.dictionaryLookup(dic);
				break;
			case 2:
				dictionarySeacher(dic);
				break;
			case 3:
				dm.addWord(dic);
				break;
			case 4:
				dm.modifyWord(dic);
				break;
			case 5:
				dm.deleteWord(dic);
				break;
			case 6:
				showAllWords(dic);
				break;
			case 7:
				try {
					dm.dictionaryExportToFile(dic);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				break;
			case 0:
				System.out.println("Exit!");
				sc.close();
				return;
			default:
				System.out.println("Invalid choice. Please try again!");
			}
		}
	}
	public void dictionarySeacher(Dictionary dic) {
		System.out.println("Enter prefix: ");
		String prefix = sc.nextLine();
		int start = Collections.binarySearch(dic.words, new Word(prefix,null));
		if(start < 0) {
			start = -start - 1;
		}
		int end = start;
		while(end < dic.words.size() && dic.words.get(end).getWord_target().startsWith(prefix)) {
			end++;
		}
		if(start != end) {
			List<Word> prefixWords = dic.words.subList(start, end);
			System.out.printf("%-6s%c %-20s%c %-20s%n","No", '|' ,"English", '|', "Vietnamese");
			for(int i=0; i<prefixWords.size(); i++) {
				System.out.printf("%-6d%c %-20s%c %-20s%n",i+1,'|',prefixWords.get(i).getWord_target(),'|',prefixWords.get(i).getWord_explain());
			}
		}
		else {
			System.out.println("No matching prefix word!");
		}
	}
}
