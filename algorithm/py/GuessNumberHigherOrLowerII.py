# https://leetcode.com/problems/guess-number-higher-or-lower-ii/

class Solution(object):
    def getMoneyAmount(self, n):
        """
        :type n: int
        :rtype: int
        """
        r = [[0 for i in range(n + 1)] for i in range(n + 1)]
        i, gap = 1, 1
        while gap != n:
            while i + gap < n + 1:
                r[i][i + gap] = (i + i + gap) * (gap + 1) / 2
                for x in range(i, i + gap):
                    r[i][i + gap] = min(r[i][i + gap], x + max(r[i][x - 1], r[x + 1][i + gap]))
                print i, i + gap, r[i][i + gap]
                i += 1
            i = 1
            gap += 1
        return r[1][n]
