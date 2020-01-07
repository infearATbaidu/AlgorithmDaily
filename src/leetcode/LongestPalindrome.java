package leetcode;

/**
 * https://leetcode.com/problems/longest-palindromic-substring/
 *
 * @author infear
 */
public class LongestPalindrome {
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        int maxStart = 0, maxEnd = 0, i = 0, l = s.length();
        boolean r[][] = new boolean[l][l];
        while (i != l) {
            r[i][i] = true;
            i++;
        }
        i = 1;
        while (i != l) {
            int j = 0;
            while (j + i < l) {
                if (s.charAt(j) != s.charAt(j + i)) {
                    r[j][j + i] = false;
                } else {
                    if (j + 1 >= j + i - 1) {
                        r[j][j + i] = true;
                    } else {
                        r[j][j + i] = r[j + 1][j + i - 1];
                    }
                }
                if (r[j][j + i] && i > maxEnd - maxStart) {
                    maxStart = j;
                    maxEnd = j + i;
                }
                j++;
            }
            i++;
        }
        return s.substring(maxStart, maxEnd + 1);

    }
}
