
class Solution {
    public int[] getSneakyNumbers(int[] nums) {
        int n = nums.length - 2;
        
        boolean[] seen = new boolean[n + 1];
        int[] result = new int[2];
        int foundCount = 0;
        
        for (int num : nums) {
            if (seen[num]) {
                result[foundCount] = num;
                foundCount++;
                
                if (foundCount == 2) {
                    break;
                }
            } else {
                seen[num] = true;
            }
        }
        
        return result;
    }
}