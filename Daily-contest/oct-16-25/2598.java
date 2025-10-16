
class Solution {
        public int findSmallestInteger(int[] nums, int value) {
        int[] count = new int[value];
        
        for (int num : nums) {
            int residue = ((num % value) + value) % value;
            count[residue]++;
        }
        for (int x = 0; x <= nums.length; x++) {
            int residue = x % value;
            int quotient = x / value;
            if (count[residue] <= quotient) {
                return x;
            }
        }
        return nums.length;
    }
}