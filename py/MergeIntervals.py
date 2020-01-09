# https://leetcode.com/problems/merge-intervals/

# Definition for an interval.
# class Interval(object):
#     def __init__(self, s=0, e=0):
#         self.start = s
#         self.end = e


class Solution(object):
    def merge(self, intervals):
        """
        :type intervals: List[Interval]
        :rtype: List[Interval]
        """
        # sort by left first,then right
        intervals.sort(key=lambda x: (x.start, x.end))
        i = 0
        while True:
            if i >= len(intervals) - 1:
                return intervals
            v1, v2 = intervals[i], intervals[i + 1]
            if v1.end < v2.start:
                i = i + 1
            else:
                del intervals[i]
                intervals[i] = Interval(v1.start, max(v1.end, v2.end))
