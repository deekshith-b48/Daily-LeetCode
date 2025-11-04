class Solution {

    public int[] findXSum(int[] nums, int k, int x) {
        int n = nums.length;
        int[] result = new int[n - k + 1];
        
        // 🔄 Process each starting position
        for (int i = 0; i <= n - k; i++) {
            // 📊 Count frequencies using array (values 1-50)
            int[] freq = new int[51]; // index 0 unused, 1-50 used
            for (int j = i; j < i + k; j++) {
                freq[nums[j]]++;
            }
            
            // 📋 Create list of (element, frequency) pairs
            List<int[]> elements = new ArrayList<>();
            for (int val = 1; val <= 50; val++) {
                if (freq[val] > 0) {
                    elements.add(new int[]{val, freq[val]});
                }
            }
            
            // 🎯 Sort by frequency (desc), then by value (desc)
            elements.sort((a, b) -> {
                if (a[1] != b[1]) {
                    return b[1] - a[1]; // frequency descending
                }
                return b[0] - a[0]; // value descending
            });
            
            // 💰 Calculate x-sum: sum first x elements' total contributions
            int xSum = 0;
            int elementsToTake = Math.min(x, elements.size());
            for (int j = 0; j < elementsToTake; j++) {
                int[] element = elements.get(j);
                xSum += element[0] * element[1]; // value * frequency
            }
            
            result[i] = xSum;
        }
        
        return result;
    }
}