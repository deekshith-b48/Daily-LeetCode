import java.util.*;

class Solution {
    public int swimInWater(int[][] grid) {
        int n = grid.length;
        int left = grid[0][0];
        int right = 0;
        
        // Find max elevation
        for (int[] row : grid) {
            for (int val : row) {
                right = Math.max(right, val);
            }
        }
        
        // Binary search on time t
        while (left < right) {
            int mid = (left + right) / 2;
            if (canReach(grid, mid)) {
                right = mid;      // Try smaller t
            } else {
                left = mid + 1;   // Need larger t
            }
        }
        return left;
    }
    
    private boolean canReach(int[][] grid, int t) {
        int n = grid.length;
        if (grid[0][0] > t) return false;
        
        boolean[][] visited = new boolean[n][n];
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{0, 0});
        visited[0][0] = true;
        
        int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
        
        while (!q.isEmpty()) {
            int[] cell = q.poll();
            int r = cell[0], c = cell[1];
            
            if (r == n - 1 && c == n - 1) return true;
            
            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n 
                    && !visited[nr][nc] && grid[nr][nc] <= t) {
                    visited[nr][nc] = true;
                    q.offer(new int[]{nr, nc});
                }
            }
        }
        return false;
    }
}