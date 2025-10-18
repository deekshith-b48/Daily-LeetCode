# 🧙‍♂️✨ Comprehensive Solution Report: Find Resultant Array After Removing Anagrams

---

## 📋 Problem Summary

**Inputs**:  
- `words`: Array of strings consisting of lowercase English letters  
- Each string length: 1 ≤ length ≤ 10  
- Array length: 1 ≤ words.length ≤ 100

**Operation**:  
- Remove `words[i]` if `0 < i < words.length` and `words[i-1]` and `words[i]` are anagrams
- Repeat this operation until no more valid removals are possible

**Anagram Definition**:  
Two strings are anagrams if they contain the same characters with the same frequencies (just rearranged)

**Goal**:  
Return the final array after performing all possible operations

**Output**:  
- List of strings representing the resultant array

---

## 🔍 Key Observations and Constraints

### 🎯 Critical Insights:
1. **Greedy removal works**: Since we always compare with the previous element in the current array, we can simulate the process in a single pass
2. **Order independence**: The problem states that any removal order leads to the same result, so we can process left to right
3. **Anagram detection**: Two strings are anagrams if their sorted versions are equal, or if their character frequency maps are identical
4. **Stack-like behavior**: We keep the first word, then only add subsequent words if they're not anagrams of the last kept word

### ⚠️ Constraints Analysis:
- **Small input size**: Maximum 100 words, each up to 10 characters
- **Efficiency not critical**: Even O(n × m log m) solution is acceptable (100 × 10 log 10 ≈ 1000 operations)
- **Edge cases**:
  - Single word array (no removals possible)
  - All words are anagrams of each other
  - No adjacent anagrams

---

## 📚 Relevant Concepts and Theory

### 🔍 Anagram Detection Methods
**Method 1: Sorting**
- Sort both strings and compare
- Time: O(m log m) per comparison, where m is string length
- Space: O(m) for sorted copies

**Method 2: Character Frequency Counting**
- Count frequency of each character in both strings
- Compare frequency arrays
- Time: O(m) per comparison
- Space: O(1) since only 26 lowercase letters

Given the small string length (≤ 10), **sorting is simpler and efficient enough**.

### 📊 Stack Simulation
The removal process can be simulated using a stack:
- Always keep the first word
- For each subsequent word, compare with the top of the stack
- If not anagrams, push to stack; otherwise, skip (simulate removal)

This works because:
- We only care about adjacency in the current state
- Removing a word doesn't affect comparisons with words before the previous one

---

## 🧠 Logical Analysis

### ❌ Why Multiple Passes Are Unnecessary:
- If we have words `[A, B, C]` where A and B are anagrams, and B and C are anagrams
- Removing B first gives `[A, C]`, and if A and C are anagrams, we remove C
- But if we process left to right with a stack, we keep A, skip B (since A and B are anagrams), then compare A and C directly
- This gives the same result in one pass

### ✅ Why Stack Approach Works:
- **Invariant**: The stack always contains the correct result for the processed prefix
- **Greedy choice**: If current word is anagram of last kept word, removing it is always optimal
- **No lookback needed**: Once we decide to keep a word, we never need to reconsider previous decisions

### 🎯 Key Insight: Single Pass Simulation
We can simulate the entire removal process in one left-to-right pass by maintaining a result list and only adding words that are not anagrams of the last word in the result.

---

## 🎯 Effective Strategy

### 📝 Plan:
1. **Initialize result list** with the first word
2. **Iterate through remaining words** starting from index 1
3. **For each word**:
   - Compare with the last word in the result list
   - If they are **not anagrams**, add the current word to result
   - If they **are anagrams**, skip it (simulate removal)
4. **Return** the result list

### 🛠️ Design Decisions:
- **Use sorting for anagram detection**: Simple and efficient for small strings
- **ArrayList for result**: Efficient append operations
- **Single pass**: O(n) iterations with O(m log m) per comparison

---

## 🧪 Illustrative Example Walkthrough

**Example 1**: `words = ["abba","baba","bbaa","cd","cd"]`

### Step-by-step simulation:

**Initial result**: `["abba"]`

**Process "baba"**:
- Last in result: "abba"
- Sorted("abba") = "aabb", Sorted("baba") = "aabb" → **anagrams**
- **Skip "baba"**
- Result: `["abba"]`

**Process "bbaa"**:
- Last in result: "abba"  
- Sorted("abba") = "aabb", Sorted("bbaa") = "aabb" → **anagrams**
- **Skip "bbaa"**
- Result: `["abba"]`

**Process "cd"**:
- Last in result: "abba"
- Sorted("abba") = "aabb", Sorted("cd") = "cd" → **not anagrams**
- **Add "cd"**
- Result: `["abba", "cd"]`

**Process "cd"**:
- Last in result: "cd"
- Sorted("cd") = "cd", Sorted("cd") = "cd" → **anagrams**
- **Skip "cd"**
- Result: `["abba", "cd"]`

**Final Output**: `["abba", "cd"]` ✅

**Example 2**: `words = ["a","b","c","d","e"]`
- No adjacent words are anagrams
- All words are added to result
- Output: `["a","b","c","d","e"]` ✅

---

## ⚡ Optimal Approach

**Single Pass with Stack Simulation** is optimal because:
- **Time Complexity**: O(n × m log m) - optimal for this problem
- **Space Complexity**: O(n × m) - for storing result
- **Simple implementation**: Easy to understand and debug
- **Handles all cases correctly**: Including complex removal chains

---

## 📋 Step-by-Step Algorithm

