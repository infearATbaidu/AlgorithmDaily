package likou.top.interview.questions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author infear
 */
public class GraphySolution {
    public static void main(String args[]) {
        new GraphySolution().findOrder(2, new int[][] {{0, 1}});
    }

    public int numIslands(char[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int r = 0;
        boolean visit[][] = new boolean[grid.length][grid[0].length];
        for (int i = 0; i != grid.length; i++) {
            for (int j = 0; j != grid[0].length; j++) {
                if (grid[i][j] == '0' || visit[i][j]) {
                    continue;
                }
                search(grid, i, j, visit);
                r++;
            }
        }
        return r;
    }

    private void search(char[][] grid, int i, int j, boolean[][] visit) {
        visit[i][j] = true;
        if (i - 1 >= 0 && !visit[i - 1][j] && grid[i - 1][j] == '1') {
            search(grid, i - 1, j, visit);
        }
        if (i + 1 < grid.length && !visit[i + 1][j] && grid[i + 1][j] == '1') {
            search(grid, i + 1, j, visit);
        }
        if (j - 1 >= 0 && !visit[i][j - 1] && grid[i][j - 1] == '1') {
            search(grid, i, j - 1, visit);
        }
        if (j + 1 < grid[0].length && !visit[i][j + 1] && grid[i][j + 1] == '1') {
            search(grid, i, j + 1, visit);
        }
    }

    /*    单词接龙
        给定两个单词（beginWord 和 endWord）和一个字典，找到从 beginWord 到 endWord 的最短转换序列的长度。转换需遵循如下规则：

        每次转换只能改变一个字母。
        转换过程中的中间单词必须是字典中的单词。
        说明:

        如果不存在这样的转换序列，返回 0。
        所有单词具有相同的长度。
        所有单词只由小写字母组成。
        字典中不存在重复的单词。
        你可以假设 beginWord 和 endWord 是非空的，且二者不相同。
        示例 1:

        输入:
        beginWord = "hit",
        endWord = "cog",
        wordList = ["hot","dot","dog","lot","log","cog"]

        输出: 5
        解释: 一个最短转换序列是 "hit" -> "hot" -> "dot" -> "dog" -> "cog",
        返回它的长度 5。
        示例 2:

        输入:
        beginWord = "hit"
        endWord = "cog"
        wordList = ["hot","dot","dog","lot","log"]

        输出: 0
        解释: endWord "cog" 不在字典中，所以无法进行转换。*/
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // 剩余未访问过的单词
        Set<String> all = new HashSet<>(wordList);
        if (!all.contains(endWord)) {
            return 0;
        }
        all.remove(endWord);
        all.add(beginWord);

        // 当前层的单词，第一层为endword，第二层为和endword距离为1的单词集合，第三层为和第二层中某个单词距离为1的单词集合，以此类推
        Set<String> curLayer = new HashSet<>();
        curLayer.add(endWord);
        int i = 1;
        while (!curLayer.contains(beginWord)) {
            if (curLayer.isEmpty()) {
                return 0;
            }
            Set<String> tmp = new HashSet<>();
            for (String target : curLayer) {
                for (String src : all) {
                    if (reachable(src, target)) {
                        tmp.add(src);
                    }
                }
            }
            // 将当前层的单词从剩余的单词集合中移除，并重置当前层，层数+1
            all.removeAll(tmp);
            curLayer = tmp;
            i++;
        }
        return i;
    }

    private boolean reachable(String src, String target) {
        int life = 1;
        for (int i = 0; i != src.length(); i++) {
            if (src.charAt(i) != target.charAt(i)) {
                life--;
                if (life < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        HashMap<Integer, Set<Integer>> m = getPreMap(prerequisites);
        boolean[] visited = new boolean[numCourses], path = new boolean[numCourses];
        for (int i = 0; i != numCourses; i++) {
            if (visited[i]) {
                continue;
            }
            if (!dfs(i, visited, path, m)) {
                return false;
            }
        }
        return true;
    }

    private boolean dfs(int i, boolean[] visited, boolean[] path, HashMap<Integer, Set<Integer>> prerequisites) {
        if (path[i]) {
            return false;
        }
        visited[i] = true;
        path[i] = true;
        for (Integer post : prerequisites.getOrDefault(i, new HashSet<>())) {
            if (!dfs(post, visited, path, prerequisites)) {
                return false;
            }
        }
        path[i] = false;
        return true;
    }

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        HashMap<Integer, Set<Integer>> preToPost = new HashMap<>(), postToPre = new HashMap<>();
        for (int[] pair : prerequisites) {
            int pre = pair[1], post = pair[0];
            preToPost.putIfAbsent(pre, new HashSet<>());
            preToPost.get(pre).add(post);
            postToPre.putIfAbsent(post, new HashSet<>());
            postToPre.get(post).add(pre);
        }
        // calculate the most outer layer
        Set<Integer> all = new HashSet<>(), outer = new HashSet<>();
        for (int i = 0; i != numCourses; i++) {
            all.add(i);
            if (!preToPost.containsKey(i)) {
                outer.add(i);
            }
        }
        int[] result = new int[numCourses];
        int index = numCourses;
        while (!all.isEmpty()) {
            if (outer.isEmpty()) {
                return new int[0];
            }
            Set<Integer> outerCopy = new HashSet();
            for (int ele : outer) {
                result[--index] = ele;
                all.remove(ele);
                if (postToPre.containsKey(ele)) {
                    for (int pre : postToPre.get(ele)) {
                        preToPost.get(pre).remove(ele);
                        if (preToPost.get(pre).isEmpty() && all.contains(pre)) {
                            outerCopy.add(pre);
                        }
                    }
                }
            }
            outer = outerCopy;
        }
        return result;
    }

    private HashMap<Integer, Set<Integer>> getPreMap(int[][] prerequisites) {
        HashMap<Integer, Set<Integer>> m = new HashMap<>();
        for (int[] pair : prerequisites) {
            int pre = pair[1], post = pair[0];
            m.putIfAbsent(pre, new HashSet<>());
            m.get(pre).add(post);
        }
        return m;
    }
}
