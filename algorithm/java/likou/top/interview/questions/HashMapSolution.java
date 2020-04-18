package likou.top.interview.questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author infear
 */
public class HashMapSolution {
    public static void main(String args[]) {
        new HashMapSolution().fourSumCount(new int[] {1, 2}, new int[] {-2, -1}, new int[] {-1, 2}, new int[] {0, 2});
    }

    /*    四数相加 II
        给定四个包含整数的数组列表 A , B , C , D ,计算有多少个元组 (i, j, k, l) ，使得 A[i] + B[j] + C[k] + D[l] = 0。

        为了使问题简单化，所有的 A, B, C, D 具有相同的长度 N，且 0 ≤ N ≤ 500 。所有整数的范围在 -228 到 228 - 1 之间，最终结果不会超过 231 - 1 。

        例如:

        输入:
        A = [ 1, 2]
        B = [-2,-1]
        C = [-1, 2]
        D = [ 0, 2]

        输出:
                2

        解释:
        两个元组如下:
                1. (0, 0, 0, 1) -> A[0] + B[0] + C[0] + D[1] = 1 + (-2) + (-1) + 2 = 0
                2. (1, 1, 0, 0) -> A[1] + B[1] + C[0] + D[0] = 2 + (-1) + (-1) + 0 = 0*/
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        Map<Integer, Integer> AB = new HashMap<>();
        for (int i = 0; i != A.length; i++) {
            for (int j = 0; j != B.length; j++) {
                Integer old = AB.getOrDefault(A[i] + B[j], 0);
                AB.put(A[i] + B[j], old + 1);
            }
        }

        Map<Integer, Integer> CD = new HashMap<>();
        for (int i = 0; i != C.length; i++) {
            for (int j = 0; j != D.length; j++) {
                Integer old = CD.getOrDefault(C[i] + D[j], 0);
                CD.put(C[i] + D[j], old + 1);
            }
        }

        Integer result = 0;
        for (Integer part : AB.keySet()) {
            Integer count1 = AB.get(part);
            Integer count2 = CD.getOrDefault(-part, 0);
            result += count1 * count2;
        }
        return result;
    }

    public int titleToNumber(String s) {
        int result = 0;
        int base = 26;
        for (char c : s.toCharArray()) {
            int value = c - 'A' + 1;
            result = result * base + value;
        }
        return result;
    }

    /*    常数时间插入、删除和获取随机元素
        设计一个支持在平均 时间复杂度 O(1) 下，执行以下操作的数据结构。

        insert(val)：当元素 val 不存在时，向集合中插入该项。
        remove(val)：元素 val 存在时，从集合中移除该项。
        getRandom：随机返回现有集合中的一项。每个元素应该有相同的概率被返回。*/
    class RandomizedSet {
        ArrayList<Integer> eles;
        HashMap<Integer, Integer> pos;

        /**
         * Initialize your data structure here.
         */
        public RandomizedSet() {
            eles = new ArrayList<>();
            pos = new HashMap<>();
        }

        /**
         * Inserts a value to the set. Returns true if the set did not already contain the specified element.
         */
        public boolean insert(int val) {
            if (!pos.containsKey(val)) {
                pos.put(val, eles.size());
                eles.add(val);
                return true;
            }
            return false;
        }

        /**
         * Removes a value from the set. Returns true if the set contained the specified element.
         */
        public boolean remove(int val) {
            if (pos.containsKey(val)) {
                int oldPos = pos.get(val);
                int lastEle = eles.get(eles.size() - 1);
                eles.set(oldPos, lastEle);
                eles.remove(eles.size() - 1);
                pos.put(lastEle, oldPos);
                pos.remove(val);
                return true;
            }
            return false;

        }

        /**
         * Get a random element from the set.
         */
        public int getRandom() {
            return eles.get(new Random().nextInt(eles.size()));
        }
    }
}
