package main

// https://leetcode.com/problems/longest-mountain-in-array/

func longestMountain(A []int) int {
	var length = len(A)
	var i, cur = 0, 0
	for i != length {
		var j = i + 1
		for j < length && A[j] > A[j-1] {
			j += 1
		}
		j -= 1
		if !(A[j] > A[i]) {
			i += 1
			continue
		}
		var k = j + 1
		for k < length && A[k] < A[k-1] {
			k += 1
		}
		k -= 1
		if !(A[k] < A[j]) {
			i = j + 1
			continue
		}
		if k-i+1 > cur {
			cur = k - i + 1
		}
		i = k
	}
	return cur
}
