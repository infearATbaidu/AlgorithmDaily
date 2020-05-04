package likou.top.interview.questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author infear
 */
public class SortAndIndexSolution {
    public static void main(String args[]) {
        new SortAndIndexSolution().search(new int[] {1, 3}, 3);
    }

    /*    最大数
        给定一组非负整数，重新排列它们的顺序使之组成一个最大的整数。

        示例 1:

        输入: [10,2]
        输出: 210
        示例 2:

        输入: [3,30,34,5,9]
        输出: 9534330
        说明: 输出结果可能非常大，所以你需要返回一个字符串而不是整数。*/
    public String largestNumber(int[] nums) {
        List<String> ele = Arrays.stream(nums).mapToObj(String::valueOf).collect(Collectors.toList());
        ele.sort((o1, o2) -> {
            if (o1.startsWith(o2) || o2.startsWith(o1)) {
                return -((o1 + o2).compareTo(o2 + o1));
            }
            return -o1.compareTo(o2);
        });
        StringBuilder stringBuilder = new StringBuilder();
        boolean zero = true;
        for (String s : ele) {
            if (s.equals("0") && zero) {
                continue;
            }
            zero = false;
            stringBuilder.append(s);
        }
        if (zero) {
            return "0";
        }
        return stringBuilder.toString();
    }

    /*    寻找重复数
        给定一个包含 n + 1 个整数的数组 nums，其数字都在 1 到 n 之间（包括 1 和 n），可知至少存在一个重复的整数。假设只有一个重复的整数，找出这个重复的数。

        示例 1:

        输入: [1,3,4,2,2]
        输出: 2
        示例 2:

        输入: [3,1,3,4,2]
        输出: 3
        说明：

        不能更改原数组（假设数组是只读的）。
        只能使用额外的 O(1) 的空间。
        时间复杂度小于 O(n2) 。
        数组中只有一个重复的数字，但它可能不止重复出现一次。*/
    public int findDuplicate(int[] nums) {
        int slow = 0, fast = 0, t = 0;
        while (true) {
            slow = nums[slow];
            fast = nums[nums[fast]];
            if (slow == fast) {
                break;
            }
        }
        while (true) {
            slow = nums[slow];
            t = nums[t];
            if (slow == t) {
                break;
            }
        }
        return slow;
    }

/*    寻找峰值
    峰值元素是指其值大于左右相邻值的元素。

    给定一个输入数组 nums，其中 nums[i] ≠ nums[i+1]，找到峰值元素并返回其索引。

    数组可能包含多个峰值，在这种情况下，返回任何一个峰值所在位置即可。

    你可以假设 nums[-1] = nums[n] = -∞。

    示例 1:

    输入: nums = [1,2,3,1]
    输出: 2
    解释: 3 是峰值元素，你的函数应该返回其索引 2。
    示例 2:

    输入: nums = [1,2,1,3,5,6,4]
    输出: 1 或 5
    解释: 你的函数可以返回索引 1，其峰值元素为 2；
    或者返回索引 5， 其峰值元素为 6。
    说明:

    你的解法应该是 O(logN) 时间复杂度的。*/

    public int findPeakElement(int[] nums) {
        if (nums == null) {
            return -1;
        }
        int s = 0, e = nums.length, mid;
        while (true) {
            mid = (s + e) / 2;
            if (s == mid) {
                return s;
            }
            if (nums[mid] > nums[mid - 1]) {
                s = mid;
            } else {
                e = mid;
            }
        }
    }

    /*    摆动排序 II
        给定一个无序的数组 nums，将它重新排列成 nums[0] < nums[1] > nums[2] < nums[3]... 的顺序。

        示例 1:

        输入: nums = [1, 5, 1, 1, 6, 4]
        输出: 一个可能的答案是 [1, 4, 1, 5, 1, 6]
        示例 2:

        输入: nums = [1, 3, 2, 2, 3, 1]
        输出: 一个可能的答案是 [2, 3, 1, 3, 1, 2]
        说明:
        你可以假设所有输入都会得到有效的结果。

        进阶:
        你能用 O(n) 时间复杂度和 / 或原地 O(1) 额外空间来实现吗？*/
    public void wiggleSort(int[] nums) {
        Arrays.sort(nums);
        int copy[] = new int[nums.length];
        int j = nums.length / 2;
        int k = nums.length - 1;
        if (nums.length % 2 == 0) {
            j = nums.length / 2 - 1;
        }
        for (int i = 0; i != nums.length; i++) {
            if (i % 2 == 0) {
                copy[i] = nums[j--];
            } else {
                copy[i] = nums[k--];
            }
        }
        for (int i = 0; i != nums.length; i++) {
            nums[i] = copy[i];
        }
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    /*    计算右侧小于当前元素的个数
        给定一个整数数组 nums，按要求返回一个新数组 counts。数组 counts 有该性质： counts[i] 的值是  nums[i] 右侧小于 nums[i] 的元素的数量。

        示例:

        输入: [5,2,6,1]
        输出: [2,1,1,0]
        解释:
                5 的右侧有 2 个更小的元素 (2 和 1).
                2 的右侧仅有 1 个更小的元素 (1).
                6 的右侧有 1 个更小的元素 (1).
                1 的右侧有 0 个更小的元素.*/
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> r = new LinkedList<>();
        if (nums.length == 0) {
            return r;
        }
        r.add(0, 0);
        for (int i = nums.length - 2; i != -1; i--) {
            int ele = nums[i];
            int k = i + 1;
            while (k != nums.length) {
                if (nums[k] < ele) {
                    nums[k - 1] = nums[k];
                    k++;
                } else {
                    break;
                }
            }
            nums[k - 1] = ele;
            r.add(0, k - i - 1);
        }
        return r;
    }

