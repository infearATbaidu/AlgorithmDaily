package main

// https://leetcode.com/problems/restore-ip-addresses/

import "strconv"

func restoreIpAddresses(s string) []string {
	return restore(s, 0, 4)
}

func restore(s string, start int, k int) []string {
	if k == 0 {
		if start == len(s) {
			return []string{}
		}
		return nil
	}
	if start >= len(s) {
		return nil
	}
	var result []string = nil
	for step := 1; step <= 3; step++ {
		var sub = restore(s, start+step, k-1)
		if sub == nil {
			continue
		}
		var str = s[start : start+step]
		var intVal, _ = strconv.Atoi(str)
		if intVal > 255 || (step != 1 && s[start] == '0') {
			continue
		}
		if result == nil {
			result = []string{}
		}
		if len(sub) == 0 {
			result = append(result, str)
		} else {
			for _, tmp := range sub {
				result = append(result, str+"."+tmp)
			}
		}
	}
	return result
}
