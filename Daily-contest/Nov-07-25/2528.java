
class Solution {
    
    public long maxPower(int[] stations, int r, int k) {
        int n = stations.length;
         long[] power = new long[n];
        
        long sum = 0;
        for (int i = 0; i <= Math.min(r, n - 1); i++) {
            sum += stations[i];
        }
        power[0] = sum;
        
       for (int i = 1; i < n; i++) {
            // Remove leftmost element that's no longer in range
            if (i - r - 1 >= 0) {
                sum -= stations[i - r - 1];
            }
            // Add rightmost element that's now in range
            if (i + r < n) {
                sum += stations[i + r];
            }
            power[i] = sum;
        }
        
        // 🎯 Binary search on answer
        long left = 0;
        long right = 0;
        for (long p : power) {
            left = Math.min(left, p);
            right = Math.max(right, p);
        }
        right += k; // Upper bound: max power + all k stations in one place
        
        while (left <= right) {
            long mid = left + (right - left) / 2;
            if (canAchieve(power, r, k, mid)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return right;
    }
    
    /**
     * Checks if it's possible to achieve minimum power 'target' using at most k stations.
     * 
     * 🧠 Uses difference array technique for efficient range updates and greedy placement
     * strategy that always places stations as far right as possible to maximize coverage.
     * 
     * @param power initial power of each city
     * @param r range of power stations
     * @param k available additional stations
     * @param target minimum power to achieve
     * @return true if achievable, false otherwise
     */
    private boolean canAchieve(long[] power, int r, int k, long target) {
        int n = power.length;
        long[] diff = new long[n + 1]; // Difference array for range updates
        long add = 0; // Current additions from difference array prefix sum
        long used = 0; // Total stations used
        
        for (int i = 0; i < n; i++) {
            // Apply difference array updates
            add += diff[i];
            
            long currentPower = power[i] + add;
            if (currentPower < target) {
                long need = target - currentPower;
                used += need;
                if (used > k) {
                    return false;
                }
                
                int pos = Math.min(i + r, n - 1);
                add += need;
                
                // Update difference array for range [pos - r, pos + r]
                int rightBound = pos + r + 1;
                if (rightBound < n) {
                    diff[rightBound] -= need;
                }
            }
        }
        
        return true;
    }
}