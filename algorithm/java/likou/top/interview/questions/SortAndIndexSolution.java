package likou.top.interview.questions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author infear
 */
public class SortAndIndexSolution {
    public static void main(String args[]) {
        new SortAndIndexSolution().largestNumber(new int[] {10, 2});
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
}
