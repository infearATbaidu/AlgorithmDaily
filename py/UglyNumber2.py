# https://leetcode.com/problems/ugly-number-ii/


class Solution(object):
    def nthUglyNumber(self, n):
        """
        :type n: int
        :rtype: int
        """
        r = [1]
        a,b,c=0,0,0
        while len(r) != n:
            next = min(r[a]*2,r[b]*3,r[c]*5)
            r.append(next)
            if next == r[a]*2:
                a+=1
            if next == r[b]*3:
                b+=1
            if next == r[c]*5:
                c+=1
        return r[-1]