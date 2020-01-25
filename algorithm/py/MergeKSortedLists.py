# https://leetcode.com/problems/merge-k-sorted-lists/

# Definition for singly-linked list.
# class ListNode(object):
#     def __init__(self, x):
#         self.val = x
#         self.next = None

class Solution(object):
    def mergeKLists(self, lists):
        """
        :type lists: List[ListNode]
        :rtype: ListNode
        """
        if not lists or len(lists) == 0:
            return None
        if len(lists) == 1:
            return lists[0]
        k = 0
        ll = []
        while k + 1 < len(lists):
            ll.append(self.mergeTwo(lists[k], lists[k + 1]))
            k += 2
        if k == len(lists) - 1:
            ll.append(lists[k])
        return self.mergeKLists(ll)

    def mergeTwo(self, ln1, ln2):
        vhead = ListNode(-1)
        i, i1, i2 = vhead, ln1, ln2
        while i1 is not None or i2 is not None:
            if i1 is None:
                i.next = i2
                i2 = i2.next
            else:
                if i2 is None:
                    i.next = i1
                    i1 = i1.next
                else:
                    if i1.val <= i2.val:
                        i.next = i1
                        i1 = i1.next
                    else:
                        i.next = i2
                        i2 = i2.next
            i = i.next
        return vhead.next
