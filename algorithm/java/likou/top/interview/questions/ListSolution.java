package likou.top.interview.questions;

import java.util.HashMap;

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

    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        Node vhead = new Node(-1), pre = vhead, cur = head;
        HashMap<Node, Node> m = new HashMap<>();
        while (cur != null) {
            Node copy = new Node(cur.val);
            copy.random = cur;
            m.put(cur, copy);
            cur = cur.next;
            pre.next = copy;
            pre = copy;
        }
        cur = vhead.next;
        while (cur != null) {
            if (cur.random.random != null) {
                cur.random = m.get(cur.random.random);
            } else {
                cur.random = null;
            }
            cur = cur.next;
        }
        return vhead.next;
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode cur = headA;
        int lengthA = 0;
        while (cur != null) {
            cur = cur.next;
            lengthA++;
        }
        cur = headB;
        int lengthB = 0;
        while (cur != null) {
            cur = cur.next;
            lengthB++;
        }
        ListNode cur1, cur2;
        if (lengthA >= lengthB) {
            cur = headA;
            int gap = lengthA - lengthB;
            while (gap != 0) {
                cur = cur.next;
                gap--;
            }
            cur1 = cur;
            cur2 = headB;
        } else {
            cur = headB;
            int gap = lengthB - lengthA;
            while (gap != 0) {
                cur = cur.next;
                gap--;
            }
            cur1 = headA;
            cur2 = cur;
        }
        while (cur1 != null && cur2 != null && cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        if (cur1 == null) {
            return null;
        }
        return cur1;
    }

    public void deleteNode(ListNode node) {
        ListNode cur = node;
        while (cur.next != null) {
            cur.val = cur.next.val;
            if (cur.next.next == null) {
                cur.next = null;
                break;
            }
            cur = cur.next;
        }
    }

    public boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = slow;
        while (true) {
            if (fast != null && fast.next != null) {
                fast = fast.next.next;
            } else {
                return false;
            }
            if (slow != null) {
                slow = slow.next;
            } else {
                return false;
            }
            if (slow == fast) {
                return true;
            }
        }
    }

    public ListNode sortList(ListNode head) {
        ListNode last = null;
        ListNode cur = head;
        while (cur != last) {
            while (cur.next != last) {
                if (cur.val > cur.next.val) {
                    int tmp = cur.val;
                    cur.val = cur.next.val;
                    cur.next.val = tmp;
                }
                cur = cur.next;
            }
            last = cur;
            cur = head;
        }
        return head;
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }
}
