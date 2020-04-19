package likou.top.interview.questions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode-cn.com/explore/interview/card/top-interview-questions/263/simulation/1123/
 * 模拟面试
 *
 * @author infear
 */
public class SimulationTest {
    public static void main(String args[]) {
        LRUCache cache = new LRUCache(16);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1));
    }

    /*    快乐数
        编写一个算法来判断一个数 n 是不是快乐数。

                「快乐数」定义为：对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和，然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。如果 可以变为  1，那么这个数就是快乐数。

        如果 n 是快乐数就返回 True ；不是，则返回 False 。



        示例：

        输入：19
        输出：true
        解释：
                12 + 92 = 82
                82 + 22 = 68
                62 + 82 = 100
                12 + 02 + 02 = 1*/
    public boolean isHappy(int n) {
        Set<Integer> history = new HashSet<>();
        while (true) {
            String str = String.valueOf(n);
            int result = 0;
            for (char c : str.toCharArray()) {
                result += (c - '0') * (c - '0');
            }
            if (result == 1) {
                return true;
            }
            if (history.contains(result)) {
                return false;
            }
            history.add(result);
            n = result;
        }
    }

    /*    两整数之和
        不使用运算符 + 和 - ​​​​​​​，计算两整数 ​​​​​​​a 、b ​​​​​​​之和。

        示例 1:

        输入: a = 1, b = 2
        输出: 3
        示例 2:

        输入: a = -2, b = 3
        输出: 1*/
    public int getSum(int a, int b) {
        if (b == 0) {
            return a;
        }
        return getSum(a ^ b, (a & b) << 1);
    }

    /*    Fizz Buzz
        写一个程序，输出从 1 到 n 数字的字符串表示。

                1. 如果 n 是3的倍数，输出“Fizz”；

                2. 如果 n 是5的倍数，输出“Buzz”；

                3.如果 n 同时是3和5的倍数，输出 “FizzBuzz”。

        示例：

        n = 15,

        返回:
                [
                "1",
                "2",
                "Fizz",
                "4",
                "Buzz",
                "Fizz",
                "7",
                "8",
                "Fizz",
                "Buzz",
                "11",
                "Fizz",
                "13",
                "14",
                "FizzBuzz"
                ]*/
    public List<String> fizzBuzz(int n) {
        List<String> result = new ArrayList<>(n);
        for (int i = 1; i <= n; i++) {
            if (i % 15 == 0) {
                result.add("FizzBuzz");
            } else if (i % 3 == 0) {
                result.add("Fizz");
            } else if (i % 5 == 0) {
                result.add("Buzz");
            } else {
                result.add(String.valueOf(i));
            }
        }
        return result;
    }

    /*    加油站
        在一条环路上有 N 个加油站，其中第 i 个加油站有汽油 gas[i] 升。

        你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升。你从其中的一个加油站出发，开始时油箱为空。

        如果你可以绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1。

        说明:

        如果题目有解，该答案即为唯一答案。
        输入数组均为非空数组，且长度相同。
        输入数组中的元素均为非负数。
        示例 1:

        输入:
        gas  = [1,2,3,4,5]
        cost = [3,4,5,1,2]

        输出: 3

        解释:
        从 3 号加油站(索引为 3 处)出发，可获得 4 升汽油。此时油箱有 = 0 + 4 = 4 升汽油
        开往 4 号加油站，此时油箱有 4 - 1 + 5 = 8 升汽油
        开往 0 号加油站，此时油箱有 8 - 2 + 1 = 7 升汽油
        开往 1 号加油站，此时油箱有 7 - 3 + 2 = 6 升汽油
        开往 2 号加油站，此时油箱有 6 - 4 + 3 = 5 升汽油
        开往 3 号加油站，你需要消耗 5 升汽油，正好足够你返回到 3 号加油站。
        因此，3 可为起始索引。
        示例 2:

        输入:
        gas  = [2,3,4]
        cost = [3,4,3]

        输出: -1

        解释:
        你不能从 0 号或 1 号加油站出发，因为没有足够的汽油可以让你行驶到下一个加油站。
        我们从 2 号加油站出发，可以获得 4 升汽油。 此时油箱有 = 0 + 4 = 4 升汽油
        开往 0 号加油站，此时油箱有 4 - 3 + 2 = 3 升汽油
        开往 1 号加油站，此时油箱有 3 - 3 + 3 = 3 升汽油
        你无法返回 2 号加油站，因为返程需要消耗 4 升汽油，但是你的油箱只有 3 升汽油。
        因此，无论怎样，你都不可能绕环路行驶一周。*/
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int total = 0;
        int current = 0;
        int start = 0;

        for (int i = 0; i != gas.length; i++) {
            current += gas[i] - cost[i];
            if (current < 0) {
                start = i + 1;
                total += current;
                current = 0;
            }
        }
        return current + total >= 0 ? start : -1;
    }

    /*    LRU缓存机制
        运用你所掌握的数据结构，设计和实现一个  LRU (最近最少使用) 缓存机制。它应该支持以下操作： 获取数据 get 和 写入数据 put 。

        获取数据 get(key) - 如果密钥 (key) 存在于缓存中，则获取密钥的值（总是正数），否则返回 -1。
        写入数据 put(key, value) - 如果密钥已经存在，则变更其数据值；如果密钥不存在，则插入该组「密钥/数据值」。当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。



        进阶:

        你是否可以在 O(1) 时间复杂度内完成这两种操作？



        示例:

        LRUCache cache = new LRUCache( 2 *//* 缓存容量 *//* );

cache.put(1, 1);
cache.put(2, 2);
cache.get(1);       // 返回  1
cache.put(3, 3);    // 该操作会使得密钥 2 作废
cache.get(2);       // 返回 -1 (未找到)
cache.put(4, 4);    // 该操作会使得密钥 1 作废
cache.get(1);       // 返回 -1 (未找到)
cache.get(3);       // 返回  3
cache.get(4);       // 返回  4*/
    static class LRUCache extends LinkedHashMap<Integer, Integer> {
        private int cacheSize;

        public LRUCache(int cacheSize) {
            super(16, 1, true);
            this.cacheSize = cacheSize;
        }

        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return size() >= cacheSize;
        }

        public Integer get(int key) {
            return getOrDefault(key, -1);
        }

        public void put(int key, int value) {
            put(key, value);
        }
    }
}
