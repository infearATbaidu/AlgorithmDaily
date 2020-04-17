package likou.top.interview.questions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @author infear
 */
public class ArraySolution {
    /*给你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字）。
    示例 1:
    输入: [2,3,-2,4]
    输出: 6
    解释: 子数组 [2,3] 有最大乘积 6。
    示例 2:

    输入: [-2,0,-1]
    输出: 0
    解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。*/
    public int maxProduct(int[] nums) {
        int[] max = new int[nums.length];
        int[] min = new int[nums.length];
        int result = nums[0];
        max[0] = min[0] = result;
        for (int i = 1; i < nums.length; i++) {
            max[i] = Math.max(Math.max(max[i - 1] * nums[i], min[i - 1] * nums[i]), nums[i]);
            min[i] = Math.min(Math.min(max[i - 1] * nums[i], min[i - 1] * nums[i]), nums[i]);
            result = Math.max(max[i], result);
        }
        return result;
    }

    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int i : nums) {
            if (set.contains(i)) {
                return true;
            }
            set.add(i);
        }
        return false;
    }

    /* 移动零
     给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。

     示例:

     输入: [0,1,0,3,12]
     输出: [1,3,12,0,0]
     说明:

     必须在原数组上操作，不能拷贝额外的数组。
     尽量减少操作次数。*/
    public void moveZeroes(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        int i = 0;
        while (i != nums.length) {
            if (nums[i] != 0) {
                i++;
                continue;
            }
            int j = i;
            while (j < nums.length && nums[j] == 0) {
                j++;
            }
            if (j < nums.length) {
                nums[i] = nums[j];
                nums[j] = 0;
            } else {
                return;
            }

        }
    }

    /* 两个数组的交集 II
     给定两个数组，编写一个函数来计算它们的交集。

     示例 1:

     输入: nums1 = [1,2,2,1], nums2 = [2,2]
     输出: [2,2]
     示例 2:

     输入: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
     输出: [4,9]
     说明：

     输出结果中每个元素出现的次数，应与元素在两个数组中出现的次数一致。
     我们可以不考虑输出结果的顺序。
     进阶:

     如果给定的数组已经排好序呢？你将如何优化你的算法？
     如果 nums1 的大小比 nums2 小很多，哪种方法更优？
     如果 nums2 的元素存储在磁盘上，磁盘内存是有限的，并且你不能一次加载所有的元素到内存中，你该怎么办？*/
    public int[] intersect(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> stats1 = new HashMap<>();
        HashMap<Integer, Integer> stats2 = new HashMap<>();
        for (int i : nums1) {
            stats1.putIfAbsent(i, 0);
            stats1.put(i, stats1.get(i) + 1);
        }
        for (int i : nums2) {
            stats2.putIfAbsent(i, 0);
            stats2.put(i, stats2.get(i) + 1);
        }
        List<Integer> result = new LinkedList<>();
        for (Integer key : stats1.keySet()) {
            int times = Math.min(stats1.get(key), stats2.getOrDefault(key, 0));
            for (int i = 0; i != times; i++) {
                result.add(key);
            }
        }
        return result.stream().mapToInt(i -> i).toArray();
    }

    /* 除自身以外数组的乘积
     给你一个长度为 n 的整数数组 nums，其中 n > 1，返回输出数组 output ，其中 output[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积。
     示例:

     输入: [1,2,3,4]
     输出: [24,12,8,6]


     提示：题目数据保证数组之中任意元素的全部前缀元素和后缀（甚至是整个数组）的乘积都在 32 位整数范围内。

     说明: 请不要使用除法，且在 O(n) 时间复杂度内完成此题。

     进阶：
     你可以在常数空间复杂度内完成这个题目吗？（ 出于对空间复杂度分析的目的，输出数组不被视为额外空间。）*/
    public int[] productExceptSelf(int[] nums) {
        int[] r = new int[nums.length];
        r[0] = 1;
        for (int i = 1; i != nums.length; i++) {
            r[i] = r[i - 1] * nums[i - 1];
        }
        int tmp = 1;
        for (int i = nums.length - 1; i != -1; i--) {
            r[i] = r[i] * tmp;
            tmp *= nums[i];
        }
        return r;
    }

    /*旋转数组
    给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。

    示例 1:

    输入: [1,2,3,4,5,6,7] 和 k = 3
    输出: [5,6,7,1,2,3,4]
    解释:
    向右旋转 1 步: [7,1,2,3,4,5,6]
    向右旋转 2 步: [6,7,1,2,3,4,5]
    向右旋转 3 步: [5,6,7,1,2,3,4]
    示例 2:

    输入: [-1,-100,3,99] 和 k = 2
    输出: [3,99,-1,-100]
    解释:
    向右旋转 1 步: [99,-1,-100,3]
    向右旋转 2 步: [3,99,-1,-100]
    说明:

    尽可能想出更多的解决方案，至少有三种不同的方法可以解决这个问题。
    要求使用空间复杂度为 O(1) 的 原地 算法。*/
    public void rotate(int[] nums, int k) {
        k = k % nums.length;
        swap(nums, 0, nums.length);
        swap(nums, 0, k);
        swap(nums, k, nums.length);
    }

    private void swap(int[] nums, int start, int end) {
        int i = start, j = end - 1;
        while (i < j) {
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
            i++;
            j--;
        }
    }

