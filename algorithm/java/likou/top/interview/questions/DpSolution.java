package likou.top.interview.questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import leetcode.TreeNode;

/**
 * @author infear
 */
public class DpSolution {

    public static void main(String args[]) {
        new DpSolution().lengthOfLIS(new int[] {4, 10, 4, 3, 8, 9});
    }

    /*    完全平方数
        给定正整数 n，找到若干个完全平方数（比如 1, 4, 9, 16, ...）使得它们的和等于 n。你需要让组成和的完全平方数的个数最少。

        示例 1:

        输入: n = 12
        输出: 3
        解释: 12 = 4 + 4 + 4.
        示例 2:

        输入: n = 13
        输出: 2
        解释: 13 = 4 + 9.*/
    public int numSquares(int n) {
        int arr[] = new int[n + 1];
        arr[0] = 0;
        for (int k = 1; k != n + 1; k++) {
            int root = (int) Math.sqrt(k);
            int min = k;
            for (int i = 1; i <= root; i++) {
                min = Math.min(min, arr[k - i * i]);
            }
            arr[k] = min + 1;
        }
        return arr[n];
    }

    /*    二叉树中的最大路径和
        给定一个非空二叉树，返回其最大路径和。

        本题中，路径被定义为一条从树中任意节点出发，达到任意节点的序列。该路径至少包含一个节点，且不一定经过根节点。

        示例 1:

        输入: [1,2,3]

                1
                / \
                2   3

        输出: 6
        示例 2:

        输入: [-10,9,20,null,null,15,7]

                -10
                / \
                9  20
                /  \
                15   7

        输出: 42*/
    public int maxPathSum(TreeNode root) {
        // use array to record the max result during the "calMaxPathFrom" recursive phrase.
        int max[] = {Integer.MIN_VALUE};
        calMaxPathFrom(root, max);
        return max[0];
    }

    private int calMaxPathFrom(TreeNode root, int[] max) {
        if (root == null) {
            return 0;
        }
        int left = calMaxPathFrom(root.left, max), right = calMaxPathFrom(root.right, max);
        int tmp = Math.max(Math.max(root.val, root.val + left), Math.max(root.val + right, root.val + left + right));
        max[0] = Math.max(max[0], tmp);
        return Math.max(Math.max(root.val, root.val + left), root.val + right);
    }

