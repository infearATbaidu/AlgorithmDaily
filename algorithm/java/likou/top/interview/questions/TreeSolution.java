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
        new TreeSolution().buildTree2(new int[] {3, 9, 20, 15, 7}, new int[] {9, 3, 15, 20, 7});
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

    /*    对于一个具有树特征的无向图，我们可选择任何一个节点作为根。图因此可以成为树，在所有可能的树中，具有最小高度的树被称为最小高度树。给出这样的一个图，写出一个函数找到所有的最小高度树并返回他们的根节点。
        格式：该图包含 n 个节点，标记为 0 到 n - 1。给定数字 n 和一个无向边 edges 列表（每一个边都是一对标签）。
        你可以假设没有重复的边会出现在 edges 中。由于所有的边都是无向边， [0, 1]和 [1, 0] 是相同的，因此不会同时出现在 edges 里。
        示例 1:
        输入: n = 4, edges = [[1, 0], [1, 2], [1, 3]]

                0
                |
                1
                / \
                2   3

        输出: [1]
        示例 2:
        输入: n = 6, edges = [[0, 3], [1, 3], [2, 3], [4, 3], [5, 4]]

                0  1  2
                \ | /
                3
                |
                4
                |
                5

        输出: [3, 4]*/
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

    /*    给定二叉树根结点 root ，此外树的每个结点的值要么是 0，要么是 1。

        返回移除了所有不包含 1 的子树的原二叉树。
                ( 节点 X 的子树为 X 本身，以及所有 X 的后代。)*/
    public TreeNode pruneTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = pruneTree(root.left);
        TreeNode right = pruneTree(root.right);
        if (left != null || right != null) {
            root.left = left;
            root.right = right;
            return root;
        }
        if (root.val == 1) {
            root.left = null;
            root.right = null;
            return root;
        }
        return null;
    }

    /*    输入某二叉树的前序遍历和中序遍历的结果，请重建该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
        例如，给出

        前序遍历 preorder = [3,9,20,15,7]
        中序遍历 inorder = [9,3,15,20,7]
        返回如下的二叉树：

                3
                / \
                9  20
                /  \
                15   7*/
    public TreeNode buildTree2(int[] preorder, int[] inorder) {
        return buildTree2(preorder, 0, inorder, 0, inorder.length);
    }

    private TreeNode buildTree2(int[] preorder, int s1, int[] inorder, int s2, int length) {
        if (length == 0) {
            return null;
        }
        int pos = -1;
        for (int i = s2; i != s2 + length; i++) {
            if (inorder[i] == preorder[s1]) {
                pos = i;
                break;
            }
        }
        TreeNode root = new TreeNode(preorder[s1]);
        root.left = buildTree2(preorder, s1 + 1, inorder, s2, pos - s2);
        root.right = buildTree2(preorder, s1 + 1 + pos - s2, inorder, pos + 1, s2 + length - pos - 1);
        return root;
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

    // use recursive to implement.
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        if (root == p || root == q) {
            return root;
        }
        TreeNode left = lowestCommonAncestor2(root.left, p, q);
        TreeNode right = lowestCommonAncestor2(root.right, p, q);
        if (left != null && right != null) {
            return root;
        }
        if (left == null) {
            return right;
        }
        return left;
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

    // 给你一棵二叉搜索树，请你返回一棵 平衡后 的二叉搜索树，新生成的树应该与原来的树有着相同的节点值。
    // 如果一棵二叉搜索树中，每个节点的两棵子树高度差不超过 1 ，我们就称这棵二叉搜索树是 平衡的 。
    // 如果有多种构造方法，请你返回任意一种。
    public TreeNode balanceBST(TreeNode root) {
        List<Integer> path = new LinkedList<>();
        mid(path, root);
        return balanceBST(path, 0, path.size());
    }

    private TreeNode balanceBST(List<Integer> path, int start, int end) {
        if (start >= end) {
            return null;
        }
        int mid = (start + end) / 2;
        TreeNode root = new TreeNode(path.get(mid));
        root.left = balanceBST(path, start, mid);
        root.right = balanceBST(path, mid + 1, end);
        return root;
    }

    // 给定一个单链表，其中的元素按升序排序，将其转换为高度平衡的二叉搜索树。
    // 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) {
            return null;
        }
        if (head.next == null) {
            return new TreeNode(head.val);
        }
        ListNode slow = head, fast = head, pre = head;
        while (fast.next != null && fast.next.next != null) {
            // use slow would cause no-balance tree,so use the position before slow.
            pre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode h2 = pre.next.next;
        TreeNode root = new TreeNode(pre.next.val);
        pre.next = null;
        root.left = sortedListToBST(head);
        root.right = sortedListToBST(h2);
        return root;
    }

    // 给定二叉搜索树（BST）的根节点和要插入树中的值，将值插入二叉搜索树。 返回插入后二叉搜索树的根节点。 保证原始二叉搜索树中不存在新值。
    // 注意，可能存在多种有效的插入方式，只要树在插入后仍保持为二叉搜索树即可。 你可以返回任意有效的结果。
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        if (val > root.val) {
            root.right = insertIntoBST(root.right, val);
        } else {
            root.left = insertIntoBST(root.left, val);
        }
        return root;
    }

    // 不同的二叉搜索树
    // https://leetcode-cn.com/problems/unique-binary-search-trees/
    public int numTrees(int n) {
        if (n < 1) {
            return 0;
        }
        int arr[] = new int[n + 1];
        arr[0] = 1;
        for (int i = 1; i <= n; i++) {
            int sum = 0;
            for (int j = 0; j != i; j++) {
                sum += arr[j] * arr[i - 1 - j];
            }
            arr[i] = sum;
        }
        return arr[n];
    }

    /*    给定一个二叉搜索树的根节点 root 和一个值 key，删除二叉搜索树中的 key 对应的节点，并保证二叉搜索树的性质不变。返回二叉搜索树（有可能被更新）的根节点的引用。
        一般来说，删除节点可分为两个步骤：
        首先找到需要删除的节点；
        如果找到了，删除它。*/
    // 迭代实现
    public TreeNode deleteNode(TreeNode root, int key) {
        TreeNode pre = root, cur = root;
        while (cur != null && cur.val != key) {
            pre = cur;
            if (cur.val > key) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        if (cur == null) {
            return root;
        }
        TreeNode deleted = deleteNode(cur);
        if (pre == cur) {
            return deleted;
        }
        if (pre.left == cur) {
            pre.left = deleted;
        } else {
            pre.right = deleted;
        }
        return root;
    }

    private TreeNode deleteNode(TreeNode cur) {
        if (cur.left == null && cur.right == null) {
            return null;
        }
        // 找到左子树中的最大值
        if (cur.left != null) {
            TreeNode left = cur.left, pre = left;
            while (left.right != null) {
                pre = left;
                left = left.right;
            }
            cur.val = left.val;
            if (pre == left) {
                cur.left = left.left;
            } else {
                pre.right = left.left;
            }
        } else {
            TreeNode right = cur.right, pre = right;
            while (right.left != null) {
                pre = right;
                right = right.left;
            }
            cur.val = right.val;
            if (pre == right) {
                cur.right = right.right;
            } else {
                pre.left = right.right;
            }
        }
        return cur;
    }

    // 删除二叉树节点的递归实现
    public TreeNode deleteNode2(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (root.val > key) {
            root.left = deleteNode(root.left, key);
            return root;
        } else if (root.val < key) {
            root.right = deleteNode(root.right, key);
            return root;
        }
        if (root.left == null && root.right == null) {
            return null;
        }
        if (root.left != null) {
            int max = findMaxInLeft(root.left);
            root.val = max;
            root.left = deleteNode(root.left, max);
        } else {
            int min = findMinInRight(root.right);
            root.val = min;
            root.right = deleteNode(root.right, min);
        }
        return root;
    }

    private int findMinInRight(TreeNode right) {
        while (right.left != null) {
            right = right.left;
        }
        return right.val;
    }

    private int findMaxInLeft(TreeNode left) {
        while (left.right != null) {
            left = left.right;
        }
        return left.val;
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

    // 给定一个二叉树，编写一个函数来获取这个树的最大宽度。树的宽度是所有层中的最大宽度。这个二叉树与满二叉树（full binary tree）结构相同，但一些节点为空。
    // 每一层的宽度被定义为两个端点（该层最左和最右的非空节点，两端点间的null节点也计入长度）之间的长度。
    // 链接：https://leetcode-cn.com/problems/maximum-width-of-binary-tree
    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int max = 0, start = 0, last = 1;
        List<TreeNode> tree = new ArrayList<>();
        tree.add(root);
        while (start != tree.size()) {
            int size = tree.size();
            max = Math.max(last - start, max);
            for (int index = start; index != size; index++) {
                TreeNode cur = tree.get(index);
                if (cur == null) {
                    if (tree.size() > size) {
                        tree.add(null);
                        tree.add(null);
                    }
                } else {
                    if (tree.size() > size || cur.left != null) {
                        tree.add(cur.left);
                    }
                    if (cur.left != null) {
                        last = tree.size();
                    }
                    if (tree.size() > size || cur.right != null) {
                        tree.add(cur.right);
                    }
                    if (cur.right != null) {
                        last = tree.size();
                    }
                }
            }
            start = size;
        }
        return max;
    }

    // https://leetcode-cn.com/problems/find-bottom-left-tree-value
    // 找到树左下角的值
    public int findBottomLeftValue(TreeNode root) {
        int leftBottom = 0;
        List<TreeNode> level = new ArrayList<>();
        int start = 0;
        level.add(root);
        while (start < level.size()) {
            leftBottom = level.get(start).val;
            int size = level.size();
            for (int index = start; index != size; index++) {
                TreeNode cur = level.get(index);
                if (cur.left != null) {
                    level.add(cur.left);
                }
                if (cur.right != null) {
                    level.add(cur.right);
                }
            }
            start = size;
        }
        return leftBottom;
    }

    // 二叉树中的列表
    // https://leetcode-cn.com/problems/linked-list-in-binary-tree/
    public boolean isSubPath(ListNode head, TreeNode root) {
        if (head == null) {
            return true;
        }
        if (root == null) {
            return false;
        }
        return checkSub(head, root) || isSubPath(head, root.left) || isSubPath(head, root.right);
    }

    private boolean checkSub(ListNode head, TreeNode root) {
        if (head == null) {
            return true;
        }
        if (root == null) {
            return false;
        }
        if (head.val != root.val) {
            return false;
        }
        return checkSub(head.next, root.left) || checkSub(head.next, root.right);
    }

    // 二叉树上有 n 个节点，按从 0 到 n - 1 编号，其中节点 i 的两个子节点分别是 leftChild[i] 和 rightChild[i]。
    // 只有 所有 节点能够形成且 只 形成 一颗 有效的二叉树时，返回 true；否则返回 false。
    // 如果节点 i 没有左子节点，那么 leftChild[i] 就等于 -1。右子节点也符合该规则。
    // 链接：https://leetcode-cn.com/problems/validate-binary-tree-nodes
    public boolean validateBinaryTreeNodes(int n, int[] leftChild, int[] rightChild) {
        int hasAncestor[] = new int[n];
        // tag all nodes except root.
        for (int i = 0; i != n; i++) {
            if (leftChild[i] != -1) {
                if (hasAncestor[leftChild[i]] == 1) {
                    return false;
                }
                hasAncestor[leftChild[i]] = 1;
            }
            if (rightChild[i] != -1) {
                if (hasAncestor[rightChild[i]] == 1) {
                    return false;
                }
                hasAncestor[rightChild[i]] = 1;
            }
        }
        // check root count.
        int root = -1;
        for (int i = 0; i != n; i++) {
            if (hasAncestor[i] == 0) {
                if (root != -1) {
                    return false;
                }
                root = i;
            }
        }
        int history[] = new int[n];
        visit(root, history, leftChild, rightChild);
        for (int i = 0; i != n; i++) {
            if (history[i] == 0) {
                return false;
            }
        }
        return true;
    }

    private boolean visit(int root, int[] history, int[] leftChild, int[] rightChild) {
        if (root == -1) {
            return true;
        }
        if (history[root] == 1) {
            return false;
        }
        history[root] = 1;
        return visit(leftChild[root], history, leftChild, rightChild) && visit(rightChild[root], history, leftChild,
                rightChild);
    }

    // https://leetcode-cn.com/problems/check-completeness-of-a-binary-tree/
    // 二叉树完全性校验
    public boolean isCompleteTree(TreeNode root) {
        List<TreeNode> path = new ArrayList<>();
        path.add(root);
        int start = 0;
        while (start < path.size()) {
            int end = path.size();
            int firstNull = -1;
            for (int i = start; i < end; i++) {
                if (path.get(i) == null) {
                    firstNull = i;
                    break;
                }
                path.add(path.get(i).left);
                path.add(path.get(i).right);
            }
            // 当出现null，表示之后都必须是null才是完全二叉树
            if (firstNull != -1) {
                for (int i = firstNull; i < path.size(); i++) {
                    if (path.get(i) != null) {
                        return false;
                    }
                }
            }
            start = end;
        }
        return true;
    }
}
