# https://leetcode.com/submissions/detail/114720968/

class Solution(object):
    def lastRemaining(self, n):
        """
        :type n: int
        :rtype: int
        """
        if n == 1 or n == 2:
            return n
        if n % 2 == 1:
            return self.lastRemaining(n - 1)
        if n % 4 == 0:
            return self.lastRemaining(n / 4) * 4 - 2
        return self.lastRemaining((n - 2) / 4) * 4
