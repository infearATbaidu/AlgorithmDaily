package leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by infear on 2017/6/11.
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    TreeNode(int x) {
        val = x;
    }

    // https://leetcode.com/problems/find-bottom-left-tree-value/#/description
    public int findBottomLeftValue(TreeNode root) {
        int rh[] = {-1};
        TreeNode r = new TreeNode(-1);
        int ch = 0;

        middle(root, ch, r, rh);
        return r.val;
    }

    private void middle(TreeNode node, int ch, TreeNode r, int rh[]) {
        if (node == null) {
            return;
        }
        middle(node.left, ch + 1, r, rh);
        if (ch > rh[0]) {
            r.val = node.val;
            rh[0] = ch;
        }
        middle(node.right, ch + 1, r, rh);
    }

    // https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/#/description
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (p.val == root.val || q.val == root.val) {
            return root;
        }
        if (p.val < root.val) {
            if (q.val > root.val) {
                return root;
            } else {
                return lowestCommonAncestor(root.left, p, q);
            }
        } else {
            if (q.val < root.val) {
                return root;
            } else {
                return lowestCommonAncestor(root.right, p, q);
            }
        }
    }

    // https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/#/description
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        Map<TreeNode, TreeNode> parents = new HashMap<>();
        genParent(root, parents);
        Set<TreeNode> pathP = new HashSet<>();
        TreeNode i = p;
        while (i != null) {
            pathP.add(i);
            i = parents.get(i);
        }
        i = q;
        while (!pathP.contains(i)) {
            i = parents.get(i);
        }
        return i;
    }

    private void genParent(TreeNode root, Map<TreeNode, TreeNode> parents) {
        if (root == null) return;
        if (root.left != null) {
            parents.put(root.left, root);
            genParent(root.left, parents);
        }
        if (root.right != null) {
            parents.put(root.right, root);
            genParent(root.right, parents);
        }
        return;
    }
}
