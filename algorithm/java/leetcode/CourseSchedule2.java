package leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


/**
 * https://leetcode.com/problems/course-schedule-ii/
 *
 * @author Zhang Gang (zhanggang02@baidu.com)
 */
public class CourseSchedule2 {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // cal children from prerequisites
        Map<Integer, Set<Integer>> m = new HashMap<>();
        for (int[] pair : prerequisites) {
            m.putIfAbsent(pair[1], new HashSet<>());
            m.get(pair[1]).add(pair[0]);
        }
        int hasRead[] = new int[numCourses];
        LinkedList<Integer> result = new LinkedList();
        int index = 0;
        while (index != numCourses) {
            if (hasRead[index] != 1) {
                Set<Integer> path = new HashSet<>();
                path.add(index);
                if (!dfs(index, m, path, hasRead, result)) {
                    return new int[0];
                }
            }
            index++;
        }
        int rr[] = new int[numCourses];
        int i = 0;
        Iterator<Integer> it = result.iterator();
        while (i != numCourses) {
            rr[i++] = it.next();
        }
        return rr;
    }

    private boolean dfs(int index, Map<Integer, Set<Integer>> m, Set<Integer> path, int[] hasRead, LinkedList<Integer> result) {
        if (m.containsKey(index)) {
            for (Integer child : m.get(index)) {
                if (path.contains(child)) {
                    return false;
                }
                if (hasRead[child] == 1) {
                    continue;
                }
                path.add(child);
                if (!dfs(child, m, path, hasRead, result)) {
                    return false;
                }
            }
        }
        path.remove(index);
        hasRead[index] = 1;
        result.addFirst(index);
        return true;
    }
}
