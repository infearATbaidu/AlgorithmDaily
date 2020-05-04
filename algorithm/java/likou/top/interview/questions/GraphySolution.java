package likou.top.interview.questions;

/**
 * @author infear
 */
public class GraphySolution {
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
}
