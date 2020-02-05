package leetcode;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/next-permutation/
 *
 * @author infear
 */
public class NextPermutation {
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        int i = nums.length - 1, max = 0;
        while (true) {
            if (nums[i] >= max) {
                max = nums[i];
                i--;
                if (i < 0) {
                    Arrays.sort(nums);
                    // handle start with '0'
                    return;
                }
            } else {
                int j = i + 1;
                while (j != nums.length && nums[j] > nums[i]) {
                    j++;
                }
                int tmp = nums[j - 1];
                nums[j - 1] = nums[i];
                nums[i] = tmp;
                Arrays.sort(nums, i + 1, nums.length);
                return;
            }
        }
    }
}
