# 🧙‍♂️✨ Comprehensive Solution Report: Successful Pairs of Spells and Potions

---

## 📋 Problem Summary

**Inputs**:  
- `spells`: Array of `n` positive integers (spell strengths)  
- `potions`: Array of `m` positive integers (potion strengths)  
- `success`: A positive integer threshold  

**Condition**:  
A spell-potion pair `(i, j)` is **successful** if:  
`spells[i] * potions[j] >= success`

**Goal**:  
Return an array `pairs` of length `n` where `pairs[i]` = number of potions that form successful pairs with `spells[i]`.

**Output**:  
- Integer array of length `n`

---

## 🔍 Key Observations and Constraints

### 🎯 Critical Insights:
1. **Multiplication condition**: `spells[i] * potions[j] >= success`
2. **Rearranged inequality**: For fixed `spells[i]`, we need `potions[j] >= ceil(success / spells[i])`
3. **Sorting opportunity**: If we sort `potions`, we can use **binary search** to find the first valid potion efficiently
4. **All values positive**: No division by zero or negative number complications

### ⚠️ Constraints:
- **Array sizes**: `1 <= n, m <= 10⁵` → O(n×m) brute force would be **too slow** (10¹⁰ operations!)
- **Value ranges**: 
  - `1 <= spells[i], potions[i] <= 10⁵`
  - `1 <= success <= 10¹⁰` → **Potential overflow** when multiplying!
- **Edge cases**:
  - Very large `success` → few or zero successful pairs
  - Small `spells[i]` with large `success` → need careful division
  - All pairs successful or none successful

---

## 📚 Relevant Concepts and Theory

### 🔍 Binary Search
Binary search finds a target value in a **sorted array** in O(log n) time by repeatedly dividing the search interval in half.

**Why applicable here?**  
Once `potions` is sorted, for each spell strength `s`, we need to find the **leftmost potion** `p` such that `s * p >= success`. All potions to the right will also satisfy the condition.

### 🧮 Mathematical Rearrangement
Instead of checking `s * p >= success` for every potion (O(m)), we compute the **minimum required potion strength**:
- `minPotion = ceil(success / s)`

Then find the first index in sorted `potions` where `potions[i] >= minPotion`.

### ⚠️ Handling Large Numbers
Since `success` can be up to `10¹⁰` and `spells[i]` up to `10⁵`, the division `success / spells[i]` can be up to `10⁵`, which fits in integer, but we must be careful with **floating-point precision**.

**Better approach**: Use integer arithmetic with ceiling division:
- `minPotion = (success + spells[i] - 1) / spells[i]` (for positive integers)

However, even better: **avoid division entirely** by using binary search with multiplication, but watch for **overflow**!

**Safest approach**: Use `long` for multiplication to prevent overflow.

---

## 🧠 Logical Analysis

### ❌ Why Brute Force Fails:
- Time complexity: O(n × m) = 10⁵ × 10⁵ = 10¹⁰ operations
- Too slow for Java (typically handles ~10⁸ operations per second)

### ✅ Why Sorting + Binary Search Works:
- **Preprocessing**: Sort `potions` once → O(m log m)
- **Per spell**: Binary search → O(log m)
- **Total**: O(m log m + n log m) = O((n + m) log m) ≈ 10⁵ × log(10⁵) ≈ 1.6 × 10⁶ operations ✅

### 🎯 Key Decision: How to Handle the Condition in Binary Search
**Option 1**: Compute `minPotion = ceil(success / spell)` then find first `potion >= minPotion`
- **Risk**: Floating-point precision issues with large numbers

**Option 2**: In binary search comparison, compute `spell * potion >= success` using `long`
- **Safe**: Uses integer arithmetic, no precision loss
- **Overflow-safe**: Cast to `long` before multiplication

**Winner**: **Option 2** - more reliable and straightforward!

---

## 🎯 Effective Strategy

### 📝 Plan:
1. **Sort** the `potions` array in ascending order
2. **For each spell** in `spells`:
   - Use **binary search** on sorted `potions` to find the **leftmost index** where `spell * potion >= success`
   - The number of successful potions = `m - leftmostIndex`
3. **Handle edge cases** where no potion is strong enough (return 0)

### 🛠️ Design Decisions:
- **Sort potions, not spells**: We query potions multiple times per spell
- **Use long for multiplication**: Prevents integer overflow
- **Standard binary search template**: Find first valid position

---

## 🧪 Illustrative Example Walkthrough

