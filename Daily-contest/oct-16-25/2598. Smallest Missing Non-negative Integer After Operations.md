# 🧙‍♂️✨ Comprehensive Solution Report: Smallest Missing Non-negative Integer After Operations

---

## 📋 Problem Summary

**Inputs**:  
- `nums`: Array of `n` integers (-10⁹ ≤ nums[i] ≤ 10⁹)  
- `value`: Positive integer (1 ≤ value ≤ 10⁵)

**Operation**:  
- Add or subtract `value` from any element any number of times
- This means we can transform any `nums[i]` to any number in the congruence class `nums[i] mod value`

**MEX Definition**:  
- Minimum Excluded value = smallest non-negative integer not present in the array

**Goal**:  
Return the **maximum possible MEX** achievable after applying operations optimally

**Output**:  
- Single integer representing maximum MEX

---

## 🔍 Key Observations and Constraints

### 🎯 Critical Insights:
1. **Modular arithmetic**: Each number `nums[i]` can be transformed to any number congruent to `nums[i] mod value`
2. **Non-negative requirement**: We only care about non-negative integers for MEX
3. **Greedy construction**: To maximize MEX, we want to construct the sequence `0, 1, 2, ..., mex-1`
4. **Residue classes**: Numbers can be grouped by their remainder when divided by `value`

### ⚠️ Constraints Analysis:
- **Large input size**: `nums.length ≤ 10⁵`, `value ≤ 10⁵`
- **Large number range**: `nums[i]` can be as large as 10⁹ in absolute value
- **Edge cases**:
  - `value = 1` (all numbers can become any integer)
  - All numbers have the same residue modulo `value`
  - Array contains all residues but insufficient counts

---

## 📚 Relevant Concepts and Theory

### 🔢 Modular Arithmetic and Congruence Classes
Two numbers `a` and `b` are in the same congruence class modulo `value` if `a ≡ b (mod value)`. This means we can transform `a` to `b` by adding/subtracting multiples of `value`.

### 📊 Residue Counting
For each residue `r ∈ [0, value-1]`, count how many numbers in `nums` have `nums[i] mod value = r`.

### 🎯 Greedy MEX Construction
To achieve MEX = `k`, we need to be able to construct all integers from `0` to `k-1`. 

For any integer `x`, we can construct it if we have at least one number with residue `x mod value`.

More precisely, to construct integer `x`, we need at least `⌊x/value⌋ + 1` numbers with residue `x mod value`.

### 🧠 Key Insight: Count-Based Approach
For each residue `r`, if we have `count[r]` numbers with that residue, we can construct the integers:
- `r, r + value, r + 2*value, ..., r + (count[r]-1)*value`

To find the maximum MEX, we need to find the smallest non-negative integer that **cannot** be constructed.

---

## 🧠 Logical Analysis

### ❌ Why Brute Force Fails:
- **Infinite possibilities**: Each number can be transformed to infinitely many values
- **MEX could be large**: Up to `n` (array length) in the best case

### ✅ Why Residue Counting Works:
- **Finite residue classes**: Only `value` different residue classes to consider
- **Systematic construction**: We can determine exactly which integers can be constructed
- **Efficient counting**: O(n) time to count residues, O(value) time to find MEX

### 🎯 Key Insight: Per-Residue Capacity
For residue `r`, having `count[r]` numbers means we can construct exactly `count[r]` non-negative integers with residue `r`:
- The smallest is `r` (if `r ≥ 0`)
- Then `r + value`, `r + 2*value`, etc.

To construct integer `x`, where `x = q * value + r` (with `0 ≤ r < value`), we need `count[r] > q`.

Therefore, the maximum MEX is the smallest `x` such that `count[x % value] ≤ x / value`.

---

## 🎯 Effective Strategy

### 📝 Plan:
1. **Count residues**: For each number in `nums`, compute `(nums[i] % value + value) % value` to handle negative numbers
2. **Initialize count array**: `count[0..value-1]` with residue frequencies
3. **Find maximum MEX**: Starting from `x = 0`, find the first `x` where `count[x % value] ≤ x / value`
4. **Return** this `x` as the maximum MEX

