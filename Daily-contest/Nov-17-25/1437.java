
class Solution {
    
    public boolean kLengthApart(int[] nums, int k) {
        int lastOnePos = -1;
        
        // 🔁 Scan the array from left to right
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                if (lastOnePos != -1) {
                    if (i - lastOnePos - 1 < k) {
                        return false;
                    }
                }
                lastOnePos = i;
            }
        }
        
        return true;
    }
}