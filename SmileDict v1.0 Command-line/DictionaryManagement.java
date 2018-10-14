package dictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Scanner;

public class DictionaryManagement {
	private Scanner sc = new Scanner(System.in);
	private Dictionary dic = new Dictionary();
	
	public Dictionary getDic() {
		return dic;
	}
	
	public void insertFromCommandline() {
		System.out.println("Enter number of words: ");
		int n = sc.nextInt();
		sc.nextLine();
		for(int i=1; i<=n; i++) {
			System.out.printf("Enter word %d: %n", i);
			String engWord = sc.nextLine();
			System.out.println("Enter mean: ");
			String mean = sc.nextLine();
			Word w = new Word(engWord, mean);
			dic.addWord(w);
		}
	}
	
	public void insertFromFile() {
		try {
			FileReader fr = new FileReader("dictionaries.txt");
			BufferedReader br = new BufferedReader(fr);
			String line;
			while((line = br.readLine()) != null) {
				String[] arr = line.split("\t");
				dic.addWord(new Word(arr[0],arr[1]));
			}
			br.close();
			//dic.sortDic();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public String dictionaryLookup(String engWord) {
		int pos = Collections.binarySearch(dic.words, new Word(engWord,null));
		if(pos >= 0) {
			return dic.words.get(pos).getWord_explain();
		}
		return null;
	}
	
	
	public boolean addWord(String word, String mean) {
		int pos = Collections.binarySearch(dic.words, new Word(word,null));
		if(pos >= 0) {
			System.out.println("The word is already exist in dictionary!");
			return false;
		}
		else {
			dic.addWord(new Word(word.toLowerCase(),mean),(-pos-1));
			System.out.println("Add successfully!");
			return true;
		}
	}
	
	public boolean modifyWord(String word) {
		int pos = Collections.binarySearch(dic.words, new Word(word,null));
		if(pos >= 0) {
			System.out.println("New meaning: ");
			String mean = sc.nextLine();
			dic.words.get(pos).setWord_explain(mean);
			System.out.println("Modify successfully!");
			return true;
		}
		else {
			System.out.println("The word is not exist in dictionary!");
			return false;
		}	
	}
	
	public boolean deleteWord(String word) {
		int pos = Collections.binarySearch(dic.words, new Word(word,null));
		if(pos >= 0) {
			dic.words.remove(pos);
			System.out.println("Remove successfully!");
			return true;
		}
		else {
			System.out.println("The word is not exist in dictionary!");
			return false;
		}
	}
	
	public void dictionaryExportToFile() throws FileNotFoundException {
			File file = new File("output.txt");
			PrintWriter pw = new PrintWriter(file);
			for(int i=0; i<dic.words.size(); i++) {
				pw.printf("%s%s%s%n", dic.words.get(i).getWord_target(), "\t", dic.words.get(i).getWord_explain());
			}
			pw.close();
			System.out.println("Export file successfully (output.txt)");
		}
	}
