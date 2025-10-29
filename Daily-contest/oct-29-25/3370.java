class Solution {
    public int smallestNumber(int n) {
        int[] allSetBits = {1, 3, 7, 15, 31, 63, 127, 255, 511, 1023};
        
        for (int num : allSetBits) {
            if (num >= n) {
                return num;
            }
        }
        return 1023;
    }
}