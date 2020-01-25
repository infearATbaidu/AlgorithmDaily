# https://leetcode.com/problems/first-unique-character-in-a-string/

class Solution(object):
    def firstUniqChar(self, s):
        """
        :type s: str
        :rtype: int
        """
        i = 0
        m = {}
        while i != len(s):
            if m.has_key(s[i]):
                index, cnt = m.get(s[i])
                m[s[i]] = (index, cnt + 1)
            else:
                m[s[i]] = (i, 1)
            i += 1

        r = len(s)
        for k, (index, cnt) in m.items():
            if cnt > 1:
                continue
            else:
                r = min(index, r)
        if r == len(s):
            return -1
        return r
