# https://leetcode.com/problems/find-all-duplicates-in-an-array/

class Solution(object):
    def findDuplicates(self, nums):
        """
        :type nums: List[int]
        :rtype: List[int]
        """
        if not nums or len(nums) == 0:
            return []
        l = len(nums)
        i = 0
        r = []
        while i != l:
            ele = nums[i]
            if ele < 0:
                ele *= -1
            if nums[ele - 1] < 0:
                r.append(ele)
            else:
                nums[ele - 1] *= -1
            i += 1
        return r
