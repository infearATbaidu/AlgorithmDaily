package main

// https://leetcode.com/problems/convert-a-number-to-hexadecimal/

func toHex(num int) string {
	if num == 0 {
		return "0"
	}
	if num < 0 {
		num = 4294967296 + num
	}
	var m = make(map[int]string)
	m[0], m[1], m[2], m[3], m[4], m[5], m[6], m[7], m[8], m[9] = "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
	m[10] = "a"
	m[11] = "b"
	m[12] = "c"
	m[13] = "d"
	m[14] = "e"
	m[15] = "f"
	var result = ""
	for num/16 != 0 {
		var remain = num % 16
		result = m[remain] + result
		num = num / 16
	}
	result = m[num%16] + result
	return result
}
