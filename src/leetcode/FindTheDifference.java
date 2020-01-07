package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/find-the-difference/#/description
 *
 * @author infear
 */
public class FindTheDifference {
    public char findTheDifference(String s, String t) {
        char[] ss = s.toCharArray(), tt = t.toCharArray();
        Map<Character, Integer> si = new HashMap<>();
        Map<Character, Integer> ti = new HashMap<>();
        for (char c : ss) {
            int num = si.getOrDefault(c, 0);
            si.put(c, num + 1);
        }
        for (char c : tt) {
            int num = ti.getOrDefault(c, 0);
            ti.put(c, num + 1);
        }
        for (Character c : ti.keySet()) {
            if (!si.getOrDefault(c, 0).equals(ti.getOrDefault(c, 0))) {
                return c;
            }
        }
        return 'p';
    }
}
