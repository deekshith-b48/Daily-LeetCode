class Solution {
    public int maxIncreasingSubarrays(List<Integer> nums) {
        int n = nums.size();
        int[] inc = new int[n];
        inc[0] = 1;
        for (int i = 1; i < n; i++) {
            if (nums.get(i) > nums.get(i - 1)) {
                inc[i] = inc[i - 1] + 1;
            } else {
                inc[i] = 1;
            }
        }

        int left = 1, right = n / 2, result = 0;
        while (left <= right) {
            int k = left + (right - left) / 2;
            if (isValid(k, inc, n)) {
                result = k;
                left = k + 1;
            } else {
                right = k - 1;
            }
        }
        return result;
    }

    private boolean isValid(int k, int[] inc, int n) {
        for (int a = 0; a <= n - 2 * k; a++) {
            if (inc[a + k - 1] >= k && inc[a + 2 * k - 1] >= k) {
                return true;
            }
        }
        return false;
    }
}
