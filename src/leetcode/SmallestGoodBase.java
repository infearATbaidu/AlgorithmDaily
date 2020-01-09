package leetcode;

import java.math.BigInteger;

/**
 * https://leetcode.com/problems/smallest-good-base/
 *
 * @author Zhang Gang (zhanggang02@baidu.com)
 */
public class SmallestGoodBase {
    public static String smallestGoodBase(String n) {
        long value = Long.valueOf(n);
        long i = 2;
        while (true) {
            long t = value - 1;
            while (t % i == 0) {
                t = t / i;
                if (t == 1) {
                    return String.valueOf(i);
                } else {
                    t = t - 1;
                }
            }
            i++;
        }
    }

    public static void main(String args[]) {
        System.out.println(new SmallestGoodBase().smallestGoodBase2("13"));
    }

    public String smallestGoodBase2(String n) {
        BigInteger value = new BigInteger(n);
        BigInteger tmp = value.add(new BigInteger("1"));
        // calculate max pow on base 2 for value + 1.
        BigInteger base = BigInteger.ONE;
        int max = 0;
        while (base.compareTo(tmp) <= 0) {
            base = base.multiply(BigInteger.valueOf(2));
            max++;
        }
        max--;

        //
        int min = 2, r = max;
        while (r >= min) {
            long start = 2, end = value.longValue() - 1;
            while (start <= end) {
                long mid = (start + end) / 2;
                BigInteger rr = cal(mid, r);
                if (rr.equals(value)) {
                    return String.valueOf(mid);
                } else {
                    if (start == end) {
                        break;
                    } else {
                        if (rr.compareTo(value) > 0) {
                            end = mid - 1;
                        } else {
                            start = mid + 1;
                        }
                    }
                }
            }
            r--;
        }
        return null;
    }

    public BigInteger cal(long a, int b) {
        return BigInteger.valueOf(a).pow(b).add(BigInteger.valueOf(-1)).divide(BigInteger.valueOf(a).add(BigInteger.valueOf(-1)));
    }
}
