package leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * https://leetcode.com/problems/next-greater-element-i/#/description
 *
 * @author infear
 */
public class NextGreaterElement {
    public int[] nextGreaterElement(int[] findNums, int[] nums) {
        if (findNums == null || findNums.length == 0) {
            return null;
        }

        // 存储nums中每个元素的first-greater
        Stack<Integer> s = new Stack();
        Map<Integer, Integer> m = new HashMap();

        /*
        遍历nums,用Stack来存储：
        如果当前元素ele小于栈顶（或者栈为空）则直接入栈；
        否则逐个弹出栈顶元素，并标记每个弹出元素的first-greater为ele，直到栈顶大于ele或者栈为空
        */
        for (int ele : nums) {
            if (s.isEmpty()) {
                s.push(ele);
            } else {
                if (s.peek() > ele) {
                    s.push(ele);
                } else {
                    while (!s.isEmpty() && s.peek() < ele) {
                        m.put(s.pop(), ele);
                    }
                    s.push(ele);
                }
            }
        }

        int[] r = new int[findNums.length];
        int i = 0;
        for (int ele : findNums) {
            r[i++] = m.getOrDefault(ele, -1);
        }
        return r;

    }
}
