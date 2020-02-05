package leetcode;

/**
 * https://leetcode.com/problems/element-appearing-more-than-25-in-sorted-array/
 *
 * @author infear
 */
public class FindSpecialInteger {
    public static void main(String args[]) {
        new FindSpecialInteger().findSpecialInteger(new int[]{1, 2, 2, 6, 6, 6, 6, 7, 10});
    }

    public int findSpecialInteger(int[] arr) {
        if (arr.length <= 4) {
            return arr[0];
        }
        int l = arr.length / 4;
        int times = 1;
        while (times <= 3) {
            int cur = l * times - 1;
            int max = findNearestMax(arr, cur), min = findNearestMin(arr, cur);
            if (max - min - 1 > l) {
                return arr[cur];
            }
            times++;
        }
        int max = findNearestMax(arr, arr.length - 1), min = findNearestMin(arr, arr.length - 1);
        return max - min - 1;
    }

    private int findNearestMin(int[] arr, int eleIndex) {
        int start = 0, end = arr.length - 1;
        while (start < end) {
            System.out.println("start:" + start + " end:" + end);
            int mid = (start + end) / 2;
            if (arr[mid] >= arr[eleIndex]) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        if (arr[start] >= arr[eleIndex]) {
            System.out.println("findNearestMin ele:" + arr[eleIndex] + ",pos:" + String.valueOf(start - 1));
            return start - 1;
        } else {
            System.out.println("findNearestMin ele:" + arr[eleIndex] + ",pos:" + String.valueOf(start));
            return start;
        }
    }

    private int findNearestMax(int[] arr, int eleIndex) {
        int start = 0, end = arr.length - 1;
        while (start < end) {
            System.out.println("start:" + start + " end:" + end);
            int mid = (start + end) / 2;
            if (arr[mid] <= arr[eleIndex]) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        if (arr[start] <= arr[eleIndex]) {
            System.out.println("findNearestMax ele:" + arr[eleIndex] + ",pos:" + String.valueOf(start + 1));
            return start + 1;
        } else {
            System.out.println("findNearestMax ele:" + arr[eleIndex] + ",pos:" + String.valueOf(start));
            return start;
        }
    }
}
