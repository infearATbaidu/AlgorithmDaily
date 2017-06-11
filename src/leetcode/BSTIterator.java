package leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by infear on 2017/6/11.
 * https://leetcode.com/problems/binary-search-tree-iterator/#/description
 */
public class BSTIterator {
    int cur = 0;
    List<TreeNode> path;

    public BSTIterator(TreeNode root) {
        path = new LinkedList<>();
        middle(root);
    }

    private void middle(TreeNode node) {
        if (node == null) {
            return;
        }
        middle(node.left);
        path.add(node);
        middle(node.right);
    }


    /**
     * @return whether we have a next smallest number
     */
    public boolean hasNext() {
        return cur < path.size();
    }

    /**
     * @return the next smallest number
     */
    public int next() {
        return path.get(cur++).val;
    }
}
