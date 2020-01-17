package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/restore-ip-addresses/
 *
 * @author infear
 */
public class RestoreIPAddresses {
    public static void main(String args[]) {
        new RestoreIPAddresses().restoreIpAddresses("25525511135");
    }

    public List<String> restoreIpAddresses(String s) {
        List<String> result = func(s, 0, 4);
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }

    private List<String> func(String s, int start, int k) {
        if (k == 0) {
            if (start == s.length()) {
                return new ArrayList<>();
            }
            return null;
        }
        if (start >= s.length()) {
            return null;
        }
        ArrayList<String> result = null;
        int step = 1;
        while (step <= 3) {
            List<String> sub = func(s, start + step, k - 1);
            if (sub == null) {
                step++;
                continue;
            }
            String str = s.substring(start, start + step);
            if (Integer.valueOf(str) > 255 || (step != 1 && s.charAt(start) == '0')) {
                step++;
                continue;
            }
            if (result == null) {
                result = new ArrayList<>();
            }
            if (sub.isEmpty()) {
                result.add(str);
            } else {
                for (String tmp : sub) {
                    result.add(str + "." + tmp);
                }
            }
            step++;
        }
        return result;
    }
}
