# https://leetcode.com/problems/set-mismatch/

class Solution(object):
    def findErrorNums(self, nums):
        """
        :type nums: List[int]
        :rtype: List[int]
        """
        d = {}
        actualSum = 0
        r = []
        for ele in nums:
            actualSum += ele
            d.setdefault(ele, 0)
            d[ele] = d[ele] + 1
            if d[ele] == 2:
                r.insert(0, ele)
        cnt = len(nums)
        expectedSum = cnt * (cnt + 1) / 2
        lose = expectedSum - actualSum + r[0]
        r.append(lose)
        return r
