package main

import (
	"reflect"
)

// https://leetcode.com/problems/leaf-similar-trees/

func leafSimilar(root1 *TreeNode, root2 *TreeNode) bool {
	var leaf1 []int
	var leaf2 []int
	leaf1 = findLeaf(root1, leaf1)
	leaf2 = findLeaf(root2, leaf2)
	return reflect.DeepEqual(leaf1, leaf2)
}

func findLeaf(root *TreeNode, leaf []int) []int {
	if root == nil {
		return leaf
	}
	leaf = findLeaf(root.Left, leaf)
	leaf = findLeaf(root.Right, leaf)
	if root.Left == nil && root.Right == nil {
		leaf = append(leaf, root.Val)
	}
	return leaf
}
