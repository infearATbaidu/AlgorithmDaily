package main

// https://leetcode.com/problems/maximize-distance-to-closest-person/
func maxDistToClosest(seats []int) int {
	var person []int
	for i := 0; i < len(seats); i++ {
		if seats[i] == 1 {
			if len(person) == 0 {
				person = append(person, -i)
			}
			person = append(person, i)
		}
	}
	var last = person[len(person)-1]
	person = append(person, (len(seats)-1)*2-last)
	var curMax = 1
	for i := 0; i < len(person)-1; i++ {
		if (person[i+1]-person[i])/2 > curMax {
			curMax = (person[i+1] - person[i]) / 2
		}
	}
	return curMax
}
