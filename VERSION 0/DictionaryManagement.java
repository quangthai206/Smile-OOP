package dictionary;

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
}