1. **Handle edge case**: If words is empty, return empty list
2. **Initialize result list** and add `words[0]`
3. **For i = 1 to words.length - 1**:
   - Get `current = words[i]` and `previous = result.get(result.size() - 1)`
   - If `!areAnagrams(previous, current)`:
     - Add `current` to result
4. **Return** result list

**Helper function `areAnagrams(s1, s2)`**:
- If lengths differ, return false
- Return `sort(s1) == sort(s2)`

---

## 📊 Complexity Analysis

### ⏱️ Time Complexity: **O(n × m log m)**
- **n**: number of words (≤ 100)
- **m**: average word length (≤ 10)
- **Sorting each word**: O(m log m) = O(10 log 10) ≈ O(10)
- **Total operations**: 100 × 10 = **1,000 operations** ✅

### 💾 Space Complexity: **O(n × m)**
- **Result list**: Stores at most n words of length m
- **Temporary sorted strings**: O(m) per comparison
- **Total**: 100 × 10 = **1,000 characters** ✅

---

## 🧪 Test Cases

### ✅ Basic Cases
1. **Example 1**: `["abba","baba","bbaa","cd","cd"]` → `["abba","cd"]`
2. **Example 2**: `["a","b","c","d","e"]` → `["a","b","c","d","e"]`

### ⚠️ Edge Cases
3. **Single word**: `["hello"]` → `["hello"]`
4. **All anagrams**: `["abc","bca","cab"]` → `["abc"]`
5. **Empty strings**: `["",""]` → `[""]`
6. **Alternating anagrams**: `["ab","ba","cd","dc","ef"]` → `["ab","cd","ef"]`

### 🚀 Boundary Cases
7. **Maximum length**: 100 words of length 10, all anagrams → result has 1 word
8. **Maximum diversity**: 100 words, no anagrams → result has 100 words

---

## 💻 Final Implementation (Java)

```java
import java.util.*;

/**
 * 🧙‍♀️ Solution for "Find Resultant Array After Removing Anagrams"
 * 
 * Strategy: Single pass with stack simulation
 * Time Complexity: O(n * m log m)
 * Space Complexity: O(n * m)
 */
class Solution {
    
    /**
     * Removes anagrams from the word array by simulating the removal process.
     * 
     * 🧠 Key insight: Use a stack-like approach where we only keep words that
     * are not anagrams of the previously kept word. This simulates the removal
     * process in a single left-to-right pass.
     * 
     * 🔍 Anagram detection: Two strings are anagrams if their sorted versions
     * are identical. For small strings (length ≤ 10), sorting is efficient and simple.
     * 
     * @param words array of words to process
     * @return list of words after removing all adjacent anagrams
     */
    public List<String> removeAnagrams(String[] words) {
        // 📋 Handle edge case (though constraints guarantee at least 1 word)
        if (words.length == 0) {
            return new ArrayList<>();
        }
        
        // 🧪 Initialize result with first word (always kept)
        List<String> result = new ArrayList<>();
        result.add(words[0]);
        
        // 🔁 Process remaining words
        for (int i = 1; i < words.length; i++) {
            String currentWord = words[i];
            String lastKeptWord = result.get(result.size() - 1);
            
            // ✅ Only add if not anagrams of the last kept word
            if (!areAnagrams(lastKeptWord, currentWord)) {
                result.add(currentWord);
            }
            // ❌ If anagrams, skip (simulate removal)
        }
        
        return result;
    }
    
    /**
     * Checks if two strings are anagrams of each other.
     * 
     * Two strings are anagrams if they contain the same characters
     * with the same frequencies. We check this by sorting both strings
     * and comparing the results.
     * 
     * @param s1 first string
     * @param s2 second string  
     * @return true if s1 and s2 are anagrams, false otherwise
     */
    private boolean areAnagrams(String s1, String s2) {
        // 🚫 Quick check: different lengths cannot be anagrams
        if (s1.length() != s2.length()) {
            return false;
        }
        
        // 📈 Sort both strings and compare
        char[] chars1 = s1.toCharArray();
        char[] chars2 = s2.toCharArray();
        Arrays.sort(chars1);
        Arrays.sort(chars2);
        
        return Arrays.equals(chars1, chars2);
    }
}
```

---

## 🗺️ Logic Flowchart

```
┌─────────────────────────────────┐
│           START                 │
│ removeAnagrams(words)           │
└─────────────────────────────────┘
                │
                ▼
┌─────────────────────────────────┐
│      If words.length == 0:      │
│        return empty list        │
└─────────────────────────────────┘
                │
                ▼
┌─────────────────────────────────┐
│      result = [words[0]]        │
│      (initialize with first)    │
└─────────────────────────────────┘
                │
                ▼
┌─────────────────────────────────┐
│      For i = 1 to n-1:          │
│        current = words[i]       │
│        last = result[last]      │
└─────────────────────────────────┘
                │
                ▼
┌─────────────────────────────────┐
│      areAnagrams(last, current)?│
└─────────────────────────────────┘
                │
       ┌────────┴─────────┐
       │                  │
       ▼                  ▼
┌─────────────┐    ┌──────────────────┐
│   Skip      │    │   Add current    │
│   (remove)  │    │   to result      │
└─────────────┘    └──────────────────┘
       │                  │
       └────────┬─────────┘
                ▼
        ┌─────────────────┐
        │   Continue loop │
        └─────────────────┘

                │
                ▼
┌─────────────────────────────────┐
│      Return result list         │
└─────────────────────────────────┘
                │
                ▼
┌─────────────────────────────────┐
│             END                 │
└─────────────────────────────────┘
```