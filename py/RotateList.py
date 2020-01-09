# https://leetcode.com/problems/rotate-list/

# Definition for singly-linked list.
# class ListNode(object):
#     def __init__(self, x):
#         self.val = x
#         self.next = None


class Solution(object):
    def rotateRight(self, head, k):
        """
        :type head: ListNode
        :type k: int
        :rtype: ListNode
        """
        if k == 0 or head is None:
            return head
        tail = idx = head
        l = 0
        while idx is not None:
            tail = idx
            idx = idx.next
            l = l + 1
        if k >= l:
            return self.rotateRight(head,k%l)
        idx = head
        kk = l - k - 1
        while kk != 0:
            kk = kk - 1
            idx = idx.next
        nhead = idx.next
        idx.next = None
        tail.next = head
        return nhead