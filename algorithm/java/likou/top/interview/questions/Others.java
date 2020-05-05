package likou.top.interview.questions;

import java.util.Arrays;

/**
 * @author infear
 */
public class Others {

    public static void main(String args[]) {
        char[] input = new char[] {'A', 'A', 'B', 'B', 'C', 'C', 'D', 'D', 'E', 'E'};
        System.out.println(new Others().leastInterval(input, 2));
    }

    public int leastInterval(char[] tasks, int n) {
        int counts[] = new int[26];
        for (char task : tasks) {
            counts[task - 'A']++;
        }
        Arrays.sort(counts);
        int r = 0, used = 0;
        while (used != tasks.length) {
            // 准备分配n+1的任务
            int tmp = 0;
            for (int i = counts.length - 1; i != -1; i--) {
                if (tmp == n + 1) {
                    break;
                }
                if (counts[i] == 0) {
                    break;
                }
                counts[i]--;
                used++;
                tmp++;
                r++;
            }
            if (used != tasks.length && tmp < n + 1) {
                r += n + 1 - tmp;
            }
            Arrays.sort(counts);
        }
        return r;
    }
}
