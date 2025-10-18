class Solution {
    public int maxDistinctElements(int[] nums, int k) {
        Arrays.sort(nums);
        long lastAssigned = Long.MIN_VALUE; 
        int distinctCount = 0;
        
        for (int num : nums) {
            long candidate = Math.max(lastAssigned + 1, (long) num - k);
            if (candidate <= (long) num + k) {
                lastAssigned = candidate;
                distinctCount++;
            }
        }
        
        return distinctCount;
    }
}