package main

// https://leetcode.com/problems/k-concatenation-maximum-sum/submissions/

func kConcatenationMaxSum(arr []int, k int) int {
	var index = 0
	var oneMax, curOneMax, maxSuffix, curMaxSuffix, maxPre, curMaxPre, sum int64 = 0, 0, 0, 0, 0, 0, 0
	var max int64 = 0
	for index != len(arr) {
		sum += int64(arr[index])
		if curOneMax+int64(arr[index]) >= 0 {
			curOneMax += int64(arr[index])
			if curOneMax > oneMax {
				oneMax = curOneMax
			}
		} else {
			curOneMax = 0
		}
		curMaxPre += int64(arr[index])
		if curMaxPre >= maxPre {
			maxPre = curMaxPre
		}
		curMaxSuffix += int64(arr[len(arr)-1-index])
		if curMaxSuffix > maxSuffix {
			maxSuffix = curMaxSuffix
		}
		index += 1
	}
	if curOneMax > oneMax {
		oneMax = curOneMax
	}
	if oneMax > max {
		max = oneMax
	}
	if k >= 2 && maxSuffix+maxPre > max {
		max = maxSuffix + maxPre
	}
	if k >= 2 && sum > 0 {
		max = maxSuffix + maxPre + sum*(int64(k)-2)
	}
	return int(max % 1000000007)
}

func main() {
	var arr = []int{-5, -2, 0, 0, 3, 9, -2, -5, 4}
	kConcatenationMaxSum(arr, 5)
}
