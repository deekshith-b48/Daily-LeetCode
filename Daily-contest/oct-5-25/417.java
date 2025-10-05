import java.util.*;

public class Solution {
    private int[][] directions = {{0,1}, {0,-1}, {1,0}, {-1,0}};

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;

        boolean[][] pacific = new boolean[m][n];
        boolean[][] atlantic = new boolean[m][n];

        // DFS from Pacific Ocean borders (top & left)
        for (int i = 0; i < m; i++) {
            dfs(heights, i, 0, pacific, -1); // left edge
        }
        for (int j = 0; j < n; j++) {
            dfs(heights, 0, j, pacific, -1); // top edge
        }

        // DFS from Atlantic Ocean borders (bottom & right)
        for (int i = 0; i < m; i++) {
            dfs(heights, i, n - 1, atlantic, -1); // right edge
        }
        for (int j = 0; j < n; j++) {
            dfs(heights, m - 1, j, atlantic, -1); // bottom edge
        }

        // Collect cells reachable from both oceans
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (pacific[i][j] && atlantic[i][j]) {
                    result.add(Arrays.asList(i, j));
                }
            }
        }

        return result;
    }

    private void dfs(int[][] heights, int r, int c, boolean[][] visited, int prevHeight) {
        // Check bounds, already visited, or too low
        if (r < 0 || r >= heights.length || c < 0 || c >= heights[0].length ||
            visited[r][c] || heights[r][c] < prevHeight) {
            return;
        }

        visited[r][c] = true;

        // Explore 4 directions
        for (int[] dir : directions) {
            int nr = r + dir[0];
            int nc = c + dir[1];
            dfs(heights, nr, nc, visited, heights[r][c]);
        }
    }
}
