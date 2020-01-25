# https://leetcode.com/problems/robot-return-to-origin/

class Solution(object):
    def judgeCircle(self, moves):
        """
        :type moves: str
        :rtype: bool
        """
        x, y = 0, 0
        d = {'U': [0, 1], 'L': [-1, 0], 'R': [1, 0], 'D': [0, -1]}
        for e in moves:
            x += d[e][0]
            y += d[e][1]
        return x == 0 and y == 0
