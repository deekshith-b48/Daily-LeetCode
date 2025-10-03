👨‍🏫 **Expert Algorithmic Solution**

---

### 1️⃣ **Exact Question Summary**  
Find the minimum time to reach the bottom-right room `(n-1, m-1)` in a grid starting from `(0, 0)`, where each room `(i, j)` can only be entered at or after time `moveTime[i][j]`, and moving to an adjacent room takes exactly 1 second.

---

### 2️⃣ **Key Concepts**  

- **Modified Dijkstra’s Algorithm**: The entry time to a cell depends on both the current time and the cell’s `moveTime`, making edge weights dynamic but non-negative.
- **State = (time, row, col)**: The "distance" to a cell is the earliest time we can **enter** it, computed as `max(current_time, moveTime[new]) + 1`.
- **Priority Queue (Min-Heap)**: Ensures we always expand the path with the smallest current time first, guaranteeing optimality.

---

### 3️⃣ **Important Observations & Constraints**  

- Grid size: `2 ≤ n, m ≤ 50` → at most 2500 cells.
- `0 ≤ moveTime[i][j] ≤ 1e9` → time values fit in `int` (since max path length is 98, total time ≤ 1e9 + 98 < 2.15e9).
- You **start in (0,0) at time 0**, and `moveTime[0][0]` is always 0 (per examples).
- Movement is 4-directional (up/down/left/right).
- **Correct entry time formula**: `new_time = max(current_time, moveTime[nr][nc]) + 1`.

---

### 4️⃣ **Worked Example (Step-by-Step)**  

**Input**: `moveTime = [[0,1],[1,2]]`  
Goal: reach (1,1)

- Start at (0,0) with time = 0.
- From (0,0), try (1,0):  
  `new_time = max(0, moveTime[1][0]=1) + 1 = 1 + 1 = 2`
- From (0,0), try (0,1):  
  `new_time = max(0, 1) + 1 = 2`
- Suppose we pick (1,0) with time = 2.
- From (1,0), go to (1,1):  
  `new_time = max(2, moveTime[1][1]=2) + 1 = 2 + 1 = 3`
- Return 3 ✅

---

### 5️⃣ **Key Insights**  

- You can **wait in the current cell** until the destination room opens, then move (taking 1 sec).
- The entry time to a neighbor is **not** `current_time + 1`, but `max(current_time, moveTime[neighbor]) + 1`.
- Dijkstra’s algorithm works because all "edge weights" (time increments) are non-negative.

---

### 6️⃣ **Step-by-Step Algorithm** 🛠️  

1. Initialize a 2D `dist` array with `Integer.MAX_VALUE`, except `dist[0][0] = 0`.
2. Use a min-heap storing `(time, row, col)`, start with `(0, 0, 0)`.
3. While heap not empty:
   - Pop the smallest-time state.
   - If it’s the destination `(n-1, m-1)`, return its time.
   - Skip if outdated (time > dist[r][c]).
   - For each of 4 neighbors:
     - If in bounds, compute `new_time = max(current_time, moveTime[nr][nc]) + 1`.
     - If `new_time < dist[nr][nc]`, update and push.
4. Return `dist[n-1][m-1]`.

**Pseudocode**:
```plaintext
dist[0][0] = 0
pq = min-heap with (0, 0, 0)
while pq not empty:
    (t, r, c) = pq.pop()
    if (r, c) == (n-1, m-1): return t
    if t > dist[r][c]: continue
    for each neighbor (nr, nc):
        new_t = max(t, moveTime[nr][nc]) + 1
        if new_t < dist[nr][nc]:
            dist[nr][nc] = new_t
            pq.push((new_t, nr, nc))
```

---

### 7️⃣ **Java Solution Code** ☕

```java
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
```

---

### 8️⃣ **Complexity Analysis** ⏱️  

- **Time Complexity**: `O(nm log(nm))`  
  Each cell is processed at most once with optimal time; heap operations cost `O(log(nm))`.

- **Space Complexity**: `O(nm)`  
  For the `dist` array and priority queue (stores up to `O(nm)` entries).

---

### 9️⃣ **Test Cases (5 total)** 🧪  

1. `[[0,1],[1,2]] → 3`  
   *Must wait for rooms to open; path takes 3 seconds.*

2. `[[0,0,0],[0,0,0]] → 3`  
   *No waiting; 3 moves in 2x3 grid.*

3. `[[0,2],[2,2]] → 4`  
   *Wait until t=2, then two moves: enter (1,0) at 3, (1,1) at 4.*

4. `50x50 grid of zeros → 98`  
   *Manhattan distance = 49+49 = 98 moves.*

5. `[[0,1,1],[1,1,1]] → 4`  
   *Three moves, each delayed to at least t=1, arriving at t=4.*

---

### 🔟 **Common Pitfalls & Optimizations** ⚠️  

- **❌ Wrong formula**: Using `time + 1` instead of `max(time, moveTime[new]) + 1` → fails examples.
- **❌ BFS instead of Dijkstra**: BFS assumes uniform cost; here cost depends on current time.
- **✅ Early termination**: Return immediately when popping destination from heap.
- **✅ Use `int` for time**: Safe since max time ≈ 1e9 + 100 < `Integer.MAX_VALUE`.
