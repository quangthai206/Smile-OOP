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
		DictionaryManagement dm = new DictionaryManagement();
		dm.insertFromFile();
		System.out.println("DICTIONARY: ");
		showAllWords(dm.getDic());
		System.out.println("Seach word: ");
		String word = sc.nextLine();
		dm.dictionaryLookup(word);
	}
	
	public void dictionaryAdvanced() {
		DictionaryManagement dm = new DictionaryManagement();
		dm.insertFromFile();
		int choice;
		String word;
		String mean;
		while(true) {
			System.out.println();
			System.out.println("+----------------------------+");
			System.out.println("|    ^ SMILE DICTIONARY ^    |");
			System.out.println("|                            |");
			System.out.println("|   1) Search by word        |");
			System.out.println("|   2) Search by prefix      |");
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
				System.out.println("Seach word: ");
				word = sc.nextLine();
				mean = dm.dictionaryLookup(word);
				if(mean != null) {
					System.out.println("Meaning: " + mean);
				}
				else {
					System.out.println("No exact match found for \"" + word + "\" in English");
				}
				break;
			case 2:
				dictionarySearcher(dm.getDic());
				break;
			case 3:
				System.out.println("Add word: ");
				word = sc.nextLine();
				System.out.println("Meaning: ");
				mean = sc.nextLine();
				dm.addWord(word, mean);
				break;
			case 4:
				System.out.println("Modify word: ");
				word = sc.nextLine();
				dm.modifyWord(word);
				break;
			case 5:
				System.out.println("Delete word: ");
				word = sc.nextLine();
				dm.deleteWord(word);
				break;
			case 6:
				showAllWords(dm.getDic());
				break;
			case 7:
				try {
					dm.dictionaryExportToFile();
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
	
	public void dictionarySearcher(Dictionary dic) {
		System.out.println("Enter prefix: ");
		String prefix = sc.nextLine();
		prefix = prefix.toLowerCase();
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