**Input**: `spells = [5,1,3]`, `potions = [1,2,3,4,5]`, `success = 7`

### Step 1: Sort potions
`potions = [1,2,3,4,5]` (already sorted)

### Step 2: Process each spell

**Spell = 5**:
- Binary search for first `potion` where `5 * potion >= 7`
- Check midpoints:
  - `potions[2] = 3` → `5*3 = 15 >= 7` ✓ → search left
  - `potions[1] = 2` → `5*2 = 10 >= 7` ✓ → search left  
  - `potions[0] = 1` → `5*1 = 5 < 7` ✗ → search right
- First valid index = 1
- Count = 5 - 1 = **4** ✅

**Spell = 1**:
- Need `1 * potion >= 7` → `potion >= 7`
- All potions < 7 → first valid index = 5 (out of bounds)
- Count = 5 - 5 = **0** ✅

**Spell = 3**:
- Need `3 * potion >= 7` → `potion >= 7/3 ≈ 2.33`
- First valid potion = 3 (index 2)
- Count = 5 - 2 = **3** ✅

**Output**: `[4, 0, 3]` 🎉

---

## ⚡ Optimal Approach

**Sorting + Binary Search** is optimal because:
- **Time Complexity**: O(m log m + n log m) - much better than O(n×m)
- **Space Complexity**: O(1) extra space (not counting output array)
- **Handles all edge cases** naturally
- **No precision issues** with integer arithmetic

**Alternative considered**: Two pointers after sorting both arrays, but spells need individual processing, so binary search per spell is cleaner.

---

## 📋 Step-by-Step Algorithm

1. **Sort** the `potions` array in non-decreasing order
2. **Create result array** `pairs` of length `n`
3. **For each index `i` from 0 to n-1**:
   - Set `spell = spells[i]`
   - Initialize binary search bounds: `left = 0`, `right = m`
   - **While `left < right`**:
     - Calculate `mid = left + (right - left) / 2`
     - If `(long)spell * potions[mid] >= success`:
       - Move `right = mid` (this could be our answer, but check left)
     - Else:
       - Move `left = mid + 1` (current potion too weak)
   - Set `pairs[i] = m - left` (all potions from index `left` to end are valid)
4. **Return** `pairs`

---

## 📊 Complexity Analysis

### ⏱️ Time Complexity: **O(m log m + n log m)**
- **Sorting potions**: O(m log m)
- **Binary search for each spell**: O(n log m)
- **Total**: O((n + m) log m)

### 💾 Space Complexity: **O(1)**
- **Extra space**: Only a few variables for binary search
- **Note**: Sorting is done in-place, output array doesn't count as extra space

**Why efficient?**  
For maximum constraints (n = m = 10⁵):
- Operations ≈ 10⁵ × log₂(10⁵) ≈ 10⁵ × 17 ≈ **1.7 million operations** ✅

---

## 🧪 Test Cases

### ✅ Basic Cases
1. **Example 1**: `spells = [5,1,3]`, `potions = [1,2,3,4,5]`, `success = 7` → `[4,0,3]`
2. **Example 2**: `spells = [3,1,2]`, `potions = [8,5,8]`, `success = 16` → `[2,0,2]`

### ⚠️ Edge Cases
3. **All successful**: `spells = [10]`, `potions = [1,2,3]`, `success = 5` → `[3]`
4. **None successful**: `spells = [1]`, `potions = [1,2,3]`, `success = 10` → `[0]`
5. **Single elements**: `spells = [2]`, `potions = [3]`, `success = 6` → `[1]`
6. **Exact match**: `spells = [2]`, `potions = [1,2,3]`, `success = 4` → `[2]` (2×2=4, 2×3=6)

### 🚀 Boundary Cases
7. **Large success**: `spells = [1]`, `potions = [100000]`, `success = 10000000000L` → `[0]`
8. **Maximum values**: `spells = [100000]`, `potions = [100000,100000]`, `success = 1` → `[2]`
9. **Minimum values**: `spells = [1]`, `potions = [1]`, `success = 1` → `[1]`

---

## 💻 Final Implementation (Java)

