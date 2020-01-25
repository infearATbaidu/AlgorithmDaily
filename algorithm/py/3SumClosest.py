# https://leetcode.com/problems/3sum-closest/

class Solution(object):
    def threeSumClosest(self, nums, target):
        """
        :type nums: List[int]
        :type target: int
        :rtype : int
        """
        i = 0
        r = nums[0] + nums[1] + nums[2]
        nums.sort()
        while i < len(nums):
            j = i + 1
            k = len(nums) - 1
            while j < k:
                s = nums[i] + nums[j] + nums[k]
                if s == target:
                    return s
                if abs(s - target) < abs(r - target):
                    r = s
                if s > target:
                    k -= 1
                else:
                    j += 1
            i += 1
        return r
