package dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;

public class DictionaryManagement {
	public void insertFromCommandline(Dictionary dic) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter number of words: ");
		int n = sc.nextInt();
		sc.nextLine();
		for(int i=1; i<=n; i++) {
			System.out.printf("Enter english word: ", i);
			String engWord = sc.nextLine();
			System.out.printf("Enter mean in VNese: ", i);
			String mean = sc.nextLine();
			Word w = new Word(engWord, mean);
			dic.addWord(w);
		}
		sc.close();
	}
	
	public void insertFromFile(Dictionary dic) {
		try {
			FileReader fr = new FileReader("C:\\Users\\Administrator\\eclipse-workspace\\myProject\\src\\dictionary\\dictionaries.txt");
			BufferedReader br = new BufferedReader(fr);
			String line;
			while((line = br.readLine()) != null) {
				int pos = line.indexOf('\t');
				String engWord = line.substring(0, pos);
				String mean = line.substring(pos+1);
				dic.addWord(new Word(engWord,mean));
			}
			br.close();
			dic.sortDic();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void dictionaryLookup(Dictionary dic) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Seach word: ");
		String engWord = sc.nextLine();
		int pos = Collections.binarySearch(dic.words, new Word(engWord,null));
		if(pos >= 0) {
			System.out.println("Mean: " + dic.words.get(pos).getWord_explain());
		}
		else {
			System.out.println("The word is not exist in dictionary!");
		}
		sc.close();
	}
	
}
