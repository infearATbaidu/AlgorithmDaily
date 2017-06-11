package leetcode;

import java.util.*;

/**
 * Created by infear on 2017/6/11.
 */
public class Solution {
    // https://leetcode.com/problems/next-greater-element-i/#/description
    public int[] nextGreaterElement(int[] findNums, int[] nums) {
        if (findNums == null || findNums.length == 0) {
            return null;
        }

        // 存储nums中每个元素的first-greater
        Stack<Integer> s = new Stack();
        Map<Integer, Integer> m = new HashMap();

        /*
        遍历nums,用Stack来存储：
        如果当前元素ele小于栈顶（或者栈为空）则直接入栈；
        否则逐个弹出栈顶元素，并标记每个弹出元素的first-greater为ele，直到栈顶大于ele或者栈为空
        */
        for (int ele : nums) {
            if (s.isEmpty()) {
                s.push(ele);
            } else {
                if (s.peek() > ele) {
                    s.push(ele);
                } else {
                    while (!s.isEmpty() && s.peek() < ele) {
                        m.put(s.pop(), ele);
                    }
                    s.push(ele);
                }
            }
        }

        int[] r = new int[findNums.length];
        int i = 0;
        for (int ele : findNums) {
            r[i++] = m.getOrDefault(ele, -1);
        }
        return r;

    }

    // https://leetcode.com/problems/intersection-of-two-arrays/#/description
    public int[] intersection(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null) {
            return null;
        }
        Set s2 = new HashSet();
        for (int ele : nums2) {
            s2.add(ele);
        }

        Set<Integer> r = new HashSet<>();
        for (int ele : nums1) {
            if (s2.contains(ele)) {
                r.add(ele);
            }
        }
        int result[] = new int[r.size()];
        int i = 0;
        for (int ele : r) {
            result[i++] = ele;
        }
        return result;
    }

    // https://leetcode.com/problems/find-the-difference/#/description
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