/*    给定一个未排序的数组，判断这个数组中是否存在长度为 3 的递增子序列。
    数学表达式如下:
    如果存在这样的 i, j, k,  且满足 0 ≤ i < j < k ≤ n-1，
    使得 arr[i] < arr[j] < arr[k] ，返回 true ; 否则返回 false 。
    说明: 要求算法的时间复杂度为 O(n)，空间复杂度为 O(1) 。

    示例 1:

    输入: [1,2,3,4,5]
    输出: true
    示例 2:

    输入: [5,4,3,2,1]
    输出: false*/

    public boolean increasingTriplet(int[] nums) {
        if (nums.length < 3) {
            return false;
        }
        int i = 0;
        while (i + 1 < nums.length && nums[i] >= nums[i + 1]) {
            i++;
        }
        if (i == nums.length - 1) {
            return false;
        }
        int low = nums[i], mid = nums[i + 1];
        int j = i + 2;
        if (j >= nums.length) {
            return false;
        }
        while (j != nums.length) {
            if (nums[j] > mid) {
                return true;
            }
            if (nums[j] < mid && nums[j] > low) {
                mid = nums[j];
            } else if (nums[j] < low) {
                low = nums[j];
            }
            j++;
        }
        return false;
    }

/*    打乱数组
    打乱一个没有重复元素的数组。

    示例:

    // 以数字集合 1, 2 和 3 初始化数组。
    int[] nums = {1,2,3};
    Solution solution = new Solution(nums);

    // 打乱数组 [1,2,3] 并返回结果。任何 [1,2,3]的排列返回的概率应该相同。
solution.shuffle();º

    // 重设数组到它的初始状态[1,2,3]。
solution.reset();

    // 随机返回数组[1,2,3]打乱后的结果。
solution.shuffle();*/

    class Solution {
        private int origin[];

        public Solution(int[] nums) {
            origin = nums;
        }

        /**
         * Resets the array to its original configuration and return it.
         */
        public int[] reset() {
            return origin;
        }

        /**
         * Returns a random shuffling of the array.
         */
        public int[] shuffle() {
            int[] copy = new int[origin.length];
            for (int i = 0; i != origin.length; i++) {
                copy[i] = origin[i];
            }
            for (int i = 0; i != copy.length; i++) {
                int pos = new Random().nextInt(origin.length - i) + i;
                int tmp = copy[pos];
                copy[pos] = copy[i];
                copy[i] = tmp;
            }
            return copy;
        }
    }
}
