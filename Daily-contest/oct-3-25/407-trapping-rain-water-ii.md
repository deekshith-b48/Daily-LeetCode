### 🔹 **Question Summary**

Given an `m x n` matrix `heightMap` representing the elevation of each cell, compute the **total volume of rainwater** that can be trapped after raining.  
Water flows in **4 directions** (up/down/left/right) and **cannot be trapped at the boundaries** (it flows out).

> **Goal**: Simulate how water fills basins from the outside in, constrained by surrounding "walls".

---

### 🔹 **Important Observation**

- In 1D (Trapping Rain Water I), we use two pointers or precomputed left/right max arrays.
- In 2D, water can **leak in any direction**, so the trapping height is determined by the **lowest point on the enclosing boundary**.
- **Key idea**: The **minimum height on the current boundary** limits how much water inner cells can hold.
- Therefore, we must **process cells in increasing order of boundary height** → use a **min-heap**.

---

### 🔹 **Example Recap**

#### ✅ Example 1:
```java
Input: [[1,4,3,1,3,2],
        [3,2,1,3,2,4],
        [2,3,3,2,3,1]]
Output: 4
```
- Two small ponds trap 1 and 3 units → total = 4.

#### ✅ Example 2:
```java
Input: [[3,3,3,3,3],
        [3,2,2,2,3],
        [3,2,1,2,3],
        [3,2,2,2,3],
        [3,3,3,3,3]]
Output: 10
```
- A deep central basin (height 1) surrounded by walls of height 3 → traps 2 units in center + 8 in surrounding ring = 10.

---

### 🔹 **Key Insights**

1. **Boundary cells cannot trap water** → they act as initial "walls".
2. Use a **min-heap (priority queue)** to always expand from the **lowest wall** → ensures correct water level calculation.
3. When visiting an inner cell:
   - If its height < current boundary height → water trapped = `boundary - cell_height`.
   - The **effective height** of the cell (for future neighbors) becomes `max(cell_height, boundary)` (since water fills up to the boundary level).
4. This is a **multi-source Dijkstra-like BFS** where distance = "water containment height".

---

### 🔹 **Step-by-Step Algorithm**

1. **Edge Case**: If grid has ≤ 2 rows or columns → no interior → return 0.
2. **Initialize**:
   - Min-heap to store `[height, row, col]`.
   - `visited[][]` to avoid reprocessing.
3. **Add all boundary cells** to the heap and mark them visited.
4. **While heap not empty**:
   - Pop the cell with **smallest height** (current lowest boundary).
   - For each of its 4 neighbors:
     - Skip if out of bounds or already visited.
     - Mark neighbor as visited.
     - If neighbor’s height < current boundary height → add trapped water.
     - Push neighbor into heap with height = `max(neighbor_height, current_boundary_height)`.
5. Return total trapped water.

---

### 🔹 **Solution Code (Clean & Annotated)**

```java
import java.util.*;

class Solution {
    public int trapRainWater(int[][] heightMap) {
        // Edge case: no interior cells possible
        if (heightMap == null || heightMap.length <= 2 || heightMap[0].length <= 2) {
            return 0;
        }

        int m = heightMap.length;
        int n = heightMap[0].length;

        // Min-heap: [height, row, col]
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        boolean[][] visited = new boolean[m][n];

        // Directions for 4 neighbors
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        // Step 1: Add all boundary cells to the heap
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || i == m - 1 || j == 0 || j == n - 1) {
                    pq.offer(new int[]{heightMap[i][j], i, j});
                    visited[i][j] = true;
                }
            }
        }

        int totalWater = 0;

        // Step 2: Process cells from lowest boundary inward
        while (!pq.isEmpty()) {
            int[] cell = pq.poll();
            int boundaryHeight = cell[0];
            int i = cell[1];
            int j = cell[2];

            for (int[] dir : dirs) {
                int ni = i + dir[0];
                int nj = j + dir[1];

                // Skip invalid or visited cells
                if (ni < 0 || ni >= m || nj < 0 || nj >= n || visited[ni][nj]) {
                    continue;
                }

                visited[ni][nj] = true;

                // Water can be trapped if neighbor is lower than current boundary
                if (heightMap[ni][nj] < boundaryHeight) {
                    totalWater += boundaryHeight - heightMap[ni][nj];
                }

                // Push neighbor with its effective height (after water fills)
                pq.offer(new int[]{
                    Math.max(boundaryHeight, heightMap[ni][nj]),
                    ni,
                    nj
                });
            }
        }

        return totalWater;
    }
}
```

---

### ✅ Why This Works

- Always processes the **lowest possible wall** first → guarantees accurate water level for inner cells.
- Uses **BFS + priority queue** to simulate "flooding from the outside".
- Efficient and optimal: **O(mn log(mn)) time**, **O(mn) space**.

This approach elegantly extends the 1D intuition to 2D using heap-based boundary expansion.
