
class Solution {
    public int maximumEnergy(int[] energy, int k) {
        int n = energy.length;
        int maxEnergy = Integer.MIN_VALUE;
        
        for (int r = 0; r < k; r++) {
            int currentSum = 0;
            int i = r + k * ((n - 1 - r) / k);
            
            while (i >= r) {
                currentSum += energy[i];
                maxEnergy = Math.max(maxEnergy, currentSum);
                i -= k;
            }
        }
        
        return maxEnergy;
    }
}