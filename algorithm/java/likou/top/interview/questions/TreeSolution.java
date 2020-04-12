package likou.top.interview.questions;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author infear
 */
public class TreeSolution {
    public int kthSmallest(TreeNode root, int k) {
        AtomicInteger count = new AtomicInteger(k);
        TreeNode node = preOrder(root, count);
        return node.val;
    }

    private TreeNode preOrder(TreeNode root, AtomicInteger count) {
        if (root == null) {
            return null;
        }
        TreeNode left = preOrder(root.left, count);
        if (left != null) {
            return left;
        }
        if (count.decrementAndGet() == 0) {
            return root;
        }
        return preOrder(root.right, count);
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        Map<TreeNode, TreeNode> ancestor = new HashMap<>();
        traverse(root, ancestor);
        LinkedHashSet<TreeNode> first = new LinkedHashSet(), second = new LinkedHashSet();
        TreeNode cur = p;
        first.add(cur);
        while (ancestor.get(cur) != null) {
            first.add(ancestor.get(cur));
            cur = ancestor.get(cur);
        }
        cur = q;
        second.add(cur);
        while (ancestor.get(cur) != null) {
            second.add(ancestor.get(cur));
            cur = ancestor.get(cur);
        }
        for (TreeNode key : first) {
            if (second.contains(key)) {
                return key;
            }
        }
        return root;
    }

    private void traverse(TreeNode root, Map<TreeNode, TreeNode> ancestor) {
        if (root == null) {
            return;
        }
        if (root.left != null) {
            ancestor.put(root.left, root);
            traverse(root.left, ancestor);
        }
        if (root.right != null) {
            ancestor.put(root.right, root);
            traverse(root.right, ancestor);
        }
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

}