### 🛠️ Design Decisions:
- **Proper modulo for negatives**: Use `(nums[i] % value + value) % value` to get non-negative residues
- **Direct MEX calculation**: Instead of simulating construction, use the mathematical condition
- **Early termination**: Stop as soon as we find the first unconstructible integer

---

## 🧪 Illustrative Example Walkthrough

**Example 1**: `nums = [1,-10,7,13,6,8], value = 5`

### Step 1: Compute residues
- `1 % 5 = 1`
- `-10 % 5 = 0` (since -10 = (-2)*5 + 0)
- `7 % 5 = 2`
- `13 % 5 = 3`
- `6 % 5 = 1`
- `8 % 5 = 3`

### Step 2: Count residues
- `count[0] = 1` (from -10)
- `count[1] = 2` (from 1, 6)
- `count[2] = 1` (from 7)
- `count[3] = 2` (from 13, 8)
- `count[4] = 0` (none)

### Step 3: Check constructibility for x = 0, 1, 2, 3, 4, ...

- `x = 0`: `r = 0 % 5 = 0`, `q = 0 / 5 = 0`, need `count[0] > 0` → `1 > 0` ✓
- `x = 1`: `r = 1`, `q = 0`, need `count[1] > 0` → `2 > 0` ✓
- `x = 2`: `r = 2`, `q = 0`, need `count[2] > 0` → `1 > 0` ✓
- `x = 3`: `r = 3`, `q = 0`, need `count[3] > 0` → `2 > 0` ✓
- `x = 4`: `r = 4`, `q = 0`, need `count[4] > 0` → `0 > 0` ✗

**Maximum MEX = 4** ✅

**Example 2**: `nums = [1,-10,7,13,6,8], value = 7`

### Residues:
- `1 % 7 = 1`
- `-10 % 7 = 4` (since -10 = (-2)*7 + 4)
- `7 % 7 = 0`
- `13 % 7 = 6`
- `6 % 7 = 6`
- `8 % 7 = 1`

### Count array:
- `count[0] = 1`, `count[1] = 2`, `count[4] = 1`, `count[6] = 2`
- `count[2] = count[3] = count[5] = 0`

### Check:
- `x = 0`: `r = 0`, `count[0] = 1 > 0` ✓
- `x = 1`: `r = 1`, `count[1] = 2 > 0` ✓
- `x = 2`: `r = 2`, `count[2] = 0 > 0` ✗

**Maximum MEX = 2** ✅

---

## ⚡ Optimal Approach

**Residue Counting with Direct MEX Calculation** is optimal because:
- **Time Complexity**: O(n + value) - efficient for given constraints
- **Space Complexity**: O(value) - acceptable for value ≤ 10⁵
- **Handles all cases correctly**: Including negative numbers and edge cases
- **Mathematically sound**: Based on modular arithmetic properties

---

## 📋 Step-by-Step Algorithm

1. **Initialize count array** of size `value` with zeros
2. **For each number in nums**:
   - Compute non-negative residue: `r = (nums[i] % value + value) % value`
   - Increment `count[r]`
3. **For x = 0 to n** (maximum possible MEX is n+1):
   - Compute `r = x % value`
   - Compute `q = x / value`
   - If `count[r] <= q`, return `x`
4. **Return n** (if all numbers 0 to n-1 can be constructed)

---

## 📊 Complexity Analysis

### ⏱️ Time Complexity: **O(n + value)**
- **Residue counting**: O(n)
- **MEX calculation**: O(min(n, value)) in practice, worst case O(n)
- **Total**: O(n + value)
- **For maximum constraints**: 10⁵ + 10⁵ = **200,000 operations** ✅

### 💾 Space Complexity: **O(value)**
- **Count array**: `value` integers
- **Total**: **10⁵ integers** ≈ 400 KB ✅

---

## 🧪 Test Cases

### ✅ Basic Cases
1. **Example 1**: `[1,-10,7,13,6,8], value=5` → `4`
2. **Example 2**: `[1,-10,7,13,6,8], value=7` → `2`

