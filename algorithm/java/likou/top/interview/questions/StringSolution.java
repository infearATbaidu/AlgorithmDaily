package likou.top.interview.questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author infear
 */
public class StringSolution {
    public static void main(String args[]) {
        new StringSolution().simplifyPath("/a//b////c/d//././/..");
    }

    /* 验证回文串
     给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。

     说明：本题中，我们将空字符串定义为有效的回文串。

     示例 1:

     输入: "A man, a plan, a canal: Panama"
     输出: true
     示例 2:

     输入: "race a car"
     输出: false*/
    public boolean isPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        int i = 0, j = s.length() - 1;
        s = s.toLowerCase();
        while (i < j) {
            while (i != s.length() && !Character.isAlphabetic(s.charAt(i)) && !Character.isDigit(s.charAt(i))) {
                i++;
            }
            if (i == s.length()) {
                break;
            }
            while (j > i && !Character.isAlphabetic(s.charAt(j)) && !Character.isDigit(s.charAt(j))) {
                j--;
            }
            if (j <= i) {
                break;
            }
            if (s.charAt(i) == s.charAt(j)) {
                i++;
                j--;
            } else {
                return false;
            }
        }
        return true;
    }

    /*    编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 char[] 的形式给出。
        不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
        你可以假设数组中的所有字符都是 ASCII 码表中的可打印字符。

        示例 1：

        输入：["h","e","l","l","o"]
        输出：["o","l","l","e","h"]
        示例 2：

        输入：["H","a","n","n","a","h"]
        输出：["h","a","n","n","a","H"]*/
    public void reverseString(char[] s) {
        reverseString(s, 0, s.length);
    }

    public void reverseString(char[] s, int start, int end) {
        if (s == null || s.length == 0) {
            return;
        }
        int i = start, j = end - 1;
        char tmp;
        while (i < j) {
            tmp = s[i];
            s[i] = s[j];
            s[j] = tmp;
            i++;
            j--;
        }
    }

    /* 字符串中的第一个唯一字符
     给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。

     案例:

     s = "leetcode"
     返回 0.

     s = "loveleetcode",
     返回 2.*/
    public int firstUniqChar(String s) {
        if (s == null || s.length() == 0) {
            return -1;
        }
        // use linked hashmap to reserve order
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();
        // reserve all duplicated char
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i != s.length(); i++) {
            char c = s.charAt(i);
            if (!set.contains((int) c)) {
                map.put((int) c, i);
                set.add((int) c);
            } else {
                map.remove((int) c);
            }
        }
        if (map.isEmpty()) {
            return -1;
        }
        return map.entrySet().iterator().next().getValue();
    }

    /*   分割回文串
       给定一个字符串 s，将 s 分割成一些子串，使每个子串都是回文串。

       返回 s 所有可能的分割方案。

       示例:

       输入: "aab"
       输出:
               [
               ["aa","b"],
               ["a","a","b"]
               ]*/
    public List<List<String>> partition(String s) {
        if (s == null || s.length() == 0) {
            return new LinkedList<>();
        }
        List<List<String>> result = new LinkedList<>();
        for (int i = 1; i <= s.length(); i++) {
            if (isReverse(s.substring(0, i))) {
                List<List<String>> subs = partition(s.substring(i));
                if (subs.isEmpty()) {
                    List<String> tmp = new LinkedList<>();
                    tmp.add(s.substring(0, i));
                    result.add(tmp);
                } else {
                    for (List<String> sub : subs) {
                        sub.add(0, s.substring(0, i));
                        result.add(sub);
                    }
                }
            }
        }
        return result;
    }

    private boolean isReverse(String substring) {
        if (substring.length() == 0) {
            return false;
        }
        int i = 0, j = substring.length() - 1;
        while (i < j) {
            if (substring.charAt(i) != substring.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    /* 单词拆分
     给定一个非空字符串 s 和一个包含非空单词列表的字典 wordDict，判定 s 是否可以被空格拆分为一个或多个在字典中出现的单词。

     说明：

     拆分时可以重复使用字典中的单词。
     你可以假设字典中没有重复的单词。
     示例 1：

     输入: s = "leetcode", wordDict = ["leet", "code"]
     输出: true
     解释: 返回 true 因为 "leetcode" 可以被拆分成 "leet code"。
     示例 2：

     输入: s = "applepenapple", wordDict = ["apple", "pen"]
     输出: true
     解释: 返回 true 因为 "applepenapple" 可以被拆分成 "apple pen apple"。
     注意你可以重复使用字典中的单词。
     示例 3：

     输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
     输出: false*/
    public boolean wordBreak(String s, List<String> wordDict) {
        int l = s.length();
        boolean[] isMatch = new boolean[l + 1];
        isMatch[l] = true;
        Set<String> dict = new HashSet<>(wordDict);
        for (int j = l - 1; j >= 0; j--) {
            for (int k = j + 1; k <= l; k++) {
                if (!isMatch[k]) {
                    continue;
                }
                if (dict.contains(s.substring(j, k))) {
                    isMatch[j] = true;
                    break;
                }
            }
        }
        return isMatch[0];
    }

    public boolean wordBreak_(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        return wordBreakRecur(s, dict);
    }

    private boolean wordBreakRecur(String s, Set<String> dict) {
        if (s.length() == 0) {
            return true;
        }
        for (int i = 1; i <= s.length(); i++) {
            if (!dict.contains(s.substring(0, i))) {
                continue;
            }
            if (wordBreakRecur(s.substring(i), dict)) {
                return true;
            }
        }
        return false;
    }

    /*单词拆分 II
    给定一个非空字符串 s 和一个包含非空单词列表的字典 wordDict，在字符串中增加空格来构建一个句子，使得句子中所有的单词都在词典中。返回所有这些可能的句子。
    说明：

    分隔时可以重复使用字典中的单词。
    你可以假设字典中没有重复的单词。
    示例 1：

    输入:
    s = "catsanddog"
    wordDict = ["cat", "cats", "and", "sand", "dog"]
    输出:
            [
            "cats and dog",
            "cat sand dog"
            ]
    示例 2：

    输入:
    s = "pineapplepenapple"
    wordDict = ["apple", "pen", "applepen", "pine", "pineapple"]
    输出:
            [
            "pine apple pen apple",
            "pineapple pen apple",
            "pine applepen apple"
            ]
    解释: 注意你可以重复使用字典中的单词。
    示例 3：

    输入:
    s = "catsandog"
    wordDict = ["cats", "dog", "sand", "and", "cat"]
    输出:
            []*/
    public List<String> wordBreak2(String s, List<String> wordDict) {
        Trie trie = new Trie();
        HashMap<String, Integer> wordIndex = new HashMap<>();
        for (int i = 0; i != wordDict.size(); i++) {
            trie.insert(wordDict.get(i));
            wordIndex.put(wordDict.get(i), i);
        }
        HashMap<Integer, Set<Integer>> startToEndMatched = new HashMap<>();
        for (int i = 0; i != s.length(); i++) {
            startToEndMatched.put(i, trie.searchEndMatchIndex(s, i));
        }
        List<List<Integer>>[] r = new List[s.length() + 1];
        r[s.length()] = Arrays.asList(Arrays.asList());
        for (int i = s.length() - 1; i != -1; i--) {
            List<List<Integer>> cur = new LinkedList<>();
            for (int j = i + 1; j <= s.length(); j++) {
                if (!r[j].isEmpty() && startToEndMatched.get(i).contains(j)) {
                    for (List<Integer> sub : r[j]) {
                        List<Integer> copy = new LinkedList(sub);
                        copy.add(0, wordIndex.get(s.substring(i, j)));
                        cur.add(copy);
                    }
                }
            }
            r[i] = cur;
        }
        List<String> result = new LinkedList<>();
        for (List<Integer> indexes : r[0]) {
            List<String> ele = indexes.stream().map(i -> wordDict.get(i)).collect(Collectors.toList());
            result.add(String.join(" ", ele));
        }
        return result;
    }

    /*
        有效的字母异位词
        给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。

        示例 1:

        输入: s = "anagram", t = "nagaram"
        输出: true
        示例 2:

        输入: s = "rat", t = "car"
        输出: false
        说明:
        你可以假设字符串只包含小写字母。

        进阶:
        如果输入字符串包含 unicode 字符怎么办？你能否调整你的解法来应对这种情况？*/
    public boolean isAnagram(String s, String t) {
        HashMap<Integer, Integer> stats1 = new HashMap<>();
        HashMap<Integer, Integer> stats2 = new HashMap<>();
        for (char c : s.toCharArray()) {
            stats1.putIfAbsent((int) c, 0);
            stats1.put((int) c, stats1.get((int) c) + 1);
        }
        for (char c : t.toCharArray()) {
            stats2.putIfAbsent((int) c, 0);
            stats2.put((int) c, stats2.get((int) c) + 1);
        }

        for (Integer key : stats1.keySet()) {
            if (!stats1.get(key).equals(stats2.getOrDefault(key, null))) {
                return false;
            }
            stats2.remove(key);
        }
        if (stats2.isEmpty()) {
            return true;
        }
        return false;
    }

    public List<String> findWords2(char[][] board, String[] words) {
        List<String> result = new LinkedList<>();
        HashMap<Integer, Set<Coodinate>> pos = new HashMap();
        for (String word : words) {
            boolean isFind = false;
            for (int i = 0; i != board.length; i++) {
                for (int j = 0; j != board[0].length; j++) {
                    if (find(word, 0, board, new Coodinate(i, j), new HashSet<>())) {
                        result.add(word);
                        isFind = true;
                        break;
                    }
                }
                if (isFind) {
                    break;
                }
            }
        }
        return result;
    }

    private boolean find(String word, int i, char board[][], Coodinate c, HashSet path) {
        if (i == word.length()) {
            return true;
        }
        if (c.getX() < 0 || c.getX() >= board.length || c.getY() < 0 || c.getY() >= board[0].length) {
            return false;
        }
        char start = word.charAt(i);
        if (board[c.getX()][c.getY()] != start) {
            return false;
        }
        path.add(c);
        if (!path.contains(new Coodinate(c.getX(), c.getY() - 1))) {
            boolean left = find(word, i + 1, board, new Coodinate(c.getX(), c.getY() - 1), path);
            if (left) {
                return true;
            }
        }

        if (!path.contains(new Coodinate(c.getX() - 1, c.getY()))) {
            boolean up = find(word, i + 1, board, new Coodinate(c.getX() - 1, c.getY()), path);
            if (up) {
                return true;
            }
        }

        if (!path.contains(new Coodinate(c.getX(), c.getY() + 1))) {
            boolean right = find(word, i + 1, board, new Coodinate(c.getX(), c.getY() + 1), path);
            if (right) {
                return true;
            }
        }

        if (!path.contains(new Coodinate(c.getX() + 1, c.getY()))) {
            boolean down = find(word, i + 1, board, new Coodinate(c.getX() + 1, c.getY()), path);
            if (down) {
                return true;
            }
        }
        path.remove(c);
        return false;
    }

    public List<String> findWords(char[][] board, String[] words) {
        Set<String> result = new HashSet<>();
        Trie root = new Trie();
        for (String word : words) {
            root.insert(word);
        }
        boolean path[][] = new boolean[board.length][board[0].length];
        for (int i = 0; i != board.length; i++) {
            for (int j = 0; j != board[0].length; j++) {
                search(board, i, j, root, result, path);
            }
        }
        return new LinkedList<>(result);
    }

    private void search(char[][] board, int i, int j, Trie root, Set<String> result, boolean[][] path) {
        char c = board[i][j];
        if (!root.startsWith(String.valueOf(c))) {
            return;
        }
        path[i][j] = true;
        Trie sub = root.getSourceByValue((int) c);
        if (sub.hasEnd()) {
            result.add(sub.getStr());
        }
        if (i + 1 < board.length && !path[i + 1][j]) {
            search(board, i + 1, j, sub, result, path);
        }
        if (i - 1 >= 0 && !path[i - 1][j]) {
            search(board, i - 1, j, sub, result, path);
        }
        if (j + 1 < board[0].length && !path[i][j + 1]) {
            search(board, i, j + 1, sub, result, path);
        }
        if (j - 1 >= 0 && !path[i][j - 1]) {
            search(board, i, j - 1, sub, result, path);
        }
        path[i][j] = false;
    }

    /*    字母异位词分组
        给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。

        示例:

        输入: ["eat", "tea", "tan", "ate", "nat", "bat"]
        输出:
                [
                ["ate","eat","tea"],
                ["nat","tan"],
                ["bat"]
                ]
        说明：

        所有输入均为小写字母。
        不考虑答案输出的顺序。*/
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> m = new HashMap<>();
        for (String s : strs) {
            char arr[] = s.toCharArray();
            Arrays.sort(arr);
            String sorted = new String(arr);
            m.putIfAbsent(sorted, new LinkedList<>());
            m.get(sorted).add(s);
        }
        List<List<String>> r = new LinkedList<>();
        r.addAll(m.values());
        return r;
    }

    /*    无重复字符的最长子串
        给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。

        示例 1:

        输入: "abcabcbb"
        输出: 3
        解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
        示例 2:

        输入: "bbbbb"
        输出: 1
        解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
        示例 3:

        输入: "pwwkew"
        输出: 3
        解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
        请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。*/
    public int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) {
            return 0;
        }
        int max = 0, start = -1;
        char[] array = s.toCharArray();
        HashMap<Integer, Integer> charToPos = new HashMap<>();
        for (int index = 0; index != array.length; index++) {
            int key = array[index];
            // key first appears or the last appear post is before start, which should be overwrite.
            if (!charToPos.containsKey(key) || charToPos.get(key) <= start) {
                charToPos.put(key, index);
                max = Math.max(index - start, max);
            } else {
                int pos = charToPos.get(key);
                start = pos;
                charToPos.put(key, index);
            }
        }
        return max;
    }

