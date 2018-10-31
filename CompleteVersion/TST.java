package application;

import java.util.ArrayList;
import java.util.List;

public class TST {
	private Node root;
	
	private static class Node {
		private char c;
		private Node left,mid,right;
		private String word;
		private String mean;
	}
	
	// Constructor
	public TST() {}
	
	// put
	public void put(String key, String value) {
		root = put(root, key, value, 0); 
	}
	private Node put(Node x, String key, String value, int index) {
		char c = key.charAt(index);
		if(x == null) {
			x = new Node();
			x.c = c;
		}
		if (c < x.c) x.left = put(x.left, key, value, index);
		else if (c > x.c) x.right = put(x.right, key, value, index);
		else if (index < key.length() - 1) x.mid = put(x.mid, key, value, index+1);
		else {
			x.word = key;
			x.mean = value;
		}
		return x;
	}
	
	// get
	public String get(String key) {
		Node x = get(root, key, 0);
		if(x == null) return null;
		return x.mean;
	}
	private Node get(Node x, String key, int index) {
		if(x == null) return null;
		char c = key.charAt(index);
		if(c < x.c) return get(x.left, key, index);
		else if(c > x.c) return get(x.right, key, index);
		else if(index < key.length() - 1) return get(x.mid, key, index+1);
		else return x;
	}
	
	// contain
	public boolean contains(String key) {
		if (key == null) throw new IllegalArgumentException("argument to contain() is null");
		return (get(key) != null);
	}
	
	// List word of prefix seach
	public List<String> prefixSeach(String prefix) {
		if(prefix == null) {
			throw new IllegalArgumentException("calls valuesWithPrefix() with null argument");
		}
		List<String> words = new ArrayList<>();
		Node x = get(root, prefix, 0);
		if(x == null) {
			return words;
		}
		else if(x.mean != null) {
			words.add(x.word);
		}
		collect(x.mid, new StringBuilder(prefix), words);
		return words;
	}
	private void collect(Node x, StringBuilder prefix, List<String> words) {
		if (x == null) return;
        collect(x.left,  prefix, words);
        if (x.mean != null) words.add(x.word);
        collect(x.mid,   prefix.append(x.c), words);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(x.right, prefix, words);
	}
	
	// Show All Word
	public List<String> showAllWords() {
		List<String> words = new ArrayList<>();
		showAllWords(root, words);
		return words;
	}
	private void showAllWords(Node x, List<String> words) {
		if (x == null) return;
		showAllWords(x.left, words);
        if (x.mean != null) words.add(x.word);
        showAllWords(x.mid, words);
        showAllWords(x.right, words);
	}
}
