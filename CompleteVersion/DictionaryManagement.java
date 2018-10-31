package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

public class DictionaryManagement {
	private static TST trie;
	private static BufferedReader br;
	
	public void insertFromFileVE() {
		try {
			trie = null;
			trie = new TST();
			br = new BufferedReader(new InputStreamReader(new FileInputStream("data/VA.txt"), "UTF-8"));
			br.readLine();
			String line = null;
			String[] arr = null;
			while((line=br.readLine()) != null) {
				arr = line.split("##");
				trie.put(arr[0], arr[1]);
			}
			br.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void insertFromFile() {
		try {
			trie = null;
			trie = new TST();
			br = new BufferedReader(new InputStreamReader(new FileInputStream("data/dictionaries.txt"), "UTF-8"));
			br.readLine();
			String line = null;
			String[] arr = null;
			String means[] = null;
			while((line=br.readLine()) != null) {
				arr = line.split("##");
				means = arr[1].split("@");
				String mean = "";
				for(int i=0; i<means.length; i++) {
					mean += means[i] + "\n";
				}
				trie.put(arr[0], mean);
			}
			br.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveFile() {
		try {
			PrintWriter pr = new PrintWriter("data/dictionaries.txt", "UTF-8");
			String res;
			String mean;
			pr.println();
			for(String s: trie.showAllWords()) {
				res = s + "##";
				mean = trie.get(s).replaceAll("\n", "@");
				if(mean.charAt(mean.length() - 1) == '@') mean = mean.substring(0,mean.length() - 1);
				pr.println(res + mean);
			}
			pr.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveFileVE() {
		try {
			PrintWriter pr = new PrintWriter(new File("data/VA.txt"));
			pr.println();
			for(String s: trie.showAllWords()) {
				pr.println(s + "##" + trie.get(s));
			}
			pr.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void exportToFile() {
		try {
			PrintWriter pr = new PrintWriter("data/output.txt", "UTF-8");
			String res;
			String mean;
			pr.println();
			for(String s: trie.showAllWords()) {
				res = s + "##";
				mean = trie.get(s).replaceAll("\n", "@");
				if(mean.charAt(mean.length() - 1) == '@') mean = mean.substring(0,mean.length() - 1);
				pr.println(res + mean);
			}
			pr.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
//	public void dictionaryExportToFile() throws IOException {
//		File file = new File("output.txt");
//		PrintWriter pw = new PrintWriter(file);
//		for(String s: trie.showAllWords()) {
//			pw.println(s + "##" + trie.get(s));
//		}
//		pw.close();
//	}
	
	public List<String> getAllWords() {
		return trie.showAllWords();
	}
	
	public List<String> prefixSeach(String prefix) {
		return trie.prefixSeach(prefix);
	}
	
	public String get(String key) {
		return trie.get(key);
	}
	
	public void put(String key, String value) {
		trie.put(key, value);
	}
	
	public boolean contains(String key) {
		return trie.contains(key);
	}
}
