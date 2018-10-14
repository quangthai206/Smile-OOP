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
	
	public void insertFromCommandline(Dictionary dic) {
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
	
	public void insertFromFile(Dictionary dic) {
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
	
	public void dictionaryLookup(Dictionary dic) {
		System.out.println("Search word: ");
		String engWord = sc.nextLine();
		int pos = Collections.binarySearch(dic.words, new Word(engWord,null));
		if(pos >= 0) {
			System.out.println("Meaning: " + dic.words.get(pos).getWord_explain());
		}
		else {
			System.out.println("No exact match found for \"" + engWord + "\" in English");
		}
	}
	
	
	public void addWord(Dictionary dic) {
		System.out.println("Add word: ");
		String word = sc.nextLine();
		int pos = Collections.binarySearch(dic.words, new Word(word,null));
		if(pos >= 0) {
			System.out.println("The word is already exist in dictionary!");
		}
		else {
			System.out.println("Enter mean: ");
			String mean = sc.nextLine();
			dic.addWord(new Word(word.toLowerCase(),mean),(-pos-1));
			System.out.println("Add successfully!");
		}
	}
	
	public void modifyWord(Dictionary dic) {
		System.out.println("Modify word: ");
		String word = sc.nextLine();
		int pos = Collections.binarySearch(dic.words, new Word(word,null));
		if(pos >= 0) {
			System.out.println("New meaning: ");
			String mean = sc.nextLine();
			dic.words.get(pos).setWord_explain(mean);
			System.out.println("Modify successfully!");
		}
		else {
			System.out.println("The word is not exist in dictionary!");
		}	
	}
	
	public void deleteWord(Dictionary dic) {
		System.out.println("Delete word: ");
		String word = sc.nextLine();
		int pos = Collections.binarySearch(dic.words, new Word(word,null));
		if(pos >= 0) {
			dic.words.remove(pos);
			System.out.println("Remove successfully!");
		}
		else {
			System.out.println("The word is not exist in dictionary!");
		}
	}
	
	public void dictionaryExportToFile(Dictionary dic) throws FileNotFoundException {
			File file = new File("output.txt");
			PrintWriter pw = new PrintWriter(file);
			for(int i=0; i<dic.words.size(); i++) {
				pw.printf("%s%s%s%n", dic.words.get(i).getWord_target(), "\t", dic.words.get(i).getWord_explain());
			}
			pw.close();
			System.out.println("Export file successfully (output.txt)");
		}
	}
