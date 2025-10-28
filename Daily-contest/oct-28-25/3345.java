
class Solution {
    public int countValidSelections(int[] nums) {
        int n = nums.length;
        int validCount = 0;
        
        // 📌 Find all zero positions
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                // 🔄 Try both directions: right (1) and left (-1)
                for (int direction : new int[]{1, -1}) {
                    if (simulate(nums, i, direction)) {
                        validCount++;
                    }
                }
            }
        }
        
        return validCount;
    }
    
    private boolean simulate(int[] original, int startPos, int direction) {
        int n = original.length;
        int[] arr = original.clone();
        
        int curr = startPos;
        int dir = direction;
        
        while (curr >= 0 && curr < n) {
            if (arr[curr] == 0) {
                curr += dir;
            } else {
                arr[curr]--;
                dir *= -1; // Reverse direction
                curr += dir; // Take step in new direction
            }
        }
        
        // ✅ Check if all elements are zero
        for (int value : arr) {
            if (value != 0) {
                return false;
            }
        }
        return true;
    }
}