package main

// https://leetcode.com/problems/insert-into-a-binary-search-tree/

type TreeNode struct {
	Val   int
	Left  *TreeNode
	Right *TreeNode
}

func insertIntoBST(root *TreeNode, val int) *TreeNode {
	if root == nil {
		var newroot TreeNode
		newroot.Val = val
		return &newroot
	}
	if root.Val > val {
		root.Left = insertIntoBST(root.Left, val)
		return root
	}
	if root.Val < val {
		root.Right = insertIntoBST(root.Right, val)
		return root
	}
	return nil
}
