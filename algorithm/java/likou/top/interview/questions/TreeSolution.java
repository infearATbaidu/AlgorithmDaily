package likou.top.interview.questions;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author infear
 */
public class TreeSolution {
    public static void main(String args[]) {
        TreeNode t1 = new TreeNode(1);
        TreeNode t2 = new TreeNode(2);
        TreeNode t3 = new TreeNode(3);
        t1.right = t2;
        t2.left = t3;
        String ser = Codec.serialize(t1);
        System.out.println(ser);
        TreeNode root = Codec.deserialize(ser);
        ser = Codec.serialize(root);
        System.out.println(ser);
    }

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
