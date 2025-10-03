import java.util.*;

class Solution {
    public int minTimeToReach(int[][] moveTime) {
        int n = moveTime.length;
        int m = moveTime[0].length;
        
        // Initialize distance array with large values
        int[][] dist = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        
        // Min-heap: [time, row, col]
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        dist[0][0] = 0;
        pq.offer(new int[]{0, 0, 0});
        
        // Direction vectors for 4 neighbors
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int time = curr[0];
            int r = curr[1];
            int c = curr[2];
            
            // Early termination: reached destination
            if (r == n - 1 && c == m - 1) {
                return time;
            }
            
            // Skip if we already found a better time for this cell
            if (time > dist[r][c]) {
                continue;
            }
            
            // Explore all 4 adjacent rooms
            for (int[] dir : dirs) {
                int nr = r + dir[0];
                int nc = c + dir[1];
                
                // Check if neighbor is within grid bounds
                if (nr >= 0 && nr < n && nc >= 0 && nc < m) {
                    // Compute earliest possible entry time for neighbor
                    int newTime = Math.max(time, moveTime[nr][nc]) + 1;
                    
                    // Relaxation: update if we found a faster way
                    if (newTime < dist[nr][nc]) {
                        dist[nr][nc] = newTime;
                        pq.offer(new int[]{newTime, nr, nc});
                    }
                }
            }
        }
        
        // In case destination wasn't reached (shouldn't happen per constraints)
        return dist[n - 1][m - 1];
    }
}
