package main

// https://leetcode.com/problems/find-all-anagrams-in-a-string/
func findAnagrams(s string, p string) []int {
	var length = len(p)
	var index = 0
	var cur = make(map[uint8]int)
	var target = calCnt(p)
	var result []int
	for index+length <= len(s) {
		if index == 0 {
			cur = calCnt(s[index : index+length])
		} else {
			cur[s[index-1]] = cur[s[index-1]] - 1
			if cur[s[index-1]] == 0 {
				delete(cur, s[index-1])
			}
			cur[s[index+length-1]] = cur[s[index+length-1]] + 1
		}
		if compareCnt(cur, target) {
			result = append(result, index)
		}
		index += 1
	}
	return result
}

func calCnt(str string) map[uint8]int {
	var cnt = make(map[uint8]int)
	for i := 0; i < len(str); i++ {
		_, ok := cnt[str[i]]
		if !ok {
			cnt[str[i]] = 0
		}
		cnt[str[i]] = cnt[str[i]] + 1
	}
	return cnt
}

// compareCnt two map
func compareCnt(m1 map[uint8]int, m2 map[uint8]int) bool {
	for k, v := range m1 {
		cnt, ok := m2[k]
		if !ok || cnt != v {
			return false
		}
	}
	for k := range m2 {
		_, ok := m1[k]
		if !ok {
			return false
		}
	}
	return true
}
