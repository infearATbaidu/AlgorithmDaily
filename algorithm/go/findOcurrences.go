package _go

import "strings"

// https://leetcode.com/problems/occurrences-after-bigram/submissions/

func findOcurrences(text string, first string, second string) []string {
	var result []string
	if len(text) == 0 {
		return result
	}
	var elements = strings.Split(text, " ")
	for index, ele := range elements {
		if ele == second {
			if index-1 >= 0 && elements[index-1] == first && index+1 < len(elements) {
				result = append(result, elements[index+1])
			}
		}
	}
	return result
}
