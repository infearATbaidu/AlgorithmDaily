class Solution(object):
    def minSubArrayLen(self, s, nums):
        """
        :type s: int
        :type nums: List[int]
        :rtype: int
        """
        i = j = 0
        sum = 0
        l = len(nums)
        r = l + 1
        while True:
            while j != l and sum < s:
                sum += nums[j]
                j += 1
            if sum >= s:
                while sum >= s and i != j:
                    sum -= nums[i]
                    i += 1
                r = min(r, j - i + 1)
            else:
                break;
        if r > l:
            r = 0
        return r