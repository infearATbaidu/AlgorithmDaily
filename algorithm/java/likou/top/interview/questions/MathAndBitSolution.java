package likou.top.interview.questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author infear
 */
public class MathAndBitSolution {
    public static void main(String args[]) {
        int array[][] = new int[][] {{-54, -297}, {-36, -222}, {3, -2}, {30, 53}, {-5, 1}, {-36, -222}, {0, 2}, {1, 3},
                {6, -47}, {0, 4}, {2, 3}, {5, 0}, {48, 128}, {24, 28}, {0, -5}, {48, 128}, {-12, -122}, {-54, -297},
                {-42, -247}, {-5, 0}, {2, 4}, {0, 0}, {54, 153}, {-30, -197}, {4, 5}, {4, 3}, {-42, -247}, {6, -47},
                {-60, -322}, {-4, -2}, {-18, -147}, {6, -47}, {60, 178}, {30, 53}, {-5, 3}, {-42, -247}, {2, -2},
                {12, -22}, {24, 28}, {0, -72}, {3, -4}, {-60, -322}, {48, 128}, {0, -72}, {-5, 3}, {5, 5}, {-24, -172},
                {-48, -272}, {36, 78}, {-3, 3}};
        new MathAndBitSolution().maxPoints(array);
    }

    /*    只出现一次的数字
        给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。

        说明：

        你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？

        示例 1:

        输入: [2,2,1]
        输出: 1
        示例 2:

        输入: [4,1,2,1,2]
        输出: 4*/
    public int singleNumber(int[] nums) {
        int r = 0;
        for (int num : nums) {
            r = r ^ num;
        }
        return r;
    }

    public boolean isPowerOfThree(int n) {
        if (n <= 0) {
            return false;
        }
        int max = 729 * 729 * 729 * 3;
        return max % n == 0;
    }

    /*    阶乘后的零
        给定一个整数 n，返回 n! 结果尾数中零的数量。

        示例 1:

        输入: 3
        输出: 0
        解释: 3! = 6, 尾数中没有零。
        示例 2:

        输入: 5
        输出: 1
        解释: 5! = 120, 尾数中有 1 个零.
                说明: 你算法的时间复杂度应为 O(log n) 。*/
    public int trailingZeroes(int n) {
        if (n < 5) {
            return 0;
        }
        long base = 5;
        long maxPow = 0;
        while (base <= n) {
            base *= 5;
            maxPow = 5 * maxPow + 1;
        }
        base = base / 5;
        int result = 0;
        long piece = base;
        while (piece <= n) {
            result += maxPow;
            piece += base;
        }
        return result + trailingZeroes(n - (int) (piece - base));
    }

    /*    缺失数字
        给定一个包含 0, 1, 2, ..., n 中 n 个数的序列，找出 0 .. n 中没有出现在序列中的那个数。

        示例 1:

        输入: [3,0,1]
        输出: 2
        示例 2:

        输入: [9,6,4,2,3,5,7,0,1]
        输出: 8
        说明:
        你的算法应具有线性时间复杂度。你能否仅使用额外常数空间来实现?*/
    public int missingNumber(int[] nums) {
        int r = 0;
        for (int i = 0; i != nums.length; i++) {
            r += i - nums[i];
        }
        return r + nums.length;
    }

    /*    分数到小数
        给定两个整数，分别表示分数的分子 numerator 和分母 denominator，以字符串形式返回小数。

        如果小数部分为循环小数，则将循环的部分括在括号内。

        示例 1:

        输入: numerator = 1, denominator = 2
        输出: "0.5"
        示例 2:

        输入: numerator = 2, denominator = 1
        输出: "2"
        示例 3:

        输入: numerator = 2, denominator = 3
        输出: "0.(6)"*/
    public String fractionToDecimal(int numerator, int denominator) {
        long n = numerator, d = denominator;
        if (n == 0) {
            return "0";
        }
        boolean sign = true;
        if (n < 0 && d > 0 || n > 0 && d < 0) {
            sign = false;
        }
        if (n < 0) {
            n = -n;
        }
        if (d < 0) {
            d = -d;
        }
        long part1 = n / d, part2 = n % d;
        StringBuilder total = new StringBuilder();
        if (!sign) {
            total.append("-");
        }
        total.append(part1);
        if (part2 == 0) {
            return total.toString();
        }
        total.append(".");
        StringBuilder post = new StringBuilder();
        long cur = part2;
        LinkedHashMap<Long, String> map = new LinkedHashMap();
        while (!map.containsKey(cur)) {
            String value = "";
            long copy = cur;
            copy *= 10;
            while (copy < d) {
                copy = copy * 10;
                value += "0";
            }
            value += copy / d;
            map.put(cur, value);
            cur = copy % d;
            if (cur == 0) {
                for (String v : map.values()) {
                    total.append(v);
                }
                return total.toString();
            }
        }
        // find loop entry
        for (Long key : map.keySet()) {
            if (key.equals(cur)) {
                post.append("(");
            }
            post.append(map.get(key));
        }
        post.append(")");
        total.append(post);
        return total.toString();
    }

