package likou.top.interview.questions;

/**
 * @author infear
 */
public class BeginningSolution {
/*    给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。
    你可以假设数组是非空的，并且给定的数组总是存在多数元素。
    示例 1:
    输入: [3,2,3]
    输出: 3
    示例 2:
    输入: [2,2,1,1,1,2,2]
    输出: 2*/

    /*    给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
        说明：
        你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
        示例 1:
        输入: [2,2,1]
        输出: 1
        示例 2:

        输入: [4,1,2,1,2]
        输出: 4*/
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int i = 0; i != nums.length; i++) {
            result = result ^ nums[i];
        }
        return result;
    }

    /*    多数元素
        给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。
        你可以假设数组是非空的，并且给定的数组总是存在多数元素。
        示例 1:

        输入: [3,2,3]
        输出: 3
        示例 2:

        输入: [2,2,1,1,1,2,2]
        输出: 2*/
    public int majorityElement(int[] nums) {
        int count = 0, flag = -1;
        for (int i = 0; i != nums.length; i++) {
            if (count == 0) {
                flag = nums[i];
            }
            count = (nums[i] == flag) ? count + 1 : count - 1;
        }
        return flag;
    }

    /*    编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target。该矩阵具有以下特性：
        每行的元素从左到右升序排列。
        每列的元素从上到下升序排列。
        示例:
        现有矩阵 matrix 如下：

                [
                [1,   4,  7, 11, 15],
                [2,   5,  8, 12, 19],
                [3,   6,  9, 16, 22],
                [10, 13, 14, 17, 24],
                [18, 21, 23, 26, 30]
                ]
        给定 target = 5，返回 true。
        给定 target = 20，返回 false。*/
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        int m = matrix.length, n = matrix[0].length;
        int i = m - 1;
        int j = 0;
        while (i >= 0 && j < n) {
            if (matrix[i][j] == target) {
                return true;
            } else if (matrix[i][j] > target) {
                i--;
            } else {
                j++;
            }
        }
        return false;
    }

    /*    给你两个有序整数数组 nums1 和 nums2，请你将 nums2 合并到 nums1 中，使 num1 成为一个有序数组。
        说明:
        初始化 nums1 和 nums2 的元素数量分别为 m 和 n 。
        你可以假设 nums1 有足够的空间（空间大小大于或等于 m + n）来保存 nums2 中的元素。
        示例:
        输入:
        nums1 = [1,2,3,0,0,0], m = 3
        nums2 = [2,5,6],       n = 3

        输出: [1,2,2,3,5,6]*/
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = 0, j = 0;
        while (j != n && i != m) {
            if (nums2[j] <= nums1[i]) {
                int k = j;
                while (k != n && nums2[k] <= nums1[i]) {
                    k++;
                }
                /* A[i + k - j,m + k - j) = A[i, m)*/
                int index = m + k - j - 1;
                while (index >= i + k - j) {
                    nums1[index] = nums1[index - k + j];
                    index--;
                }
                /* A[i,i + k - j) = B[j, k)*/
                index = 0;
                while (index != k - j) {
                    nums1[i + index] = nums2[j + index];
                    index++;
                }
                m += k - j;
                i += k - j;
                j = k;
            } else {
                i++;
                if (i == m) {
                    break;
                }
            }
        }
        int index = 0;
        while (index != n - j) {
            nums1[m + index] = nums2[j + index];
            index++;
        }
    }

    public void merge2(int[] nums1, int m, int[] nums2, int n) {
        int i = 0, j = i, end = m;
        // 目标：把所有nums2中的元素插入nums1中，插入结束排序就完成
        while (j != n) {
            // 找到第一个大于nums2[j]的元素
            while (i != end && nums1[i] < nums2[j]) {
                i++;
            }
            // 没有找到，把nums2中的元素拷贝到nums1的末尾
            if (i == end) {
                System.arraycopy(nums2, j, nums1, end, n - j);
                return;
            }
            // 找到最后一个小于nums1[i]的元素
            int k = j;
            while (k != n && nums2[k] <= nums1[i]) {
                k++;
            }
            // nums1[i:end) 后移k-j个位置，给nums2中的元素腾出空间
            System.arraycopy(nums1, i, nums1, i + k - j, end - i);
            // nums2[j:k) 拷贝到 num1[i:i+k-j)
            System.arraycopy(nums2, j, nums1, i, k - j);
            // nums1的指针i后移，由于长度变大，end也要后移
            i += k - j;
            end += k - j;
            // nums2的指针j后移
            j = k;
        }
    }

    /*你将获得 K 个鸡蛋，并可以使用一栋从 1 到 N  共有 N 层楼的建筑。
    每个蛋的功能都是一样的，如果一个蛋碎了，你就不能再把它掉下去。
    你知道存在楼层 F ，满足 0 <= F <= N 任何从高于 F 的楼层落下的鸡蛋都会碎，从 F 楼层或比它低的楼层落下的鸡蛋都不会破。
    每次移动，你可以取一个鸡蛋（如果你有完整的鸡蛋）并把它从任一楼层 X 扔下（满足 1 <= X <= N）。
    你的目标是确切地知道 F 的值是多少。
    无论 F 的初始值如何，你确定 F 的值的最小移动次数是多少？
    示例 1：
    输入：K = 1, N = 2
    输出：2
    解释：
    鸡蛋从 1 楼掉落。如果它碎了，我们肯定知道 F = 0 。
    否则，鸡蛋从 2 楼掉落。如果它碎了，我们肯定知道 F = 1 。
    如果它没碎，那么我们肯定知道 F = 2 。
    因此，在最坏的情况下我们需要移动 2 次以确定 F 是多少。

    示例 2：
    输入：K = 2, N = 6
    输出：3

    示例 3：
    输入：K = 3, N = 14
    输出：4*/
    public int superEggDrop(int K, int N) {
        if (K == 1) {
            return N;
        }
        if (N == 1) {
            return 1;
        }
        int T = N % 2 == 0 ? N / 2 : N / 2 + 1;
        return Math.max(superEggDrop(K - 1, T - 1), superEggDrop(K, N - T)) + 1;
    }
}
