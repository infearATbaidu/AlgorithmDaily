package likou.top.interview.questions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * @author infear
 */
public class StackHeapQueueSolution {
    public static void main(String args[]) {
        System.out.println(new StackHeapQueueSolution().topKFrequent(new int[] {1, 1, 1, 2, 2, 3}, 2));
    }

    /*数组中的第K个最大元素
    在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。

    示例 1:

    输入: [3,2,1,5,6,4] 和 k = 2
    输出: 5
    示例 2:

    输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
    输出: 4
    说明:

    你可以假设 k 总是有效的，且 1 ≤ k ≤ 数组的长度。*/
    public int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    /*    基本计算器 II
        实现一个基本的计算器来计算一个简单的字符串表达式的值。

        字符串表达式仅包含非负整数，+， - ，*，/ 四种运算符和空格  。 整数除法仅保留整数部分。

        示例 1:

        输入: "3+2*2"
        输出: 7
        示例 2:

        输入: " 3/2 "
        输出: 1
        示例 3:

        输入: " 3+5 / 2 "
        输出: 5*/
    public int calculate(String s) {
        int plus = '+', minus = '-', multiple = '*', divide = '/';
        Stack<Integer> nums = new Stack<>();
        Stack<Integer> ops = new Stack<>();
        int base = 0;
        for (char c : s.toCharArray()) {
            if (c == ' ') {
                continue;
            }
            if (Character.isDigit(c)) {
                base = base * 10 + (c - '0');
                continue;
            }
            nums.push(base);
            base = 0;
            while (!ops.isEmpty()) {
                int lastOp = ops.peek();
                if (lastOp == multiple || lastOp == divide || c == plus || c == minus) {
                    ops.pop();
                    int result = cal(lastOp, nums.pop(), nums.pop());
                    nums.push(result);
                } else {
                    break;
                }
            }
            ops.push((int) c);
        }
        nums.push(base);
        // 形如"1 + 2*3" 因为3后无运算符，所以在上面循环中无法计算，所以需要下面的循环
        while (!ops.isEmpty()) {
            int lastOp = ops.pop();
            int result = cal(lastOp, nums.pop(), nums.pop());
            nums.push(result);
        }
        return nums.pop();
    }

    private int cal(int lastOp, Integer pop, Integer pop1) {
        switch (lastOp) {
            case '+':
                return pop1 + pop;
            case '-':
                return pop1 - pop;
            case '*':
                return pop1 * pop;
            case '/':
                return pop1 / pop;
        }
        return 0;
    }

/*    前 K 个高频元素
    给定一个非空的整数数组，返回其中出现频率前 k 高的元素。

    示例 1:

    输入: nums = [1,1,1,2,2,3], k = 2
    输出: [1,2]
    示例 2:

    输入: nums = [1], k = 1
    输出: [1]
    说明：

    你可以假设给定的 k 总是合理的，且 1 ≤ k ≤ 数组中不相同的元素的个数。
    你的算法的时间复杂度必须优于 O(n log n) , n 是数组的大小。*/

    public List<Integer> topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> counts = new HashMap<>();
        HashMap<Integer, Set<Integer>> countsToNum = new HashMap<>();
        for (int num : nums) {
            counts.putIfAbsent(num, 0);
            int old = counts.get(num);
            counts.put(num, old + 1);
            if (countsToNum.containsKey(old) && countsToNum.getOrDefault(old, new HashSet()).contains(num)) {
                countsToNum.get(old).remove(num);
            }
            countsToNum.putIfAbsent(old + 1, new HashSet<>());
            countsToNum.get(old + 1).add(num);

        }
        int t = nums.length;
        List<Integer> result = new LinkedList<>();
        while (t > 0) {
            Set<Integer> r = countsToNum.getOrDefault(t, null);
            if (r == null) {
                t--;
                continue;
            }
            Iterator<Integer> it = r.iterator();
            while (k != 0 && it.hasNext()) {
                result.add(it.next());
                k--;
            }
            t--;
        }
        return result;
    }
}