/*    矩阵置零
    给定一个 m x n 的矩阵，如果一个元素为 0，则将其所在行和列的所有元素都设为 0。请使用原地算法。

    示例 1:

    输入:
            [
            [1,1,1],
            [1,0,1],
            [1,1,1]
            ]
    输出:
            [
            [1,0,1],
            [0,0,0],
            [1,0,1]
            ]
    示例 2:

    输入:
            [
            [0,1,2,0],
            [3,4,5,2],
            [1,3,1,5]
            ]
    输出:
            [
            [0,0,0,0],
            [0,4,5,0],
            [0,3,1,0]
            ]
    进阶:

    一个直接的解决方案是使用  O(mn) 的额外空间，但这并不是一个好的解决方案。
    一个简单的改进方案是使用 O(m + n) 的额外空间，但这仍然不是最好的解决方案。
    你能想出一个常数空间的解决方案吗？*/

    public void setZeroes(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return;
        }
        List<Integer> pos = new ArrayList<>();
        for (int i = 0; i != matrix.length; i++) {
            for (int j = 0; j != matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    pos.add(i);
                    pos.add(j);
                }
            }
        }
        int i = 0;
        while (i != pos.size()) {
            int row = pos.get(i);
            i++;
            int col = pos.get(i);
            i++;
            for (int j = 0; j != matrix[row].length; j++) {
                matrix[row][j] = 0;
            }
            for (int j = 0; j != matrix.length; j++) {
                matrix[j][col] = 0;
            }
        }
    }

    /*    最长回文子串
        给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。

        示例 1：

        输入: "babad"
        输出: "bab"
        注意: "aba" 也是一个有效答案。
        示例 2：

        输入: "cbbd"
        输出: "bb"*/
    public String longestPalindrome(String s) {
        int max = 0;
        String r = "";
        char[] arr = s.toCharArray();
        for (int i = 0; i != arr.length; i++) {
            // try make arr[i] center
            int left = i, right = i;
            while (left - 1 >= 0 && right + 1 < arr.length && arr[left - 1] == arr[right + 1]) {
                left--;
                right++;
            }
            if (right - left + 1 > max) {
                r = s.substring(left, right + 1);
                max = right - left + 1;
            }
            if (i + 1 >= arr.length || arr[i] != arr[i + 1]) {
                continue;
            }
            // try make arr[i],arr[i+1] center
            left = i;
            right = i + 1;
            while (left - 1 >= 0 && right + 1 < arr.length && arr[left - 1] == arr[right + 1]) {
                left--;
                right++;
            }
            if (right - left + 1 > max) {
                r = s.substring(left, right + 1);
                max = right - left + 1;
            }
        }
        return r;
    }

    /*    最长公共前缀
        编写一个函数来查找字符串数组中的最长公共前缀。

        如果不存在公共前缀，返回空字符串 ""。

        示例 1:

        输入: ["flower","flow","flight"]
        输出: "fl"
        示例 2:

        输入: ["dog","racecar","car"]
        输出: ""
        解释: 输入不存在公共前缀。
        说明:

        所有输入只包含小写字母 a-z 。*/
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        String r = strs[0];
        for (int i = 1; i != strs.length; i++) {
            int c1 = 0, c2 = 0;
            while (c1 != r.length() && c2 != strs[i].length() && r.charAt(c1) == strs[i].charAt(c2)) {
                c1++;
                c2++;
            }
            r = r.substring(0, c1);
            if (r.equals("")) {
                return r;
            }
        }
        return r;
    }

    public String longestCommonPrefix2(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        String r = strs[0];
        int index = 0;
        while (index != r.length()) {
            for (String str : strs) {
                // 存在一个字符串的长度不够，或者对应位置字符不一致
                if (index >= str.length() || str.charAt(index) != r.charAt(index)) {
                    return r.substring(0, index);
                }
            }
            index++;
        }
        return r.substring(0, index);
    }

    /*    字符串的排列
        给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的排列。
        换句话说，第一个字符串的排列之一是第二个字符串的子串。
        示例1:
        输入: s1 = "ab" s2 = "eidbaooo"
        输出: True
        解释: s2 包含 s1 的排列之一 ("ba").

        示例2:
        输入: s1= "ab" s2 = "eidboaoo"
        输出: False
        注意：

        输入的字符串只包含小写字母
        两个字符串的长度都在 [1, 10,000] 之间*/
    public boolean checkInclusion(String s1, String s2) {
        int[] vals = new int[26];
        for (char c : s1.toCharArray()) {
            vals[c - 'a']++;
        }
        int counts[] = new int[26];
        int index = 0;
        while (index + s1.length() <= s2.length()) {
            if (index == 0) {
                for (int i = index; i != index + s1.length(); i++) {
                    counts[s2.charAt(i) - 'a']++;
                }
            } else {
                counts[s2.charAt(index - 1) - 'a']--;
                counts[s2.charAt(index + s1.length() - 1) - 'a']++;
            }
            if (Arrays.equals(vals, counts)) {
                return true;
            }
            index++;
        }
        return false;
    }

    /*    字符串相乘
        给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。

        示例 1:

        输入: num1 = "2", num2 = "3"
        输出: "6"
        示例 2:

        输入: num1 = "123", num2 = "456"
        输出: "56088"
        说明：

        num1 和 num2 的长度小于110。
        num1 和 num2 只包含数字 0-9。
        num1 和 num2 均不以零开头，除非是数字 0 本身。
        不能使用任何标准库的大数类型（比如 BigInteger）或直接将输入转换为整数来处理。*/
    public String multiply(String num1, String num2) {
        StringBuilder r = new StringBuilder("0");
        if (num1.equals("0") || num2.equals("0")) {
            return r.toString();
        }
        int base = 0;
        while (base != num2.length()) {
            int ele = num2.charAt(num2.length() - 1 - base) - '0';
            //calculate num1 * ele
            StringBuilder sb = new StringBuilder();
            // if ele = 0, no need to cal and just fill 0

            int k = num1.length() - 1, flag = 0;
            while (k != -1) {
                int val = (num1.charAt(k) - '0') * ele + flag;
                flag = val / 10;
                sb.insert(0, val % 10);
                k--;
            }
            if (flag != 0) {
                sb.insert(0, flag);
            }
            // fill 0 at end;
            int zero = 0;
            while (zero++ != base) {
                sb.append(0);
            }
            // add sb and r
            StringBuilder tmp = new StringBuilder();
            int c1 = sb.length() - 1, c2 = r.length() - 1;
            flag = 0;
            while (c1 > -1 || c2 > -1 || flag != 0) {
                int v1 = c1 > -1 ? sb.charAt(c1) - '0' : 0;
                int v2 = c2 > -1 ? r.charAt(c2) - '0' : 0;
                int v = v1 + v2 + flag;
                tmp.insert(0, v % 10);
                flag = v / 10;
                c1--;
                c2--;
            }
            r = tmp;
            base++;
        }
        return r.toString();
    }

    public String multiply2(String num1, String num2) {
        String result = "0";
        for (int index = num2.length() - 1; index != -1; index--) {
            int value = num2.charAt(index) - '0';
            String r = "0";
            for (int i = 0; i != value; i++) {
                r = add(r, num1);
            }
            if (!r.equals("0")) {
                for (int i = 0; i != num2.length() - 1 - index; i++) {
                    r = r + "0";
                }
            }
            result = add(result, r);
        }
        return result;
    }

    private String add(String n1, String n2) {
        StringBuilder r = new StringBuilder();
        int flag = 0;
        int idx1 = n1.length() - 1, idx2 = n2.length() - 1;
        while (flag == 1 || idx1 >= 0 || idx2 >= 0) {
            int v1 = idx1 >= 0 ? n1.charAt(idx1) - '0' : 0;
            int v2 = idx2 >= 0 ? n2.charAt(idx2) - '0' : 0;
            int sum = v1 + v2 + flag;
            flag = sum / 10;
            r.insert(0, sum % 10);
            idx1--;
            idx2--;
        }
        return r.toString();
    }

    /*    翻转字符串里的单词
        给定一个字符串，逐个翻转字符串中的每个单词。
        示例 1：

        输入: "the sky is blue"
        输出: "blue is sky the"
        示例 2：

        输入: "  hello world!  "
        输出: "world! hello"
        解释: 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
        示例 3：

        输入: "a good   example"
        输出: "example good a"
        解释: 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。


        说明：

        无空格字符构成一个单词。
        输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
        如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个*/
    public String reverseWords(String s) {
        StringBuilder r = new StringBuilder();
        StringBuilder word = new StringBuilder();
        for (int i = s.length() - 1; i != -1; i--) {
            char c = s.charAt(i);
            if (c != ' ') {
                word.append(c);
            } else {
                if (word.length() != 0) {
                    r.append(word.reverse());
                    r.append(" ");
                    word = new StringBuilder();
                }
            }
        }
        // last word may not have space behind.
        if (word.length() != 0) {
            r.append(word.reverse());
        }
        // remove last space
        if (r.length() >= 1 && r.charAt(r.length() - 1) == ' ') {
            r.deleteCharAt(r.length() - 1);
        }
        return r.toString();
    }

    public String reverseWords2(String s) {
        char[] arr = s.toCharArray();
        reverseString(arr, 0, arr.length);
        int index = 0;
        List<String> words = new LinkedList<>();
        while (index != arr.length) {
            while (index != arr.length && arr[index] == ' ') {
                index++;
            }
            if (index == arr.length) {
                break;
            }
            int end = index;
            while (end != arr.length && arr[end] != ' ') {
                end++;
            }
            reverseString(arr, index, end);
            words.add(new String(arr, index, end - index));
            index = end;
        }
        return String.join(" ", words);
    }

    /*    简化路径
        以 Unix 风格给出一个文件的绝对路径，你需要简化它。或者换句话说，将其转换为规范路径。

        在 Unix 风格的文件系统中，一个点（.）表示当前目录本身；此外，两个点 （..） 表示将目录切换到上一级（指向父目录）；两者都可以是复杂相对路径的组成部分。更多信息请参阅：Linux / Unix中的绝对路径 vs
         相对路径

        请注意，返回的规范路径必须始终以斜杠 / 开头，并且两个目录名之间必须只有一个斜杠 /。最后一个目录名（如果存在）不能以 / 结尾。此外，规范路径必须是表示绝对路径的最短字符串。*/
    public String simplifyPath(String path) {
        String[] dirs = path.split("/");
        LinkedList dirStack = new LinkedList();
        for (String dir : dirs) {
            if (dir.length() == 0) {
                continue;
            }
            switch (dir) {
                case ".":
                    break;
                case "..":
                    if (!dirStack.isEmpty()) {
                        dirStack.pollLast();
                    }
                    break;
                default:
                    dirStack.add(dir);
            }
        }
        return "/" + String.join("/", dirStack);
    }

    /*
        复原IP地址
        给定一个只包含数字的字符串，复原它并返回所有可能的 IP 地址格式。

        有效的 IP 地址正好由四个整数（每个整数位于 0 到 255 之间组成），整数之间用 '.' 分隔。
    */
    public List<String> restoreIpAddresses(String s) {
        return restoreIpAddresses(s, 0, s.length(), 4);
    }

    private List<String> restoreIpAddresses(String s, int start, int end, int nums) {
        if (nums == 0) {
            if (start == end) {
                return Arrays.asList("");
            } else {
                return new ArrayList<>();
            }
        }
        List<String> r = new LinkedList<>();
        for (int i = 1; i <= 3; i++) {
            if (start + i > end) {
                break;
            }
            String value = s.substring(start, start + i);
            if ((value.charAt(0) != '0' || value.equals("0")) && Integer.valueOf(value) <= 255) {
                List<String> sub = restoreIpAddresses(s, start + i, end, nums - 1);
                if (sub.isEmpty()) {
                    continue;
                }
                for (String suffix : sub) {
                    if (suffix.length() == 0) {
                        r.add(value + suffix);
                    } else {
                        r.add(value + "." + suffix);
                    }
                }
            }
        }
        return r;
    }

    class Trie {
        public static final int END = '&';
        public Map<Integer, Trie> sources;
        public Integer value = -1;
        // path from root
        public String str = "";

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

        public Trie getSourceByValue(Integer value) {
            return sources.getOrDefault(value, null);
        }

        public String getStr() {
            return str;
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

        // return the end position in word which make word[start, end), otherwise return start;
        public Set<Integer> searchEndMatchIndex(String word, int start) {
            Trie cur = this;
            char arr[] = word.toCharArray();
            Set<Integer> index = new HashSet<>();
            for (int i = start; i != arr.length; i++) {
                cur = cur.sources.getOrDefault((int) arr[i], null);
                if (cur == null) {
                    return index;
                }
                if (cur.sources.containsKey(END)) {
                    index.add(i + 1);
                }
            }
            return index;
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
    }

    class Coodinate {
        int x;
        int y;

        public Coodinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Coodinate coodinate = (Coodinate) o;
            return x == coodinate.x &&
                    y == coodinate.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
