Below is a **comprehensive, step-by-step explanation report** for the problem:

---

# 🌧️ **LeetCode 1488. Avoid Flood in The City**  
**Medium | Greedy + Binary Search (TreeSet)**

---

## 1️⃣ **Question Summary**

### 🔹 What is Given?
- An integer array `rains` of length `n` (up to `10⁵`).
- Each element:
  - `rains[i] > 0` → it rains on **lake `rains[i]`** on day `i`.
  - `rains[i] == 0` → **no rain** on day `i`; you can **dry one lake** of your choice.

### 🔹 What is the Goal?
Return an array `ans` such that:
- `ans[i] = -1` if `rains[i] > 0` (you can't control rain).
- `ans[i] = X` (a lake number) if `rains[i] == 0` (you dried lake `X`).
- **No lake ever floods** — i.e., it never rains on a lake that is already full **and hasn’t been dried since**.
- If it’s **impossible** to avoid a flood, return an **empty array** `[]`.

> 💡 **Key Rule**:  
> - A lake becomes **full** when it rains on it.  
> - It becomes **empty** only if you **explicitly dry it on a dry day**.  
> - If it rains again on a **full** lake → **flood** → invalid.

---

## 2️⃣ **Important Observations & Constraints**

| Observation | Explanation |
|-----------|-------------|
| **Dry days are precious** | You can only prevent floods by using dry days **between two rains on the same lake**. |
| **Order matters** | You cannot dry a lake **before** it rains. Drying only helps **after** it’s full and **before** it rains again. |
| **Greedy choice is optimal** | When a lake is about to flood, use the **earliest possible dry day** after it became full. This leaves later dry days for other lakes. |
| **Lake IDs can be large** | Up to `10⁹` → use `HashMap`, not array indexing. |
| **Unused dry days** | Can dry any lake (even empty ones) — output any valid number (e.g., `1`). |
| **Constraints** | `n ≤ 10⁵` → need **efficient** (`O(n log n)`) solution. |

---

## 3️⃣ **Applicable Concepts Explained**

### ✅ **Greedy Strategy with Lookahead**
- Don’t decide what to dry **on the dry day**.
- Instead, **wait** until a lake is about to flood (i.e., it rains again on a full lake).
- Then, **look back** for the **earliest dry day** that occurred **after the lake was last filled**.

### ✅ **Efficient Dry Day Lookup: TreeSet (Balanced BST)**
- Store all dry day **indices** in a `TreeSet`.
- When lake `L` rained on day `j` and rains again on day `i`, we need a dry day in `(j, i)`.
- Use `TreeSet.ceiling(j + 1)` → returns **smallest dry day ≥ j+1**.
- If none exists → flood is unavoidable.

### ✅ **Hash Map for Tracking Full Lakes**
- `Map<Integer, Integer> full`: maps `lake → lastRainDay`.
- When it rains on a lake already in `full`, a flood is imminent unless we dried it in between.

---

## 4️⃣ **Logic Analysis & Application**

### 🧠 Core Idea:
> **"Dry the right lake at the right time — not too early, not too late."**

We simulate day-by-day:
- On **rainy days**: mark lake as full; if already full, **trigger flood check**.
- On **dry days**: just record the day index — **don’t decide what to dry yet**.
- Only when a **second rain hits a full lake**, we **retroactively assign** the best dry day to prevent flood.

This **postponed decision-making** is key to optimality.

---

## 5️⃣ **Example Recap**

### Example: `rains = [1,2,0,0,2,1]`

| Day | Event | Action | Full Lakes | Notes |
|-----|-------|--------|------------|------|
| 0 | Rain on lake 1 | `ans[0] = -1` | `{1}` | — |
| 1 | Rain on lake 2 | `ans[1] = -1` | `{1,2}` | — |
| 2 | Dry day | Record day 2 | `{1,2}` | Don’t decide yet |
| 3 | Dry day | Record day 3 | `{1,2}` | — |
| 4 | Rain on lake 2 | Flood imminent! | — | Last rain on lake 2 was day 1 → need dry day in (1,4) → pick day 2 |
|   | → Assign `ans[2] = 2` | Dry lake 2 | `{1}` | Remove day 2 from dry days |
| 5 | Rain on lake 1 | Flood imminent! | — | Last rain on lake 1 was day 0 → need dry day in (0,5) → pick day 3 |
|   | → Assign `ans[3] = 1` | Dry lake 1 | `{}` | — |

✅ Final: `[-1, -1, 2, 1, -1, -1]`

---

## 6️⃣ **Optimal Approach**

**Algorithm**: **Greedy + TreeSet for Dry Day Management**

- **Why optimal?**  
  Using the **earliest possible dry day** for an imminent flood leaves **more flexibility** for future floods.

- **Why not decide on dry day?**  
  If you dry lake 1 on day 2 in the example, then lake 2 floods on day 4. So **order matters**.

- **Data Structures**:
  - `TreeSet<Integer>`: stores dry day indices → supports `ceiling()` in `O(log n)`.
  - `HashMap<Integer, Integer>`: tracks last rain day per lake.

---

## 7️⃣ **Step-by-Step Algorithm**

1. Initialize:
   - `ans[]` of size `n`, fill with `1` (default for dry days).
   - `full = new HashMap<>()` → `lake → lastRainDay`
   - `dryDays = new TreeSet<>()`

2. For each day `i` from `0` to `n-1`:
   - **If `rains[i] > 0`** (rainy day):
     - Set `ans[i] = -1`
     - Let `lake = rains[i]`
     - If `lake` is in `full`:
       - Get `lastDay = full.get(lake)`
       - Find `dryDay = dryDays.ceiling(lastDay + 1)`
       - If `dryDay == null` → return `[]` (flood unavoidable)
       - Else: set `ans[dryDay] = lake`, and remove `dryDay` from `dryDays`
     - Update `full.put(lake, i)`
   - **Else** (`rains[i] == 0`):
     - Add `i` to `dryDays`
     - (`ans[i]` remains `1` as placeholder)

3. Return `ans`

---

## 8️⃣ **Complexity Analysis**

| Metric | Complexity | Justification |
|-------|------------|---------------|
| **Time** | `O(n log n)` | Each dry day inserted/removed once (`O(log n)`); each rain does one `ceiling()` query (`O(log n)`). |
| **Space** | `O(n)` | `ans`: `O(n)`, `full`: up to `O(n)` lakes, `dryDays`: up to `O(n)` dry days. |

✅ Efficient for `n = 10⁵`.

---

## 9️⃣ **Test Cases**

| Input | Output | Reason |
|------|--------|--------|
| `[1,2,3,4]` | `[-1,-1,-1,-1]` | No repeated rain → no flood. |
| `[1,2,0,0,2,1]` | `[-1,-1,2,1,-1,-1]` | Dry days used to prevent floods on lakes 2 and 1. |
| `[1,2,0,1,2]` | `[]` | Only one dry day, but two lakes need drying → impossible. |
| `[69,0,69]` | `[-1,69,-1]` | Dry day used to dry lake 69 between rains. |
| `[1,0,2,0,2,1]` | `[-1,1,-1,2,-1,-1]` | Dry day 1 dries lake 1 (optional), dry day 3 dries lake 2 before flood. |

---

## 🔟 **Final Solution Code (Java) with Comments**

```java
import java.util.*;

class Solution {
    public int[] avoidFlood(int[] rains) {
        int n = rains.length;
        // Initialize answer array; default to 1 for dry days (can be any lake)
        int[] ans = new int[n];
        Arrays.fill(ans, 1);
        
        // Tracks last rain day for each lake: lake -> lastRainDay
        Map<Integer, Integer> full = new HashMap<>();
        
        // Stores indices of dry days (rains[i] == 0) in sorted order
        TreeSet<Integer> dryDays = new TreeSet<>();
        
        for (int i = 0; i < n; i++) {
            if (rains[i] > 0) {
                int lake = rains[i];
                ans[i] = -1; // Must be -1 on rainy days
                
                // If this lake is already full, we need to have dried it since last rain
                if (full.containsKey(lake)) {
                    int lastRainDay = full.get(lake);
                    // Find the earliest dry day AFTER the last rain on this lake
                    Integer dryDay = dryDays.ceiling(lastRainDay + 1);
                    
                    // If no such dry day exists, flood is unavoidable
                    if (dryDay == null) {
                        return new int[0];
                    }
                    
                    // Use this dry day to dry the lake
                    ans[dryDay] = lake;
                    dryDays.remove(dryDay); // This dry day is now used
                }
                
                // Update the last rain day for this lake
                full.put(lake, i);
                
            } else {
                // It's a dry day: record the index for future use
                dryDays.add(i);
                // ans[i] remains 1 (or any value) if unused — problem allows drying empty lakes
            }
        }
        
        return ans;
    }
}
```

---

## ✅ **Key Takeaways**

- **Postpone decisions**: Don’t assign dry actions immediately; wait until a flood is imminent.
- **Use TreeSet for efficient range queries**: `ceiling(x)` finds the earliest dry day after day `x`.
- **Greedy is optimal**: Using the earliest valid dry day maximizes future flexibility.
- **Handle edge cases**: Unused dry days can output any lake (e.g., `1`).

This approach is **robust, efficient, and handles all constraints** gracefully.

Let me know if you'd like a Python version or a visualization! 🌊☀️