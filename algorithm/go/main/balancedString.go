package main

// https://leetcode.com/problems/replace-the-substring-for-balanced-string/

func balancedString(s string) int {
	var m = make(map[uint8]int)
	m['Q'], m['W'], m['E'], m['R'] = 0, 0, 0, 0
	for i := 0; i < len(s); i++ {
		m[s[i]] = m[s[i]] + 1
	}
	var minR = 0
	for k, v := range m {
		if v > len(s)/4 {
			m[k] = v - len(s)/4
			minR += m[k]
		} else {
			delete(m, k)
		}
	}
	var i, j, curMin = 0, 0, len(s)
	var tmp = make(map[uint8]int)
	for i != len(s) && curMin != minR {
		for j != len(s) {
			var _, ok = tmp[s[j]]
			if !ok {
				tmp[s[j]] = 0
			}
			tmp[s[j]] += 1
			if match(tmp, m) {
				break
			}
			j += 1
		}
		if j == len(s) {
			break
		}
		for i <= j {
			tmp[s[i]] -= 1
			if !match(tmp, m) {
				break
			}
			i += 1
		}
		if j-i+1 < curMin {
			curMin = j - i + 1
		}
		j += 1
		i += 1
	}
	return curMin
}

func match(tmp map[uint8]int, target map[uint8]int) bool {
	for k, v := range target {
		var value, ok = tmp[k]
		if !ok || value < v {
			return false
		}
	}
	return true
}

func main() {
	balancedString("WWEQERQWQWWRWWERQWEQ")
}
