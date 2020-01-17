package _go

func maxNumberOfBalloons(text string) int {
	if len(text) == 0 {
		return 0
	}
	var stats = make(map[rune]int)
	stats['b'] = 0
	stats['a'] = 0
	stats['l'] = 0
	stats['o'] = 0
	stats['n'] = 0
	for _, ch := range text {
		if ch == 'b' || ch == 'a' || ch == 'l' || ch == 'o' || ch == 'n' {
			stats[ch] = stats[ch] + 1
		}
	}
	var min = 10000
	for ch, cnt := range stats {
		if ch == 'b' || ch == 'a' || ch == 'n' {
			if min > cnt {
				min = cnt
			}
		} else {
			if min > cnt/2 {
				min = cnt / 2
			}
		}
	}
	return min
}
