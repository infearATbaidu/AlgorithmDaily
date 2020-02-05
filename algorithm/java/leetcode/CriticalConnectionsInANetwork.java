package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @author infear
 */
public class CriticalConnectionsInANetwork {
    public static void main(String args[]) {
        List<List<Integer>> arr = Arrays.asList(Arrays.asList(0, 1), Arrays.asList(1, 2), Arrays.asList(2, 0), Arrays.asList(1, 3));
        new CriticalConnectionsInANetwork().criticalConnections(4, arr);
    }

    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        HashMap<Integer, HashSet<Integer>> graph = new HashMap();
        for (List<Integer> conn : connections) {
            graph.putIfAbsent(conn.get(0), new HashSet());
            graph.get(conn.get(0)).add(conn.get(1));
            graph.putIfAbsent(conn.get(1), new HashSet());
            graph.get(conn.get(1)).add(conn.get(0));
        }
        List<List<Integer>> result = new ArrayList<>();
        int[] degree = new int[n];
        for (int i = 0; i < n; i++) {
            degree[i] = -1;
        }
        dfs(0, -1, 0, degree, result, graph);
        return result;
    }

    public int dfs(int curN, int father, int degree, int[] degreeL, List<List<Integer>> result, HashMap<Integer, HashSet<Integer>> graph) {
        degreeL[curN] = degree + 1;
        for (Integer item : graph.get(curN)) {
            if (item.equals(father)) {
                continue;
            } else if (degreeL[item.intValue()] == -1) {
                int childID = dfs(item, curN, degree + 1, degreeL, result, graph);
                degreeL[curN] = Math.min(degreeL[curN], childID);
            } else {
                degreeL[curN] = Math.min(degreeL[curN], degreeL[item]);
            }
        }
        if (degreeL[curN] == degree + 1 && curN != 0) {
            List<Integer> edge = new ArrayList<>();
            edge.add(father);
            edge.add(curN);
            result.add(edge);
        }
        return degreeL[curN];
    }
}
