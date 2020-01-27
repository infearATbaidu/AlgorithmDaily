package main

import (
	"sort"
)

// 为了能够使用自定义函数来排序，我们需要一个
// 对应的排序类型，比如这里我们为内置的字符串
// 数组定义了一个别名ByLength
type ByLength []string

// 我们实现了sort接口的Len，Less和Swap方法
// 这样我们就可以使用sort包的通用方法Sort
// Len和Swap方法的实现在不同的类型之间大致
// 都是相同的，只有Less方法包含了自定义的排序
// 逻辑，这里我们希望以字符串长度升序排序
func (s ByLength) Len() int {
	return len(s)
}
func (s ByLength) Swap(i, j int) {
	s[i], s[j] = s[j], s[i]
}
func (s ByLength) Less(i, j int) bool {
	return len(s[i]) < len(s[j])
}

func longestStrChain(words []string) int {
	if len(words) <= 1 {
		return len(words)
	}
	sort.Sort(ByLength(words))
	var result = make(map[int]int)
	var index = len(words) - 1
	result[index] = 1
	var max = 1
	index -= 1
	for index != -1 {
		var curMaxLength = 1
		var j = index + 1
		for j != len(words) {
			if isPre(words[index], words[j]) {
				if curMaxLength < result[j]+1 {
					curMaxLength = result[j] + 1
				}
			}
			j += 1
		}
		result[index] = curMaxLength
		if max < curMaxLength {
			max = curMaxLength
		}
		println(index, result[index])
		index -= 1
	}
	return max
}

func isPre(s1 string, s2 string) bool {
	if len(s2)-len(s1) != 1 {
		return false
	}
	var i, j, chance = 0, 0, 1
	for true {
		for i != len(s1) && s1[i] == s2[j] {
			i += 1
			j += 1
		}
		if i != len(s1) {
			if chance == 0 {
				return false
			}
			chance -= 1
			j += 1
		} else {
			return true
		}
	}
	return true
}
