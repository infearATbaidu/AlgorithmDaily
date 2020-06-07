package likou.top.interview.questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author infear
 */
public class TreeSolution {

    public static void main(String args[]) {
        new TreeSolution().findMinHeightTrees(4, new int[][] {{1, 0}, {1, 2}, {1, 3}});
    }

    /*    https://leetcode-cn.com/problems/shu-de-zi-jie-gou-lcof/
        输入两棵二叉树A和B，判断B是不是A的子结构。(约定空树不是任意一个树的子结构)
        B是A的子结构， 即A中有出现和B相同的结构和节点值。
        例如:
        给定的树 A:

                     3
                    / \
                   4   5
                  / \
                 1   2
        给定的树 B：

                   4 
                  /
                 1
        返回 true，因为 B 与 A 的一个子树拥有相同的结构和节点值。*/
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if (A == null || B == null) {
            return false;
        }
        if (contains(A, B)) {
            return true;
        }
        return isSubStructure(A.left, B) || isSubStructure(A.right, B);
    }

    private boolean contains(TreeNode a, TreeNode b) {
        if (b == null) {
            return true;
        }
        if (a == null) {
            return false;
        }
        if (a.val != b.val) {
            return false;
        }
        return contains(a.left, b.left) && contains(a.right, b.right);

    }

    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if (edges.length == 0) {
            return Arrays.asList(0);
        }
        Map<Integer, Set<Integer>> edge = new HashMap<>();
        for (int[] e : edges) {
            edge.putIfAbsent(e[0], new HashSet());
            edge.get(e[0]).add(e[1]);
            edge.putIfAbsent(e[1], new HashSet());
            edge.get(e[1]).add(e[0]);
        }
        Set<Integer> last = new HashSet<>();
        while (!edge.isEmpty()) {
            Set<Integer> oneDegree = stats(edge);
            for (Integer src : oneDegree) {
                for (Integer dest : edge.getOrDefault(src, new HashSet<>())) {
                    edge.get(dest).remove(src);
                }
                edge.remove(src);
            }
            last = oneDegree;
        }
        return new LinkedList<>(last);
    }

    private Set<Integer> stats(Map<Integer, Set<Integer>> edge) {
        Set<Integer> result = new HashSet<>();
        for (Integer src : edge.keySet()) {
            if (edge.get(src).size() <= 1) {
                result.add(src);
            }
        }
        return result;
    }

    public int kthSmallest(TreeNode root, int k) {
        LinkedList<TreeNode> path = new LinkedList<>();
        int cnt = 0;
        TreeNode cur = root;
        while (cur != null || !path.isEmpty()) {
            while (cur != null) {
                path.add(cur);
                cur = cur.left;
            }
            TreeNode top = path.pollLast();
            cnt++;
            if (cnt == k) {
                return top.val;
            }
            cur = top.right;
        }
        return -1;
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

    /*    中序遍历二叉树
        给定一个二叉树，返回它的中序 遍历。

        示例:

        输入: [1,null,2,3]
                1
                \
                2
                /
                3

        输出: [1,3,2]
        进阶: 递归算法很简单，你可以通过迭代算法完成吗？*/
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> r = new LinkedList<>();
        LinkedList<TreeNode> s = new LinkedList();
        TreeNode cur = root;
        while (cur != null || !s.isEmpty()) {
            while (cur != null) {
                s.push(cur);
                cur = cur.left;
            }
            cur = s.pollLast();
            r.add(cur.val);
            cur = cur.right;
        }
        return r;
    }

    /*    二叉树的锯齿形层次遍历
        给定一个二叉树，返回其节点值的锯齿形层次遍历。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。

        例如：
        给定二叉树 [3,9,20,null,null,15,7],

                3
                / \
                9  20
                /  \
                15   7
        返回锯齿形层次遍历如下：

                [
                [3],
                [20,9],
                [15,7]
                ]*/
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        boolean forward = true;
        LinkedList<TreeNode> l = new LinkedList<>();
        l.add(root);
        List<List<Integer>> r = new LinkedList<>();
        while (!l.isEmpty()) {
            LinkedList<TreeNode> tmp = new LinkedList<>();
            LinkedList<Integer> val = new LinkedList<>();
            while (!l.isEmpty()) {
                TreeNode top = l.pop();
                val.add(top.val);
                if (forward) {
                    if (top.left != null) {
                        tmp.add(0, top.left);
                    }
                    if (top.right != null) {
                        tmp.add(0, top.right);
                    }
                } else {
                    if (top.right != null) {
                        tmp.add(0, top.right);
                    }
                    if (top.left != null) {
                        tmp.add(0, top.left);
                    }
                }
            }
            forward = !forward;
            l = tmp;
            r.add(val);
        }
        return r;

    }

    public List<List<Integer>> zigzagLevelOrder2(TreeNode root) {
        List<List<Integer>> r = new LinkedList();
        if (root == null) {
            return r;
        }
        LinkedList<TreeNode> cur = new LinkedList();
        cur.add(root);
        boolean left = true;
        while (!cur.isEmpty()) {
            LinkedList<Integer> tmp = new LinkedList();
            LinkedList<TreeNode> copy = new LinkedList<>();
            while (!cur.isEmpty()) {
                TreeNode top = cur.pop();
                if (left) {
                    tmp.add(top.val);
                } else {
                    tmp.add(0, top.val);
                }
                if (top.left != null) {
                    copy.add(top.left);
                }
                if (top.right != null) {
                    copy.add(top.right);
                }
            }
            r.add(tmp);
            cur = copy;
            left = !left;
        }
        return r;
    }

    /*    从前序与中序遍历序列构造二叉树
        根据一棵树的前序遍历与中序遍历构造二叉树。

        注意:
        你可以假设树中没有重复的元素。

        例如，给出

        前序遍历 preorder = [3,9,20,15,7]
        中序遍历 inorder = [9,3,15,20,7]
        返回如下的二叉树：

                3
                / \
                9  20
                /  \
                15   7*/
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // construct each element map to index in inorder, which make search in O(1);
        HashMap pos = new HashMap();
        for (int i = 0; i != inorder.length; i++) {
            pos.put(inorder[i], i);
        }
        return buildTree(preorder, 0, preorder.length, inorder, 0, inorder.length, pos);
    }

    private TreeNode buildTree(int[] preorder, int s1, int e1, int[] inorder, int s2, int e2,
                               HashMap<Integer, Integer> pos) {
        if (s1 >= e1 || s2 >= e2) {
            return null;
        }
        int index = pos.get(preorder[s1]);
        TreeNode root = new TreeNode(preorder[s1]);
        root.left = buildTree(preorder, s1 + 1, s1 + 1 + index - s2, inorder, s2, index, pos);
        root.right = buildTree(preorder, s1 + 1 + index - s2, e1, inorder, index + 1, e2, pos);
        return root;
    }

    /*    填充每个节点的下一个右侧节点指针
        给定一个完美二叉树，其所有叶子节点都在同一层，每个父节点都有两个子节点。二叉树定义如下：
        struct Node {
            int val;
            Node *left;
            Node *right;
            Node *next;
        }
        填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
        初始状态下，所有 next 指针都被设置为 NULL。
        示例：
        输入：{"$id":"1","left":{"$id":"2","left":{"$id":"3","left":null,"next":null,"right":null,"val":4},"next":null,
        "right":{"$id":"4","left":null,"next":null,"right":null,"val":5},"val":2},"next":null,"right":{"$id":"5",
        "left":{"$id":"6","left":null,"next":null,"right":null,"val":6},"next":null,"right":{"$id":"7","left":null,
        "next":null,"right":null,"val":7},"val":3},"val":1}
        输出：{"$id":"1","left":{"$id":"2","left":{"$id":"3","left":null,"next":{"$id":"4","left":null,
        "next":{"$id":"5","left":null,"next":{"$id":"6","left":null,"next":null,"right":null,"val":7},"right":null,
        "val":6},"right":null,"val":5},"right":null,"val":4},"next":{"$id":"7","left":{"$ref":"5"},"next":null,
        "right":{"$ref":"6"},"val":3},"right":{"$ref":"4"},"val":2},"next":null,"right":{"$ref":"7"},"val":1}

        解释：给定二叉树如图 A 所示，你的函数应该填充它的每个 next 指针，以指向其下一个右侧节点，如图 B 所示。
        提示：

        你只能使用常量级额外空间。
        使用递归解题也符合要求，本题中递归程序占用的栈空间不算做额外的空间复杂度。*/
    public Node connect(Node root) {
        if (root == null) {
            return null;
        }
        LinkedList<Node> s = new LinkedList<>();
        s.add(root);
        while (!s.isEmpty()) {
            int size = s.size();
            Node cur = null;
            while (size-- != 0) {
                Node top = s.pop();
                if (top.left != null) {
                    s.add(top.left);
                }
                if (top.right != null) {
                    s.add(top.right);
                }
                if (cur != null) {
                    cur.next = top;
                }
                cur = top;
            }
        }
        return root;
    }

    /*    给定一个二叉树，判断其是否是一个有效的二叉搜索树。

        假设一个二叉搜索树具有如下特征：

        节点的左子树只包含小于当前节点的数。
        节点的右子树只包含大于当前节点的数。
        所有左子树和右子树自身必须也是二叉搜索树。

        来源：力扣（LeetCode）
        链接：https://leetcode-cn.com/problems/validate-binary-search-tree
        著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。*/
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isValidBST(TreeNode root, long minValue, long maxValue) {
        if (root == null) {
            return true;
        }
        if (root.val >= maxValue || root.val <= minValue) {
            return false;
        }
        return isValidBST(root.left, minValue, root.val) && isValidBST(root.right, root.val, maxValue);
    }

    public boolean isValidBST2(TreeNode root) {
        List<Integer> result = new LinkedList();
        mid(result, root);
        if (result.size() <= 1) {
            return true;
        }
        for (int i = 1; i != result.size(); i++) {
            if (result.get(i) <= result.get(i - 1)) {
                return false;
            }
        }
        return true;
    }

    void mid(List<Integer> path, TreeNode node) {
        if (node == null) {
            return;
        }
        mid(path, node.left);
        path.add(node.val);
        mid(path, node.right);
    }

    static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /*    二叉树的序列化与反序列化
        序列化是将一个数据结构或者对象转换为连续的比特位的操作，进而可以将转换后的数据存储在一个文件或者内存中，同时也可以通过网络传输到另一个计算机环境，采取相反方式重构得到原数据。

        请设计一个算法来实现二叉树的序列化与反序列化。这里不限定你的序列 /反序列化算法执行逻辑，你只需要保证一个二叉树可以被序列化为一个字符串并且将这个字符串反序列化为原始的树结构。

        示例:

        你可以将以下二叉树：

                1
                / \
                2 3
                / \
                4 5

        序列化为 "[1,2,3,null,null,4,5]"
        提示:
        这与 LeetCode
        目前使用的方式一致，
        详情请参阅 LeetCode
        序列化二叉树的格式。你并非必须采取这种方式，你也可以采用其他的方法解决这个问题。

        说明:不要使用类的成员 /全局 /静态变量来存储状态，你的序列化和反序列化算法应该是无状态的。*/
    static class Codec {

        // Encodes a tree to a single string.
        public static String serialize(TreeNode root) {
            if (root == null) {
                return null;
            }
            LinkedList<String> result = new LinkedList<>();
            LinkedList<TreeNode> queue = new LinkedList();
            queue.add(root);
            while (!queue.isEmpty()) {
                TreeNode first = queue.pop();
                if (first != null) {
                    queue.add(first.left);
                    queue.add(first.right);
                }
                result.add(first == null ? null : String.valueOf(first.val));
            }
            return String.join(",", result);
        }

        // Decodes your encoded data to tree.
        public static TreeNode deserialize(String data) {
            if (data == null) {
                return null;
            }
            String[] result = data.split(",");
            int i = 0;
            LinkedList<TreeNode> queue = new LinkedList<>();
            TreeNode root = new TreeNode(Integer.valueOf(result[i]));
            queue.add(root);
            while (!queue.isEmpty()) {
                TreeNode first = queue.pop();
                if (first == null) {
                    continue;
                }
                i++;
                if (i >= result.length) {
                    break;
                }
                TreeNode left = (result[i].equals("null") ? null : new TreeNode(Integer.valueOf(result[i])));
                first.left = left;
                queue.add(left);
                i++;
                TreeNode right = (result[i].equals("null") ? null : new TreeNode(Integer.valueOf(result[i])));
                first.right = right;
                queue.add(right);
            }
            return root;
        }
    }
}
