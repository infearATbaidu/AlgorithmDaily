# https://leetcode.com/problems/predict-the-winner/

class Solution(object):
    def PredictTheWinner(self, nums):
        """
        :type nums: List[int]
        :rtype: bool
        """
        l = len(nums)
        r = [[0 for i in range(l)] for i in range(l)]

        for i in range(0, l - 1):
            r[i][i] = nums[i]
            r[i][i + 1] = abs(nums[i] - nums[i + 1])

        r[l - 1][l - 1] = nums[l - 1]

        i, gap = 0, 2
        while gap < l:
            while i + gap < l:
                j = i + gap
                r[i][j] = max(min(nums[i] - nums[i + 1] + r[i + 2][j], nums[i] - nums[j] + r[i + 1][j - 1]),
                              min(nums[j] - nums[i] + r[i + 1][j - 1], nums[j] - nums[j - 1] + r[i][j - 2]))
                i += 1
            i = 0
            gap += 1

        return r[0][l - 1] >= 0
