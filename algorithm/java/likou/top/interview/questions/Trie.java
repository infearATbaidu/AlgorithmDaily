package likou.top.interview.questions;

import java.util.HashMap;
import java.util.Map;

/**
 * @author infear
 */
public class Trie {
    public static final int END = '&';
    private Map<Integer, Trie> sources;
    private Integer value = -1;
    // path from root
    private String str = "";

    public Trie getSourceByValue(Integer value) {
        return sources.getOrDefault(value, null);
    }

    public String getStr() {
        return str;
    }

    /**
     * Initialize your data structure here.
     */
    public Trie() {
        sources = new HashMap<>();
    }

    public Trie(Map<Integer, Trie> sources, Integer value, String str) {
        this.sources = sources;
        this.value = value;
        this.str = str;
    }

    /**
     * Inserts a word into the trie.
     */
    public void insert(String word) {
        if (word == null) {
            return;
        }
        Trie cur = this;
        for (char c : word.toCharArray()) {
            cur.sources.putIfAbsent((int) c, new Trie(new HashMap<>(), (int) c, cur.str + c));
            cur = cur.sources.get((int) c);
        }
        cur.sources.putIfAbsent(END, null);
    }

    /**
     * Returns if the word is in the trie.
     */
    public boolean search(String word) {
        Trie cur = this;
        for (char c : word.toCharArray()) {
            cur = cur.sources.getOrDefault((int) c, null);
            if (cur == null) {
                return false;
            }
        }
        if (cur.sources.containsKey(END)) {
            return true;
        }
        return false;
    }

    /**
     * Returns if there is any word in the trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
        Trie cur = this;
        for (char c : prefix.toCharArray()) {
            cur = cur.sources.getOrDefault((int) c, null);
            if (cur == null) {
                return false;
            }
        }
        return true;
    }

    public boolean hasEnd() {
        return sources.containsKey(END);
    }

    public static void main(String args[]) {
        Trie trie = new Trie();
        trie.insert("apple");
        trie.insert("app");
        trie.search("app");
    }
}
