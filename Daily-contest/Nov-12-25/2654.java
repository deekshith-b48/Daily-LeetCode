
class Solution {
    
    public int minOperations(int[] nums) {
        int n = nums.length;
        int ones = 0;
        
        // 📊 Count existing 1s
        for (int num : nums) {
            if (num == 1) {
                ones++;
            }
        }
        
        // 🎯 If there are already 1s, we just need to convert the rest
        if (ones > 0) {
            return n - ones;
        }
        
        // 🔍 Check if it's possible to create a 1 (GCD of entire array must be 1)
        int overallGcd = nums[0];
        for (int i = 1; i < n; i++) {
            overallGcd = gcd(overallGcd, nums[i]);
        }
        
        if (overallGcd != 1) {
            return -1;
        }
        
        // 📏 Find minimum length subarray with GCD = 1
        int minLen = n + 1;
        
        for (int i = 0; i < n; i++) {
            int currentGcd = nums[i];
            for (int j = i; j < n; j++) {
                if (j > i) {
                    currentGcd = gcd(currentGcd, nums[j]);
                }
                if (currentGcd == 1) {
                    minLen = Math.min(minLen, j - i + 1);
                    break; // No need to extend further
                }
            }
        }
        
        return minLen + n - 2;
    }
    
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}