### ⚠️ Edge Cases
3. **value = 1**: `[5, -3, 10]` → `3` (can construct 0,1,2)
4. **All same residue**: `[2,7,12], value=5` → `1` (can only construct 2,7,12 → MEX=0? Wait...)
   - Actually: can construct 2, 2+5=7, 2+10=12, but also 2-5=-3, 2-10=-8, etc.
   - But for non-negative: can construct 2, 7, 12, ... but **cannot construct 0 or 1**
   - So MEX = 0? No, wait: 2 mod 5 = 2, so we can construct 2, 7, 12, ... but also 2-5=-3 (negative, ignore)
   - To construct 0: need residue 0, but we have residue 2 → cannot construct 0
   - **MEX = 0** ✅

5. **Empty residue classes**: `[0,1,2], value=5` → `3` (can construct 0,1,2 but not 3)

### 🚀 Boundary Cases
6. **Maximum constraints**: 10⁵ elements with value=10⁵
7. **Large negative numbers**: `[-10⁹, -10⁹+1], value=10⁵`

---

## 💻 Final Implementation (Java)

```java
/**
 * 🧙‍♀️ Solution for "Smallest Missing Non-negative Integer After Operations"
 * 
 * Strategy: Residue counting with direct MEX calculation
 * Time Complexity: O(n + value)
 * Space Complexity: O(value)
 */
class Solution {
    
    /**
     * Finds the maximum MEX achievable by applying add/subtract operations.
     * 
     * 🧠 Key insight: Each number can be transformed to any value in its 
     * congruence class modulo 'value'. To maximize MEX, we need to construct
     * consecutive non-negative integers starting from 0.
     * 
     * For integer x = q * value + r, we need at least (q + 1) numbers with 
     * residue r to construct x. The maximum MEX is the first x where this 
     * condition fails.
     * 
     * ⚠️ Important: Handle negative numbers properly in modulo operation
     * using (num % value + value) % value to get non-negative residues.
     * 
     * @param nums array of integers that can be modified
     * @param value the value that can be added/subtracted
     * @return maximum possible MEX
     */
    public int findSmallestInteger(int[] nums, int value) {
        // 📊 Count frequency of each residue class modulo 'value'
        int[] count = new int[value];
        
        // 🔢 Process each number and compute its non-negative residue
        for (int num : nums) {
            // Handle negative numbers: (num % value + value) % value
            int residue = ((num % value) + value) % value;
            count[residue]++;
        }
        
        // 🎯 Find the maximum MEX by checking constructibility of each integer
        // The maximum possible MEX is at most nums.length + 1
        for (int x = 0; x <= nums.length; x++) {
            int residue = x % value;
            int quotient = x / value;
            
            // To construct integer x, we need at least (quotient + 1) numbers 
            // with residue 'residue'. If we have only 'quotient' or fewer,
            // then x cannot be constructed.
            if (count[residue] <= quotient) {
                return x;
            }
        }
        
        // This line should never be reached since MEX <= nums.length + 1
        return nums.length;
    }
}
```

---

## 🗺️ Logic Flowchart

```
┌─────────────────────────────────┐
│           START                 │
│ findSmallestInteger(nums, value)│
└─────────────────────────────────┘
                │
                ▼
┌─────────────────────────────────┐
│      Initialize count[0..value-1]│
│      = 0                        │
└─────────────────────────────────┘
                │
                ▼
┌─────────────────────────────────┐
│      For each num in nums:      │
│        residue = (num % value + │
│                  value) % value │
│        count[residue]++         │
└─────────────────────────────────┘
                │
                ▼
┌─────────────────────────────────┐
│      For x = 0 to n:            │
│        residue = x % value      │
│        quotient = x / value     │
└─────────────────────────────────┘
                │
                ▼
┌─────────────────────────────────┐
│ count[residue] <= quotient ?    │
└─────────────────────────────────┘
                │
          ┌─────┴─────┐
          │    Yes    │
          └─────┬─────┘
                ▼
┌─────────────────────────────────┐
│           Return x              │
└─────────────────────────────────┘
                │
          ┌─────┴─────┐
          │    No     │
          └─────┬─────┘
                ▼
        ┌─────────────────┐
        │   Next x        │
        └─────────────────┘

                │
                ▼
┌─────────────────────────────────┐
│      Return nums.length         │
└─────────────────────────────────┘
                │
                ▼
┌─────────────────────────────────┐
│             END                 │
└─────────────────────────────────┘
```