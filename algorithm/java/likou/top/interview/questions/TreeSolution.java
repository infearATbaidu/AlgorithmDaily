package likou.top.interview.questions;

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

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

}
