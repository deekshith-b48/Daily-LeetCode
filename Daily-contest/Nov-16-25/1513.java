
class Solution {
    private static final int MOD = 1000000007;
    
    public int numSub(String s) {
        long result = 0;
        int currentCount = 0;
        
        for (char c : s.toCharArray()) {
            if (c == '1') {
                currentCount++;
                result = (result + currentCount) % MOD;
            } else {
                currentCount = 0;
            }
        }
        
        return (int) result;
    }
}