    /*    颜色分类
        给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
    
        此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
    
        注意:
        不能使用代码库中的排序函数来解决这道题。
    
        示例:
    
        输入: [2,0,2,1,1,0]
        输出: [0,0,1,1,2,2]
        进阶：
    
        一个直观的解决方案是使用计数排序的两趟扫描算法。
        首先，迭代计算出0、1 和 2 元素的个数，然后按照0、1、2的排序，重写当前数组。
        你能想出一个仅使用常数空间的一趟扫描算法吗？*/
    public void sortColors(int[] nums) {
        int i = 0;
        while (i != nums.length) {
            if (nums[i] == 0) {
                i++;
                continue;
            }
            // find first zero.
            if (nums[i] == 1) {
                int j = i;
                while (j != nums.length && nums[j] != 0) {
                    j++;
                }
                if (j != nums.length) {
                    swap(nums, i, j);
                } else {
                    i++;
                }
                continue;
            }
            // find first 0 or 1
            int j = i;
            while (j != nums.length && nums[j] == 2) {
                j++;
            }
            if (j != nums.length) {
                swap(nums, i, j);
            } else {
                i++;
            }
        }
    }

    /*    在排序数组中查找元素的第一个和最后一个位置
        给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。

        你的算法时间复杂度必须是 O(log n) 级别。

        如果数组中不存在目标值，返回 [-1, -1]。

        示例 1:

        输入: nums = [5,7,7,8,8,10], target = 8
        输出: [3,4]
        示例 2:

        输入: nums = [5,7,7,8,8,10], target = 6
        输出: [-1,-1]*/
    public int[] searchRange(int[] nums, int target) {
        int arr[] = new int[2];
        int left = findLastSmaller(nums, 0, nums.length - 1, target);
        if (left + 1 < nums.length && nums[left + 1] == target) {
            // target exist
            arr[0] = left + 1;
        } else {
            arr[0] = -1;
        }
        int right = findFirstLarger(nums, 0, nums.length - 1, target);
        // target exist
        if (right - 1 >= 0 && nums[right - 1] == target) {
            arr[1] = right - 1;
        } else {
            arr[1] = -1;
        }
        return arr;
    }

    private int findFirstLarger(int[] nums, int start, int end, int target) {
        int r = nums.length, i = start, j = end;
        while (i <= j) {
            int mid = i + (j - i) / 2;
            if (nums[mid] <= target) {
                i = mid + 1;
            } else {
                r = mid;
                // mid is already candidate, try more smaller position
                j = mid - 1;
            }
        }
        return r;
    }

    private int findLastSmaller(int[] nums, int start, int end, int target) {
        int r = -1, i = start, j = end;
        while (i <= j) {
            int mid = i + (j - i) / 2;
            if (nums[mid] >= target) {
                j = mid - 1;
            } else {
                r = mid;
                // mid is already candidate, try more larger position
                i = mid + 1;
            }
        }
        return r;
    }

    /*    搜索旋转排序数组
        假设按照升序排序的数组在预先未知的某个点上进行了旋转。

                ( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。

        搜索一个给定的目标值，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。

        你可以假设数组中不存在重复的元素。

        你的算法时间复杂度必须是 O(log n) 级别。

        示例 1:

        输入: nums = [4,5,6,7,0,1,2], target = 0
        输出: 4
        示例 2:

        输入: nums = [4,5,6,7,0,1,2], target = 3
        输出: -1*/
    public int search(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] > nums[end]) {
                if (target > nums[mid] || target < nums[start]) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            } else {
                if (target < nums[mid] || target > nums[end]) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            }
        }
        return -1;
    }

    /*    合并区间
        给出一个区间的集合，请合并所有重叠的区间。

        示例 1:

        输入: [[1,3],[2,6],[8,10],[15,18]]
        输出: [[1,6],[8,10],[15,18]]
        解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
        示例 2:

        输入: [[1,4],[4,5]]
        输出: [[1,5]]
        解释: 区间 [1,4] 和 [4,5] 可被视为重叠区间。*/
    public int[][] merge(int[][] intervals) {
        if (intervals.length == 0) {
            return new int[0][0];
        }
        Arrays.sort(intervals, (o1, o2) -> {
            if (o1[0] < o2[0] || (o1[0] == o2[0] && o1[1] < o2[1])) {
                return -1;
            }
            if (o1[0] == o2[0] && o1[1] == o2[1]) {
                return 0;
            }
            return 1;
        });
        List<int[]> r = new ArrayList<>();
        int[] last = intervals[0];
        for (int i = 1; i != intervals.length; i++) {
            // try merge last and intervals[i]
            int[] cur = intervals[i];
            if (cur[0] > last[1]) {
                r.add(last);
                last = cur;
            } else {
                last[1] = Math.max(cur[1], last[1]);
            }
        }
        // last one
        r.add(last);
        return r.toArray(new int[0][0]);
    }
}
