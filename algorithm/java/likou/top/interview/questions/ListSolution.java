package likou.top.interview.questions;

import java.util.HashMap;

/**
 * @author infear
 */
public class ListSolution {
    public static void main(String args[]) {
        ListNode n1 = new ListNode(1, new ListNode(2, new ListNode(3, null)));
        new ListSolution().oddEvenList(n1);
    }

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
            slow = slow.next;
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

    /*    奇偶链表
        给定一个单链表，把所有的奇数节点和偶数节点分别排在一起。请注意，这里的奇数节点和偶数节点指的是节点编号的奇偶性，而不是节点的值的奇偶性。

        请尝试使用原地算法完成。你的算法的空间复杂度应为 O(1)，时间复杂度应为 O(nodes)，nodes 为节点总数。

        示例 1:

        输入: 1->2->3->4->5->NULL
        输出: 1->3->5->2->4->NULL
        示例 2:

        输入: 2->1->3->5->6->4->7->NULL
        输出: 2->3->6->7->1->5->4->NULL
        说明:

        应当保持奇数节点和偶数节点的相对顺序。
        链表的第一个节点视为奇数节点，第二个节点视为偶数节点，以此类推。*/
    public ListNode oddEvenList(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode odd = head, even = head.next, evenHead = even;
        boolean flag = true;
        while (odd != null & even != null) {
            if (flag) {
                odd.next = even.next;
                if (odd.next == null) {
                    break;
                }
                odd = odd.next;
            } else {
                even.next = odd.next;
                if (even.next == null) {
                    break;
                }
                even = even.next;
            }
            flag = !flag;
        }
        odd.next = evenHead;
        return head;
    }

    /*    两数相加
        给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。

        如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。

        您可以假设除了数字 0 之外，这两个数都不会以 0 开头。

        示例：

        输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
        输出：7 -> 0 -> 8
        原因：342 + 465 = 807*/
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode c1 = l1, c2 = l2;
        ListNode head = new ListNode(-1, null);
        ListNode c = head;
        int flag = 0;
        while (c1 != null || c2 != null || flag != 0) {
            int v1 = c1 != null ? c1.val : 0;
            int v2 = c2 != null ? c2.val : 0;
            int v = v1 + v2 + flag;
            if (v >= 10) {
                v = v % 10;
                flag = 1;
            } else {
                flag = 0;
            }
            c.next = new ListNode(v, null);
            c = c.next;
            if (c1 != null) {
                c1 = c1.next;
            }
            if (c2 != null) {
                c2 = c2.next;
            }
        }
        return head.next;
    }

    /*    合并两个有序链表
        将两个升序链表合并为一个新的升序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。

        示例：

        输入：1->2->4, 1->3->4
        输出：1->1->2->3->4->4*/
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(-1, null);
        ListNode cur = head;
        ListNode cur1 = l1, cur2 = l2;
        while (cur1 != null || cur2 != null) {
            int v1 = cur1 != null ? cur1.val : Integer.MAX_VALUE;
            int v2 = cur2 != null ? cur2.val : Integer.MAX_VALUE;
            if (v1 <= v2) {
                cur.next = cur1;
                cur1 = cur1.next;
            } else {
                cur.next = cur2;
                cur2 = cur2.next;
            }
            cur = cur.next;
        }
        return head.next;
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