    /*    最长连续序列
        给定一个未排序的整数数组，找出最长连续序列的长度。

        要求算法的时间复杂度为 O(n)。

        示例:

        输入: [100, 4, 200, 1, 3, 2]
        输出: 4
        解释: 最长连续序列是 [1, 2, 3, 4]。它的长度为 4。*/
    public int longestConsecutive(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int ele : nums) {
            set.add(ele);
        }
        int max = 0;
        for (int ele : nums) {
            if (!set.contains(ele)) {
                continue;
            }
            int cur = 1;
            int copy = ele + 1;
            while (set.contains(copy)) {
                set.remove(copy++);
                cur++;
            }
            copy = ele - 1;
            while (set.contains(copy)) {
                set.remove(copy--);
                cur++;
            }
            max = Math.max(cur, max);
            // counts of remainder element is not enough, directly return current result.
            if (max >= set.size()) {
                return max;
            }
        }
        return max;
    }

    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int[] result = new int[nums.length];
        int cur = 0;
        for (int i = 0; i != nums.length; i++) {
            int max = 0;
            for (int j = 0; j <= i; j++) {
                if (nums[j] >= nums[i] && j != i) {
                    continue;
                }
                max = Math.max(max, result[j] + 1);
            }
            result[i] = max;
            cur = Math.max(cur, max);
        }
        return cur;
    }

    /*    至少有K个重复字符的最长子串
        找到给定字符串（由小写字符组成）中的最长子串 T ， 要求 T 中的每一字符出现次数都不少于 k 。输出 T 的长度。

        示例 1:

        输入:
        s = "aaabb", k = 3

        输出:
                3

        最长子串为 "aaa" ，其中 'a' 重复了 3 次。
        示例 2:

        输入:
        s = "ababbc", k = 2

        输出:
                5

        最长子串为 "ababb" ，其中 'a' 重复了 2 次， 'b' 重复了 3 次。*/
    public int longestSubstring(String s, int k) {
        if (s.isEmpty()) {
            return 0;
        }
        char[] array = s.toCharArray();
        return findLongest(array, 0, array.length, k);
    }

    private int findLongest(char[] array, int start, int end, int k) {
        if (start >= end) {
            return 0;
        }
        HashMap<Integer, List<Integer>> counts = new HashMap<>();
        for (int index = start; index != end; index++) {
            counts.putIfAbsent((int) array[index], new ArrayList<>());
            counts.get((int) array[index]).add(index);
        }
        List<Integer> allPos = new ArrayList<>();
        for (Integer key : counts.keySet()) {
            if (counts.get(key).size() >= k) {
                continue;
            }
            allPos.addAll(counts.get(key));
        }
        if (allPos.isEmpty()) {
            return end - start;
        }
        Collections.sort(allPos);
        int max = 0;
        int s = start;
        for (int pos : allPos) {
            max = Math.max(max, findLongest(array, s, pos, k));
            s = pos + 1;
        }
        max = Math.max(max, findLongest(array, allPos.get(allPos.size() - 1) + 1, end, k));
        return max;
    }

    /*    打家劫舍
        你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。

        给定一个代表每个房屋存放金额的非负整数数组，计算你在不触动警报装置的情况下，能够偷窃到的最高金额。

        示例 1:

        输入: [1,2,3,1]
        输出: 4
        解释: 偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
        偷窃到的最高金额 = 1 + 3 = 4 。
        示例 2:

        输入: [2,7,9,3,1]
        输出: 12
        解释: 偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
        偷窃到的最高金额 = 2 + 9 + 1 = 12 。*/
    public int rob(int[] nums) {
        // 多两个元素用于计算r[nums.length-1]，避免特殊处理
        int[] r = new int[nums.length + 2];
        for (int i = nums.length - 1; i != -1; i--) {
            r[i] = Math.max(nums[i] + r[i + 2], r[i + 1]);
        }
        return r[0];
    }

    /*    零钱兑换
        给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。



        示例 1:

        输入: coins = [1, 2, 5], amount = 11
        输出: 3
        解释: 11 = 5 + 5 + 1
        示例 2:

        输入: coins = [2], amount = 3
        输出: -1*/
    public int coinChange(int[] coins, int amount) {
        ArrayList<Integer> eles = new ArrayList<>();
        for (Integer coin : coins) {
            eles.add(coin);
        }
        Collections.sort(eles, (o1, o2) -> o1 > o2 ? -1 : (o1 == o2 ? 0 : 1));
        HashMap<Integer, Integer> sub = new HashMap<>();
        sub.put(0, 0);
        change(eles, amount, sub);
        return sub.get(amount);
    }

    private void change(ArrayList<Integer> eles, int amount,
                        HashMap<Integer, Integer> sub) {
        if (sub.containsKey(amount)) {
            return;
        }
        sub.put(amount, -1);
        for (Integer ele : eles) {
            if (ele > amount) {
                continue;
            }
            change(eles, amount - ele, sub);
            int cur = sub.get(amount);
            if (sub.get(amount - ele) != -1) {
                if (cur == -1) {
                    sub.put(amount, sub.get(amount - ele) + 1);
                } else {
                    sub.put(amount, Math.min(cur, sub.get(amount - ele) + 1));
                }
            }
        }
    }

    public int coinChange2(int[] coins, int amount) {
        int r[] = new int[amount + 1];
        r[0] = 0;
        for (int i = 1; i <= amount; i++) {
            r[i] = Integer.MAX_VALUE;
            for (int coin : coins) {
                if ((i > coin && r[i - coin] != -1) || i == coin) {
                    r[i] = Math.min(r[i], r[i - coin] + 1);
                }
            }
            if (r[i] == Integer.MAX_VALUE) {
                r[i] = -1;
            }
        }
        return r[amount];
    }

    /*    不同路径
        一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
        机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
        问总共有多少条不同的路径？
        示例 1:

        输入: m = 3, n = 2
        输出: 3
        解释:
        从左上角开始，总共有 3 条路径可以到达右下角。
        1. 向右 -> 向右 -> 向下
        2. 向右 -> 向下 -> 向右
        3. 向下 -> 向右 -> 向右

        示例 2:
        输入: m = 7, n = 3
        输出: 28
        提示：
        1 <= m, n <= 100
        题目数据保证答案小于等于 2 * 10 ^ 9*/
    public int uniquePaths(int m, int n) {
        int arr[][] = new int[m][n];
        arr[0][0] = 1;
        for (int i = 1; i < n; i++) {
            arr[0][i] = 1;
        }
        for (int i = 1; i < m; i++) {
            arr[i][0] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                arr[i][j] = arr[i - 1][j] + arr[i][j - 1];
            }
        }
        return arr[m - 1][n - 1];
    }


