package likou.top.interview.questions;

import java.util.ArrayList;
import java.util.Arrays;
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
    public static void main(String args[]) {
        System.out.println(new ArraySolution().sumSubarrayMins(new int[] {3, 1, 2, 4}));
    }

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

    public int maxProduct2(int[] nums) {
        int max = 1;
        int min = 1;
        int result = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int lastMax = max, lastMin = min;
            max = Math.max(Math.max(lastMax * nums[i], nums[i]), lastMin * nums[i]);
            min = Math.min(Math.min(lastMax * nums[i], nums[i]), lastMin * nums[i]);
            result = Math.max(result, max);
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

    public void moveZeroes2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        // copy all the no-zero number to front position.
        int i = 0, cur = 0;
        while (i != nums.length) {
            if (nums[i] == 0) {
                i++;
                continue;
            }
            nums[cur++] = nums[i++];
        }
        // fill the remainder position 'zero'.
        while (cur != nums.length) {
            nums[cur++] = 0;
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

    /*    最长连续递增序列
        给定一个未经排序的整数数组，找到最长且连续的的递增序列。

        示例 1:

        输入: [1,3,5,4,7]
        输出: 3
        解释: 最长连续递增序列是 [1,3,5], 长度为3。
        尽管 [1,3,5,7] 也是升序的子序列, 但它不是连续的，因为5和7在原数组里被4隔开。
        示例 2:

        输入: [2,2,2,2,2]
        输出: 1
        解释: 最长连续递增序列是 [2], 长度为1。
        注意：数组长度不会超过10000。*/
    public int findLengthOfLCIS(int[] nums) {
        if (nums.length <= 1) {
            return nums.length;
        }
        int index = nums[0];
        int max = 1, cur = max;
        for (int i = 1; i != nums.length; i++) {
            if (nums[i] > index) {
                cur++;
            } else {
                max = Math.max(max, cur);
                cur = 1;
            }
            index = nums[i];
        }
        max = Math.max(max, cur);
        return max;
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

    private void swapRange(int[] nums, int start, int end) {
        int i = start, j = end - 1;
        while (i < j) {
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
            i++;
            j--;
        }
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

    public boolean increasingTriplet(int[] nums) {
        if (nums.length < 3) {
            return false;
        }
        int mid = Integer.MAX_VALUE, low = nums[0];
        int j = 1;
        while (j != nums.length) {
            if (nums[j] > mid) {
                return true;
            }
            if (nums[j] <= mid && nums[j] > low) {
                mid = nums[j];
            } else if (nums[j] <= low) {
                low = nums[j];
            }
            j++;
        }
        return false;
    }

    /*    第k个排列
        给出集合 [1,2,3,…,n]，其所有元素共有 n! 种排列。

        按大小顺序列出所有排列情况，并一一标记，当 n = 3 时, 所有排列如下：

                "123"
                "132"
                "213"
                "231"
                "312"
                "321"
        给定 n 和 k，返回第 k 个排列。

        说明：

        给定 n 的范围是 [1, 9]。
        给定 k 的范围是[1,  n!]。
        示例 1:

        输入: n = 3, k = 3
        输出: "213"*/
    public String getPermutation(int n, int k) {
        // calculate factorial.
        int factorial[] = new int[n + 1];
        factorial[0] = 1;
        for (int index = 1; index != n + 1; index++) {
            factorial[index] = factorial[index - 1] * index;
        }
        StringBuilder sb = new StringBuilder();
        int used[] = new int[n + 1];
        int start = n, index;
        while (k != 0) {
            // calculate [n-start] position of result.
            start--;
            int r = k / factorial[start], remainder = k % factorial[start];
            if (remainder != 0) {
                r++;
            }
            index = 1;
            while (true) {
                if (used[index] == 0) {
                    r--;
                }
                if (r == 0) {
                    break;
                }
                index++;
            }
            used[index] = 1;
            sb.append(index);
            k = remainder;
        }
        index = n;
        // use remainder element from last to first.
        while (index != 0) {
            if (used[index] == 0) {
                sb.append(index);
            }
            index--;
        }
        return sb.toString();
    }

    public String getPermutation2(int n, int k) {
        List<Integer> unused = new LinkedList<>();
        int factorial[] = new int[n];
        factorial[0] = 1;
        for (int i = 1; i != n; i++) {
            factorial[i] = factorial[i - 1] * i;
            unused.add(i);
        }
        unused.add(n);

        StringBuilder result = new StringBuilder();
        while (k != 0) {
            int q = k / factorial[n - 1], r = k % factorial[n - 1];
            if (r != 0) {
                q++;
            }
            result.append(unused.remove(q - 1));
            n--;
            k = r;
        }
        for (int i = unused.size() - 1; i != -1; i--) {
            result.append(unused.get(i));
        }
        return result.toString();
    }

    /*    岛屿的最大面积
        给定一个包含了一些 0 和 1 的非空二维数组 grid 。
        一个 岛屿 是由一些相邻的 1 (代表土地) 构成的组合，这里的「相邻」要求两个 1 必须在水平或者竖直方向上相邻。你可以假设 grid 的四个边缘都被 0（代表水）包围着。
        找到给定的二维数组中最大的岛屿面积。(如果没有岛屿，则返回面积为 0 。)
        示例 1:

                [[0,0,1,0,0,0,0,1,0,0,0,0,0],
                [0,0,0,0,0,0,0,1,1,1,0,0,0],
                [0,1,1,0,1,0,0,0,0,0,0,0,0],
                [0,1,0,0,1,1,0,0,1,0,1,0,0],
                [0,1,0,0,1,1,0,0,1,1,1,0,0],
                [0,0,0,0,0,0,0,0,0,0,1,0,0],
                [0,0,0,0,0,0,0,1,1,1,0,0,0],
                [0,0,0,0,0,0,0,1,1,0,0,0,0]]
        对于上面这个给定矩阵应返回 6。注意答案不应该是 11 ，因为岛屿只能包含水平或垂直的四个方向的 1 。*/
    public int maxAreaOfIsland(int[][] grid) {
        int max = 0;
        if (grid.length == 0 || grid[0].length == 0) {
            return max;
        }
        for (int i = 0; i != grid.length; i++) {
            for (int j = 0; j != grid[0].length; j++) {
                if (grid[i][j] != 1) {
                    continue;
                }
                int r = search(grid, i, j);
                max = Math.max(r, max);
            }
        }
        return max;
    }

    private int search(int[][] grid, int i, int j) {
        // tag grid[i][j] as visited.
        grid[i][j] = -1;
        int r = 1;
        if (i - 1 >= 0 && grid[i - 1][j] == 1) {
            r += search(grid, i - 1, j);
        }
        if (i + 1 < grid.length && grid[i + 1][j] == 1) {
            r += search(grid, i + 1, j);
        }
        if (j - 1 >= 0 && grid[i][j - 1] == 1) {
            r += search(grid, i, j - 1);
        }
        if (j + 1 < grid[0].length && grid[i][j + 1] == 1) {
            r += search(grid, i, j + 1);
        }
        return r;
    }

    /*    三数之和
    给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。

    注意：答案中不可以包含重复的三元组。



    示例：

    给定数组 nums = [-1, 0, 1, 2, -1, -4]，

    满足要求的三元组集合为：
            [
            [-1, 0, 1],
            [-1, -1, 2]
            ]*/
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums.length <= 2) {
            return new ArrayList<>();
        }
        Arrays.sort(nums);
        HashMap<Integer, Integer> pos = new HashMap<>();
        // record each element last position(nearest to array end)
        for (int i = 0; i != nums.length; i++) {
            pos.put(nums[i], i);
        }
        List<List<Integer>> r = new ArrayList();
        int lastFirst = nums[0] - 1, lastSecond = nums[0] - 1;
        for (int i = 0; i != nums.length; i++) {
            if (lastFirst == nums[i]) {
                continue;
            }
            lastFirst = nums[i];
            for (int j = i + 1; j != nums.length; j++) {
                if (nums[j] == lastSecond) {
                    continue;
                }
                lastSecond = nums[j];
                int val = nums[i] + nums[j];
                if (!pos.containsKey(-val) || pos.get(-val) <= j) {
                    continue;
                }
                r.add(Arrays.asList(nums[i], nums[j], -val));
            }
        }
        return r;
    }

    // 给定一个包含 非负数 的数组和一个目标 整数 k，编写一个函数来判断该数组是否含有连续的子数组，其大小至少为 2，且总和为 k 的倍数，即总和为 n*k，其中 n 也是一个整数。
    // 链接：https://leetcode-cn.com/problems/continuous-subarray-sum
    public boolean checkSubarraySum(int[] nums, int k) {
        for (int i = 0; i < nums.length; i++) {
            int sum = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                sum += nums[j];
                if (k == 0) {
                    if (sum == 0) {
                        return true;
                    }
                } else if (sum % k == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<List<Integer>> threeSum2(int[] nums) {
        HashMap<Integer, Integer> cnts = new HashMap<>();
        for (int num : nums) {
            cnts.put(num, cnts.getOrDefault(num, 0) + 1);
        }
        List<List<Integer>> result = new LinkedList<>();
        for (Integer key : cnts.keySet()) {
            if (cnts.get(key) >= 3) {
                if (key == 0) {
                    result.add(Arrays.asList(0, 0, 0));
                }
            }
            if (cnts.get(key) >= 2) {
                if (cnts.getOrDefault(-2 * key, 0) != 0 && key != 0) {
                    result.add(Arrays.asList(key, key, -2 * key));
                }
            }
            for (Integer key2 : cnts.keySet()) {
                if (cnts.getOrDefault(key2, 0) == 0 || key2 == key) {
                    continue;
                }
                if (2 * key2 + key == 0) {
                    if (cnts.get(key2) >= 2) {
                        result.add(Arrays.asList(key, key2, -key - key2));
                    }
                } else {
                    if (cnts.getOrDefault(-key - key2, 0) != 0 && 2 * key + key2 != 0 && -key - key2 < key2) {
                        result.add(Arrays.asList(key, key2, -key - key2));
                    }
                }

            }
            cnts.put(key, 0);
        }
        return result;
    }

    /*    朋友圈
        班上有 N 名学生。其中有些人是朋友，有些则不是。他们的友谊具有是传递性。如果已知 A 是 B 的朋友，B 是 C 的朋友，那么我们可以认为 A 也是 C 的朋友。所谓的朋友圈，是指所有朋友的集合。
        给定一个 N * N 的矩阵 M，表示班级中学生之间的朋友关系。如果M[i][j] = 1，表示已知第 i 个和 j 个学生互为朋友关系，否则为不知道。你必须输出所有学生中的已知的朋友圈总数。
        示例 1:
        输入:
                [[1,1,0],
                [1,1,0],
                [0,0,1]]
        输出: 2
        说明：已知学生0和学生1互为朋友，他们在一个朋友圈。
        第2个学生自己在一个朋友圈。所以返回2。
        示例 2:
        输入:
                [[1,1,0],
                [1,1,1],
                [0,1,1]]
        输出: 1
        说明：已知学生0和学生1互为朋友，学生1和学生2互为朋友，所以学生0和学生2也是朋友，所以他们三个在一个朋友圈，返回1。
        注意：
        N 在[1,200]的范围内。
        对于所有学生，有M[i][i] = 1。
        如果有M[i][j] = 1，则有M[j][i] = 1。*/
    public int findCircleNum(int[][] M) {
        Set<Integer> visited = new HashSet<>();
        int cnts = 0;
        for (int i = 0; i != M.length; i++) {
            if (find(i, M, visited)) {
                cnts++;
            }
        }
        return cnts;
    }

    private boolean find(int i, int[][] m, Set<Integer> visited) {
        if (visited.contains(i)) {
            return false;
        }
        visited.add(i);
        for (int j = 0; j != m[i].length; j++) {
            if (m[i][j] == 1) {
                find(j, m, visited);
            }
        }
        return true;
    }

    // 子数组的最小值之和
    // https://leetcode-cn.com/problems/sum-of-subarray-minimums/
    public int sumSubarrayMins(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }
        int len = A.length;

        int[] left = new int[len];
        int[] right = new int[len];
        LinkedList<Integer> stack = new LinkedList<>();
        for (int i = 0; i < len; i++) {
            while (!stack.isEmpty() && A[i] <= A[stack.peek()]) {
                int poppos = stack.pop();
                right[poppos] = i - poppos;
            }
            if (stack.isEmpty()) {
                left[i] = i;
            } else {
                left[i] = i - stack.peek();
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int pos = stack.pop();
            right[pos] = len - pos;
        }
        long res = 0;
        for (int i = 0; i < len; i++) {
            res += A[i] * (left[i]) * (right[i]);
        }
        return (int) (res % 1000000007);
    }

    /*
        给你一个整数数组 nums，请你将该数组升序排列。
    */
    public int[] sortArray(int[] nums) {
        return quickSort(nums, 0, nums.length - 1);
    }

    private int[] quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int partitionIndex = partition(arr, left, right);
            quickSort(arr, left, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, right);
        }
        return arr;
    }

    private int partition(int[] arr, int left, int right) {
        // 设定基准值（pivot）
        int pivot = left;
        int index = pivot + 1;
        for (int i = index; i <= right; i++) {
            if (arr[i] < arr[pivot]) {
                swap(arr, i, index);
                index++;
            }
        }
        swap(arr, pivot, index - 1);
        return index - 1;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

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
            System.arraycopy(origin, 0, copy, 0, origin.length);
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
