# https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/
class Solution(object):
    def findMin(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        start = 0
        end = len(nums) - 1
        while start != end:
            mid = (start + end) / 2
            if nums[mid] >= nums[start]:
                if nums[mid] > nums[end]:
                    start = mid + 1
                else:
                    end = mid
            else:
                end = mid
        return nums[start]
