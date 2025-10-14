import java.util.List;

class Solution {
    public boolean hasIncreasingSubarrays(List<Integer> nums, int k) {
        int n = nums.size();
        for (int a = 0; a <= n - 2 * k; a++) {
            boolean firstIncreasing = true;
            for (int i = a; i < a + k - 1; i++) {
                if (nums.get(i) >= nums.get(i + 1)) {
                    firstIncreasing = false;
                    break;
                }
            }
            if (!firstIncreasing) continue;
            boolean secondIncreasing = true;
            for (int i = a + k; i < a + 2 * k - 1; i++) {
                if (nums.get(i) >= nums.get(i + 1)) {
                    secondIncreasing = false;
                    break;
                }
            }
            if (firstIncreasing && secondIncreasing) return true;
        }
        return false;
    }
}
