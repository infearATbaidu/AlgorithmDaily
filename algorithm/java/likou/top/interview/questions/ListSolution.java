package likou.top.interview.questions;

/**
 * @author infear
 */
public class ListSolution {
    public ListNode reverseList(ListNode head) {
        ListNode next = null, cur = head;
        while (cur != null) {
            ListNode tmp = cur.next;
            cur.next = next;
            next = cur;
            cur = tmp;
        }
        return next;
    }

    public boolean isPalindrome(ListNode head) {
        int i = 0;
        ListNode cur = head;
        while (cur != null) {
            i++;
            cur = cur.next;
        }
        if (i == 1) {
            return true;
        }
        cur = head;
        int mid = (i % 2 == 0 ? i / 2 : i / 2 + 1);
        while (mid != 0) {
            cur = cur.next;
            mid--;
        }
        ListNode newHead = reverseList(cur);
        ListNode index1 = newHead, index2 = head;
        while (index1 != null) {
            if (index1.val == index2.val) {
                index1 = index1.next;
                index2 = index2.next;
            } else {
                return false;
            }
        }
        return true;
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
