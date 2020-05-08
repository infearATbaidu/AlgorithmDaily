package likou.top.interview.questions;

import java.util.Arrays;
import java.util.Deque;
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
        System.out.println(new StackHeapQueueSolution().findKthLargest2(new int[] {7, 7, 7, 7, 7, 7, 7}, 1));
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

    public int findKthLargest2(int[] nums, int k) {
        return findKthLargestRur(nums, 0, nums.length, k);
    }

    private int findKthLargestRur(int[] nums, int start, int end, int k) {
        if (start == end - 1) {
            return nums[start];
        }
        int target = nums[start];
        int i = start + 1, j = end - 1;
        while (true) {
            while (i < end && nums[i] >= target) {
                i++;
            }
            // the ele at start position is min, so break
            if (i == end) {
                break;
            }
            while (j > i && nums[j] <= target) {
                j--;
            }
            if (i < j) {
                swap(nums, i, j);
            } else {
                break;
            }
        }
        // make the ele at start index to correct index.
        swap(nums, i - 1, start);
        if (i - start == k) {
            return target;
        }
        if (k > i - start) {
            return findKthLargestRur(nums, i, end, k - (i - start));
        } else {
            return findKthLargestRur(nums, start, i - 1, k);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
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

    /* 逆波兰表达式求值
     根据逆波兰表示法，求表达式的值。

     有效的运算符包括 +, -, *, / 。每个运算对象可以是整数，也可以是另一个逆波兰表达式。

     说明：

     整数除法只保留整数部分。
     给定逆波兰表达式总是有效的。换句话说，表达式总会得出有效数值且不存在除数为 0 的情况。
     示例 1：

     输入: ["2", "1", "+", "3", "*"]
     输出: 9
     解释: ((2 + 1) * 3) = 9*/
    public int evalRPN(String[] tokens) {
        Stack<Integer> ele = new Stack();
        Set<String> ops = new HashSet<>(Arrays.asList("+", "-", "*", "/"));
        for (String token : tokens) {
            if (!ops.contains(token)) {
                ele.push(Integer.valueOf(token));
            } else {
                Integer i2 = ele.pop(), i1 = ele.pop();
                ele.push(cal(token.charAt(0), i2, i1));
            }
        }
        return ele.pop();
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

    public int[] topKFrequent(int[] nums, int k) {
        // each number appear times.
        HashMap<Integer, Integer> counts = new HashMap<>();
        // appear times map to number.
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
        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    /*    给定一个数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
        返回滑动窗口中的最大值。
        进阶：
        你能在线性时间复杂度内解决此题吗？
        示例:

        输入: nums = [1,3,-1,-3,5,3,6,7], 和 k = 3
        输出: [3,3,5,5,6,7]
        解释:

        滑动窗口的位置                最大值
    ---------------               -----
            [1  3  -1] -3  5  3  6  7       3
            1 [3  -1  -3] 5  3  6  7       3
            1  3 [-1  -3  5] 3  6  7       5
            1  3  -1 [-3  5  3] 6  7       5
            1  3  -1  -3 [5  3  6] 7       6
            1  3  -1  -3  5 [3  6  7]      7*/
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];
        int index = 0;
        Deque<Integer> queue = new LinkedList();
        for (int i = 0; i != nums.length; i++) {
            if (!queue.isEmpty()) {
                if (!queue.isEmpty() && i - queue.getFirst() >= k) {
                    queue.removeFirst();
                }
                while (!queue.isEmpty() && nums[queue.getLast()] < nums[i]) {
                    queue.removeLast();
                }
            }
            queue.add(i);
            if (i >= k - 1) {
                result[index++] = nums[queue.getFirst()];
            }
        }
        return result;
    }

    public boolean guess(int[][] matrix, int g, int n, int k) {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            int L = 0;
            int R = n - 1;
            int ans = 0;
            while (L <= R) {
                int mid = L + (R - L) / 2;
                //若某一行值小于g，则应该是最后一个元素的下标 + 1.
                if (matrix[i][mid] < g) {
                    ans = mid + 1;
                    L = mid + 1;
                } else {
                    R = mid - 1;
                }
            }
            sum += ans;
        }
        return k > sum;
    }

    /*    有序矩阵中第K小的元素
        给定一个 n x n 矩阵，其中每行和每列元素均按升序排序，找到矩阵中第k小的元素。
        请注意，它是排序后的第 k 小元素，而不是第 k 个不同的元素。



        示例:

        matrix = [
                [ 1,  5,  9],
                [10, 11, 13],
                [12, 13, 15]
                ],
        k = 8,

        返回 13。


        提示：
        你可以假设 k 的值永远是有效的, 1 ≤ k ≤ n2 。*/
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int L = matrix[0][0];
        int R = matrix[n - 1][n - 1];
        int ans = 0;
        while (L <= R) {
            int mid = L + (R - L) / 2;
            if (guess(matrix, mid, n, k)) {
                ans = mid;
                L = mid + 1;
            } else {
                R = mid - 1;
            }
        }
        return ans;
    }

    /*    设计一个支持 push，pop，top 操作，并能在常数时间内检索到最小元素的栈。

        push(x) -- 将元素 x 推入栈中。
        pop() -- 删除栈顶的元素。
        top() -- 获取栈顶元素。
        getMin() -- 检索栈中的最小元素。*/
    class MinStack {
        private Stack<Integer> ele;
        private Stack<Integer> min;

        /**
         * initialize your data structure here.
         */
        public MinStack() {
            ele = new Stack<>();
            min = new Stack<>();
        }

        public void push(int x) {
            ele.push(x);
            if (min.size() != 0) {
                min.push(Math.min(x, min.peek()));
            } else {
                min.push(x);
            }
        }

        public void pop() {
            ele.pop();
            min.pop();
        }

        public int top() {
            return ele.peek();
        }

        public int getMin() {
            return min.peek();
        }
    }
}
