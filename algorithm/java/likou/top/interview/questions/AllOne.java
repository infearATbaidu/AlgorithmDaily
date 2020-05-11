package likou.top.interview.questions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

/**
 * @author infear
 */
public class AllOne {
    TreeMap<Integer, HashSet<String>> countsToEle;
    HashMap<String, Integer> eleToCounts;

    /**
     * Initialize your data structure here.
     */
    public AllOne() {
        countsToEle = new TreeMap<>();
        eleToCounts = new HashMap<>();
    }

    /**
     * Inserts a new key <Key> with value 1. Or increments an existing key by 1.
     */
    public void inc(String key) {
        eleToCounts.put(key, eleToCounts.getOrDefault(key, 0) + 1);

        countsToEle.putIfAbsent(eleToCounts.get(key), new HashSet<>());
        countsToEle.get(eleToCounts.get(key)).add(key);
        if (eleToCounts.get(key) == 1) {
            return;
        }

        countsToEle.get(eleToCounts.get(key) - 1).remove(key);
        if (countsToEle.get(eleToCounts.get(key) - 1).isEmpty()) {
            countsToEle.remove(eleToCounts.get(key) - 1);
        }
    }

    /**
     * Decrements an existing key by 1. If Key's value is 1, remove it from the data structure.
     */
    public void dec(String key) {
        if (!eleToCounts.containsKey(key)) {
            return;
        }
        int oldVal = eleToCounts.get(key);
        eleToCounts.put(key, oldVal - 1);
        if (oldVal == 1) {
            eleToCounts.remove(key);
        }

        countsToEle.get(oldVal).remove(key);
        if (countsToEle.get(oldVal).isEmpty()) {
            countsToEle.remove(oldVal);
        }

        if (!countsToEle.containsKey(oldVal - 1)) {
            countsToEle.put(oldVal - 1, new HashSet<>());
        }
        countsToEle.get(oldVal - 1).add(key);
        if (oldVal - 1 == 0) {
            countsToEle.remove(oldVal - 1);
        }
    }

    /**
     * Returns one of the keys with maximal value.
     */
    public String getMaxKey() {
        if (countsToEle.isEmpty()) {
            return "";
        }
        return countsToEle.lastEntry().getValue().iterator().next();
    }

    /**
     * Returns one of the keys with Minimal value.
     */
    public String getMinKey() {
        if (countsToEle.isEmpty()) {
            return "";
        }
        return countsToEle.firstEntry().getValue().iterator().next();
    }
}
