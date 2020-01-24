package _go

import "strings"

// https://leetcode.com/problems/text-justification/

func fullJustify(words []string, maxWidth int) []string {
	var i = 0
	var result []string
	for true {
		var cur = len(words[i])
		var j = i + 1
		for cur <= maxWidth && j < len(words) {
			cur = cur + len(words[j]) + 1
			j += 1
		}
		if cur <= maxWidth && j == len(words) {
			// last line
			var lastLine = strings.Join(words[i:j], " ")
			lastLine += strings.Repeat(" ", maxWidth-len(lastLine))
			result = append(result, lastLine)
			return result
		}
		j -= 1
		cur -= len(words[j]) + 1
		// middle line
		var wordsCount = j - i
		var blankLength = maxWidth - (cur - (wordsCount - 1))
		var blankMax = blankLength
		var blankRemainder = 0
		if wordsCount-1 != 0 {
			blankMax = blankLength / (wordsCount - 1)
			blankRemainder = blankLength % (wordsCount - 1)
		}
		var tmp = ""
		var k = i
		for k != j {
			tmp += words[k]
			if k != j-1 || i == j-1 {
				tmp += strings.Repeat(" ", blankMax)
				if blankRemainder != 0 {
					tmp += " "
					blankRemainder -= 1
				}
			}
			k += 1
		}
		result = append(result, tmp)
		i = j
	}
	return result
}
