package leetcode;

import java.util.*;

/**
 * https://leetcode.com/problems/intersection-of-two-arrays/#/description
 *
 * @author infear
 */
public class Intersection {
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
}
