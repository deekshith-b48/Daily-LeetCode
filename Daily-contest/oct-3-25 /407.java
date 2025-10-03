import java.util.*;

class Solution {
    public int trapRainWater(int[][] heightMap) {
        if (heightMap == null || heightMap.length <= 2 || heightMap[0].length <= 2) {
            return 0;
        }

        int m = heightMap.length;
        int n = heightMap[0].length;

        // Priority Queue: stores [height, row, col], sorted by height (min-heap)
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        // Visited matrix
        boolean[][] visited = new boolean[m][n];

        // Add all boundary cells to the heap
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || i == m - 1 || j == 0 || j == n - 1) {
                    pq.offer(new int[]{heightMap[i][j], i, j});
                    visited[i][j] = true;
                }
            }
        }

        // Directions: up, down, left, right
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        int totalWater = 0;

        while (!pq.isEmpty()) {
            int[] cell = pq.poll();
            int h = cell[0];
            int i = cell[1];
            int j = cell[2];

            // Check 4 neighbors
            for (int[] dir : dirs) {
                int ni = i + dir[0];
                int nj = j + dir[1];

                // Skip if out of bounds or already visited
                if (ni < 0 || ni >= m || nj < 0 || nj >= n || visited[ni][nj]) {
                    continue;
                }

                // Mark as visited
                visited[ni][nj] = true;

                // If neighbor is lower, water can be trapped
                if (heightMap[ni][nj] < h) {
                    totalWater += h - heightMap[ni][nj];
                }

                // Push neighbor with effective height (max of its own height and current wall)
                pq.offer(new int[]{Math.max(h, heightMap[ni][nj]), ni, nj});
            }
        }

        return totalWater;
    }
}