```java
import java.util.Arrays;

/**
 * 🧙‍♀️ Solution for "Successful Pairs of Spells and Potions"
 * 
 * Strategy: Sort potions + Binary Search for each spell
 * Time Complexity: O(m log m + n log m)
 * Space Complexity: O(1) extra space
 */
public class Solution {
    
    /**
     * Returns the number of successful spell-potion pairs for each spell.
     * 
     * ✨ Key insight: For each spell, find the minimum potion strength needed
     * using binary search on sorted potions array.
     * 
     * ⚠️ Important: Use 'long' for multiplication to prevent integer overflow!
     * 
     * @param spells array of spell strengths
     * @param potions array of potion strengths  
     * @param success success threshold
     * @return array where result[i] = count of successful potions for spells[i]
     */
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        // 📈 Step 1: Sort potions to enable binary search
        Arrays.sort(potions);
        int n = spells.length;
        int m = potions.length;
        int[] pairs = new int[n];
        
        // 🔍 Step 2: For each spell, find successful potions using binary search
        for (int i = 0; i < n; i++) {
            int spell = spells[i];
            
            // 🎯 Binary search for the leftmost potion that forms a successful pair
            int left = 0;
            int right = m; // right is exclusive bound
            
            while (left < right) {
                int mid = left + (right - left) / 2;
                
                // 💥 Use long to prevent integer overflow during multiplication!
                if ((long) spell * potions[mid] >= success) {
                    // ✅ This potion works, but maybe a smaller one also works
                    right = mid;
                } else {
                    // ❌ This potion is too weak, need stronger ones
                    left = mid + 1;
                }
            }
            
            // 📊 All potions from index 'left' to end are successful
            pairs[i] = m - left;
        }
        
        return pairs;
    }
}
```

---

## 🗺️ Logic Flowchart

```
┌─────────────────────────────────┐
│           START                 │
│ successfulPairs(spells,         │
│                potions, success)│
└─────────────────────────────────┘
                │
                ▼
┌─────────────────────────────────┐
│      Sort potions array         │
│      (ascending order)          │
└─────────────────────────────────┘
                │
                ▼
┌─────────────────────────────────┐
│      Initialize pairs[]         │
│      array of length n          │
└─────────────────────────────────┘
                │
                ▼
┌─────────────────────────────────┐
│      For each spell i:          │
│      (i = 0 to n-1)             │
└─────────────────────────────────┘
                │
                ▼
┌─────────────────────────────────┐
│      Set spell = spells[i]      │
│      Set left = 0, right = m    │
└─────────────────────────────────┘
                │
                ▼
        ┌─────────────────┐
        │  left < right?  │◄───────────────────┐
        └─────────────────┘                    │
                │                              │
          ┌─────┴─────┐                        │
          │    No     │                        │
          └─────┬─────┘                        │
                ▼                              │
┌─────────────────────────────────┐            │
│   pairs[i] = m - left           │            │
└─────────────────────────────────┘            │
                │                              │
                ▼                              │
        ┌───────────────┐                      │
        │    i = i + 1  │                      │
        └───────────────┘                      │
                │                              │
                │──────────────────────────────┘
                │
          ┌─────┴─────┐
          │    Yes    │
          └─────┬─────┘
                ▼
┌─────────────────────────────────┐
│   mid = left + (right-left)/2   │
└─────────────────────────────────┘
                │
                ▼
┌─────────────────────────────────┐
│ (long)spell * potions[mid]      │
│        >= success ?             │
└─────────────────────────────────┘
                │
     ┌──────────┴──────────┐
     │                     │
     ▼                     ▼
┌─────────────┐    ┌──────────────────┐
│   YES       │    │      NO          │
│ right = mid │    │ left = mid + 1   │
└─────────────┘    └──────────────────┘
     │                     │
     └──────────┬──────────┘
                │
                ▼
        ┌─────────────────┐
        │  Continue loop  │
        └─────────────────┘

                │
                ▼
┌─────────────────────────────────┐
│      All spells processed?      │
└─────────────────────────────────┘
                │
          ┌─────┴─────┐
          │    Yes    │
          └─────┬─────┘
                ▼
┌─────────────────────────────────┐
│        Return pairs[]           │
└─────────────────────────────────┘
                │
                ▼
┌─────────────────────────────────┐
│             END                 │
└─────────────────────────────────┘
```

### 💡 Implementation Highlights:

1. **🔥 Overflow Protection**: `(long) spell * potions[mid]` prevents integer overflow when multiplying large numbers

2. **🎯 Binary Search Template**: Uses the **lower_bound** pattern to find the first valid index

3. **📈 Sorting Strategy**: Only sort `potions` once since we query it multiple times

4. **📊 Counting Logic**: `m - left` gives the count of valid potions (from first valid index to end)

5. **⚡ Efficiency**: Optimal time complexity handles maximum constraints gracefully

This solution efficiently handles all edge cases while maintaining optimal performance! 🚀✨