/*    跳跃游戏
    给定一个非负整数数组，你最初位于数组的第一个位置。

    数组中的每个元素代表你在该位置可以跳跃的最大长度。

    判断你是否能够到达最后一个位置。

    示例 1:

    输入: [2,3,1,1,4]
    输出: true
    解释: 我们可以先跳 1 步，从位置 0 到达 位置 1, 然后再从位置 1 跳 3 步到达最后一个位置。
    示例 2:

    输入: [3,2,1,0,4]
    输出: false
    解释: 无论怎样，你总会到达索引为 3 的位置。但该位置的最大跳跃长度是 0 ， 所以你永远不可能到达最后一个位置。*/

    public boolean canJump(int[] nums) {
        if (nums.length == 0) {
            return false;
        }
        boolean r[] = new boolean[nums.length];
        r[nums.length - 1] = true;
        for (int i = nums.length - 2; i != -1; i--) {
            for (int j = 1; j <= nums[i]; j++) {
                if (i + j >= nums.length - 1 || r[i + j]) {
                    r[i] = true;
                    break;
                }
            }
        }
        return r[0];
    }

    /*    矩阵中的最长递增路径
        给定一个整数矩阵，找出最长递增路径的长度。

        对于每个单元格，你可以往上，下，左，右四个方向移动。 你不能在对角线方向上移动或移动到边界外（即不允许环绕）。

        示例 1:

        输入: nums =
                [
                [9,9,4],
                [6,6,8],
                [2,1,1]
                ]
        输出: 4
        解释: 最长递增路径为 [1, 2, 6, 9]。
        示例 2:

        输入: nums =
                [
                [3,4,5],
                [3,2,6],
                [2,2,1]
                ]
        输出: 4
        解释: 最长递增路径是 [3, 4, 5, 6]。注意不允许在对角线方向上移动。*/
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix.length == 0) {
            return 0;
        }
        //visited有两个作用：1.判断是否访问过，2.存储当前格子的最长递增长度
        int[][] visited = new int[matrix.length][matrix[0].length];
        int max = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                //这里先做一次比较找出max，可以避免最后再去遍历一个visited数组
                dfs(i, j, matrix, visited);
                max = Math.max(max, visited[i][j]);
            }
        }
        return max;
    }

    public int dfs(int i, int j, int[][] matrix, int[][] visited) {
        if (visited[i][j] > 0) {
            return visited[i][j];
        }
        int max = 0;
        //这里分别去判断4周是否比当前数小，然后去递归遍历
        if (i - 1 >= 0 && matrix[i - 1][j] < matrix[i][j]) {
            max = Math.max(max, dfs(i - 1, j, matrix, visited));
        }
        if (i + 1 < matrix.length && matrix[i + 1][j] < matrix[i][j]) {
            max = Math.max(max, dfs(i + 1, j, matrix, visited));
        }
        if (j - 1 >= 0 && matrix[i][j - 1] < matrix[i][j]) {
            max = Math.max(max, dfs(i, j - 1, matrix, visited));
        }
        if (j + 1 < matrix[0].length && matrix[i][j + 1] < matrix[i][j]) {
            max = Math.max(max, dfs(i, j + 1, matrix, visited));
        }

        visited[i][j] = max + 1;
        return max + 1;
    }

}
