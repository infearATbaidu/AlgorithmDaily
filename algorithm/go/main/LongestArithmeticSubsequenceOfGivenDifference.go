package main

// https://leetcode.com/problems/longest-arithmetic-subsequence-of-given-difference/

func longestSubsequence(arr []int, difference int) int {
	var l = len(arr)
	var result = make(map[int]int)
	var pos = make(map[int][]int)
	var index = 0
	for index != l {
		if pos[arr[index]] == nil {
			pos[arr[index]] = []int{}
		}
		pos[arr[index]] = append(pos[arr[index]], index)
		index += 1
	}
	result[l-1] = 1
	var curMax = 1
	index = l - 2
	for index != -1 {
		var tmpMax = 1
		var subIndex = pos[arr[index]+difference]
		if subIndex != nil {
			for _, j := range subIndex {
				if j > index && tmpMax < result[j]+1 {
					tmpMax = result[j] + 1
				}
				j += 1
			}
		}
		result[index] = tmpMax
		if curMax < tmpMax {
			curMax = tmpMax
		}
		index -= 1
	}
	return curMax
}