    /*    计数质数
        统计所有小于非负整数 n 的质数的数量。

        示例:

        输入: 10
        输出: 4
        解释: 小于 10 的质数一共有 4 个, 它们是 2, 3, 5, 7 。*/
    public int countPrimes(int n) {
        if (n <= 1) {
            return 0;
        }
        int[] ele = new int[n + 1];
        int count = 0;
        for (int i = 2; i != n; i++) {
            if (ele[i] == 1) {
                continue;
            }
            count++;
            int base = 2;
            while (base * i < n) {
                ele[base * i] = 1;
                base++;
            }
        }
        return count;
    }

    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        String str = Integer.toBinaryString(n);
        while (str.length() != 32) {
            str = "0" + str;
        }
        int i = 0, j = str.length() - 1;
        char arr[] = str.toCharArray();
        while (i < j) {
            char tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
            i++;
            j--;
        }
        return Integer.parseUnsignedInt(new String(arr), 2);
    }

    /*    位1的个数
        编写一个函数，输入是一个无符号整数，返回其二进制表达式中数字位数为 ‘1’ 的个数（也被称为汉明重量）。*/
    public int hammingWeight(int n) {
        String str = Integer.toBinaryString(n);
        int cnt = 0;
        for (char c : str.toCharArray()) {
            if (c == '1') {
                cnt++;
            }
        }
        return cnt;
    }

    /*    直线上最多的点数
        给定一个二维平面，平面上有 n 个点，求最多有多少个点在同一条直线上。*/
    public int maxPoints(int[][] points) {
        if (points.length == 0) {
            return 0;
        }
        // 去掉重复的点，统计每个点的出现次数
        HashMap<Pair, Integer> pointDuplicateCnts = new HashMap<>();
        for (int[] pair : points) {
            Pair<Long> point = new Pair(Long.valueOf(pair[0]), Long.valueOf(pair[1]));
            pointDuplicateCnts.put(point, pointDuplicateCnts.getOrDefault(point, 0) + 1);
        }
        List<Pair> pointSet = new ArrayList<>(pointDuplicateCnts.keySet());
        if (pointSet.size() == 1) {
            return points.length;
        }
        HashMap<Pair, Integer> slopeCounts = new HashMap();
        int index = 0, max = 1;
        // 遍历不重复的点
        while (index != pointSet.size()) {
            Pair curP = pointSet.get(index);
            for (int i = 0; i < index; i++) {
                Pair target = pointSet.get(i);
                // 计算斜率a和b
                Pair slope = cal(target, curP);
                if (slopeCounts.containsKey(slope)) {
                    slopeCounts.put(slope, pointDuplicateCnts.get(curP));
                } else {
                    slopeCounts.put(slope, pointDuplicateCnts.get(target) + pointDuplicateCnts.get(curP));
                }
                max = Math.max(slopeCounts.get(slope), max);
            }
            System.out.println(max);
            index++;
        }
        return max;
    }

    private Pair<Pair<Long>> cal(Pair<Long> target, Pair<Long> curP) {
        long x1 = target.x, y1 = target.y, x2 = curP.x, y2 = curP.y;
        if (x1 == x2) {
            return new Pair(new Pair<>(1, 1), new Pair(-x1, 1));
        }
        return new Pair(new Pair<>(y2 - y1, x2 - x1), new Pair(x2 * y1 - x1 * y2, x2 - x1));
    }

    class Pair<T> {
        T x;
        T y;

        public Pair(T x, T y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Pair pair = (Pair) o;
            return x.equals(pair.x) &&
                    y.equals(pair.y);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
