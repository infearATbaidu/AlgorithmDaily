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

    /*    反转从位置 m 到 n 的链表。请使用一趟扫描完成反转。

        说明:
                1 ≤ m ≤ n ≤ 链表长度。

        示例:

        输入: 1->2->3->4->5->NULL, m = 2, n = 4
        输出: 1->4->3->2->5->NULL

        来源：力扣（LeetCode）
        链接：https://leetcode-cn.com/problems/reverse-linked-list-ii
        著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。*/
    public ListNode reverseBetween(ListNode head, int m, int n) {
        int nums = 1;
        ListNode vHead = new ListNode(), cur = head, pre = vHead;
        vHead.next = head;
        while (nums <= n) {
            if (nums < m) {
                pre = cur;
                cur = cur.next;
                nums++;
            } else {
                // 开始翻转，记一下pre，也就是开始翻转前的最后一个节点
                ListNode end = pre;
                while (nums <= n) {
                    ListNode next = cur.next;
                    cur.next = pre;
                    pre = cur;
                    cur = next;
                    nums++;
                }
                end.next.next = cur;
                end.next = pre;
            }
        }
        return vHead.next;
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

    public boolean isPalindrome2(ListNode head) {
        if (head == null) {
            return true;
        }
        ListNode slow = head, fast = slow;
        while (true) {
            if (fast.next == null || fast.next.next == null) {
                break;
            }
            fast = fast.next.next;
            slow = slow.next;
        }
        boolean isOdd = fast.next == null;
        ListNode midHead = slow.next;
        slow.next = null;
        ListNode newHead = reverseList(head);
        // list length is odd.
        if (isOdd) {
            newHead = newHead.next;
        }
        ListNode cur1 = newHead, cur2 = midHead;
        while (cur1 != null && cur2 != null && cur1.val == cur2.val) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1 == null && cur2 == null;
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
        ListNode curA = headA, curB = headB;
        if (curA == null || curB == null) {
            return null;
        }
        int lengthA = 0, lengthB = 0;
        while (curA.next != null || curB.next != null) {
            if (curA.next != null) {
                curA = curA.next;
                lengthA++;
            }
            if (curB.next != null) {
                curB = curB.next;
                lengthB++;
            }
        }
        if (curA != curB) {
            return null;
        }
        curA = headA;
        curB = headB;
        int gap = lengthA - lengthB;
        if (gap > 0) {
            while (gap-- != 0) {
                curA = curA.next;
            }
        } else {
            gap = -gap;
            while (gap-- != 0) {
                curB = curB.next;
            }
        }
        while (curA != curB) {
            curA = curA.next;
            curB = curB.next;
        }
        return curA;
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

    // use merge sort to reimplement sortList, which would optimize performance.
    public ListNode sortList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode mid = findMiddle(head);
        ListNode tmp = mid.next;
        mid.next = null;
        ListNode newHead1 = sortList2(head), newHead2 = sortList2(tmp);
        return merge(newHead1, newHead2);
    }

    private ListNode merge(ListNode newHead1, ListNode newHead2) {
        ListNode c1 = newHead1, c2 = newHead2, head = new ListNode(-1, null), c = head;
        while (c1 != null || c2 != null) {
            if (c1 == null) {
                c.next = c2;
                c2 = c2.next;
            } else {
                if (c2 == null || c1.val < c2.val) {
                    c.next = c1;
                    c1 = c1.next;
                } else {
                    c.next = c2;
                    c2 = c2.next;
                }
            }
            c = c.next;
        }
        return head.next;
    }

    private ListNode findMiddle(ListNode head) {
        ListNode slow = head, fast = head;
        while (true) {
            if (fast.next == null || fast.next.next == null) {
                return slow;
            }
            fast = fast.next.next;
            slow = slow.next;
        }
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
        ListNode evenHead = new ListNode(-1, null), oddHead = new ListNode(-1, null);
        ListNode oddCur = oddHead, evenCur = evenHead, cur = head, tmp;
        boolean isOdd = true;
        while (cur != null) {
            if (isOdd) {
                oddCur.next = cur;
                oddCur = oddCur.next;
            } else {
                evenCur.next = cur;
                evenCur = evenCur.next;
            }
            isOdd = !isOdd;
            // need to clear cur.next here.
            tmp = cur.next;
            cur.next = null;
            cur = tmp;
        }
        oddCur.next = evenHead.next;
        return oddHead.next;
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

    /*    合并K个排序链表
        合并 k 个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。

        示例:

        输入:
                [
                1->4->5,
                1->3->4,
                2->6
                ]
        输出: 1->1->2->3->4->4->5->6*/
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }
        int end = lists.length, cur = 0;
        while (end != 1) {
            for (int index = 0; index < end; index += 2) {
                lists[cur++] = mergeTwoLists(lists[index], index + 1 >= end ? null : lists[index + 1]);
            }
            end = cur;
            cur = 0;
        }
        return lists[0];
    }

    // use recursive to implement mergeList
    public ListNode mergeKLists2(ListNode[] lists) {
        int l = lists.length;
        if (l == 0) {
            return null;
        }
        if (l == 1) {
            return lists[0];
        }
        ListNode[] sub = new ListNode[l % 2 == 0 ? l / 2 : l / 2 + 1];
        int index = 0;
        for (int i = 0; i < l; i += 2) {
            if (i != l - 1) {
                sub[index++] = merge(lists[i], lists[i + 1]);
            } else {
                sub[index++] = lists[i];
            }
        }
        return mergeKLists(sub);
    }

    /*    环形链表 II
        给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。

        为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。

        说明：不允许修改给定的链表。*/
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (true) {
            if (fast != null && fast.next != null) {
                fast = fast.next.next;
            } else {
                return null;
            }
            slow = slow.next;
            if (slow == fast) {
                break;
            }
        }
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    // https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/
    // 删除链表的倒数第n个节点
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode vHead = new ListNode();
        vHead.next = head;
        ListNode slow = vHead, fast = vHead;
        while (n-- != 0) {
            fast = fast.next;
        }
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;
        return vHead.next;
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
