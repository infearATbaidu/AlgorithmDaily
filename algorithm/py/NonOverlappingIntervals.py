# https://leetcode.com/problems/non-overlapping-intervals/

# Definition for an interval.
# class Interval(object):
#     def __init__(self, s=0, e=0):
#         self.start = s
#         self.end = e

class Solution(object):
    def eraseOverlapIntervals(self, intervals):
        """
        :type intervals: List[Interval]
        :rtype: int
        """
        r = 0
        i = 0
        j = i + 1
        intervals.sort(key=lambda obj: obj.start)
        while j < len(intervals):
            if intervals[j].start >= intervals[i].end:
                i = j
                j += 1
            else:
                if intervals[j].end <= intervals[i].end:
                    i = j
                    j += 1
                    r += 1
                else:
                    j += 1
                    r += 1
        return r
