# https://leetcode.com/submissions/detail/114724703/

class Solution(object):
    def numberOfBoomerangs(self, points):
        """
        :type points: List[List[int]]
        :rtype: int
        """
        count = 0
        for p1 in points:
            m = {}
            for p2 in points:
                if p1 == p2:
                    continue
                d = (p1[0] - p2[0]) * (p1[0] - p2[0]) + (p1[1] - p2[1]) * (p1[1] - p2[1])
                m.setdefault(d, 0)
                m[d] = m[d] + 1
            for k, v in m.items():
                count += v * (v - 1)
        return count
