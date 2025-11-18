
class Solution {

    public boolean isOneBitCharacter(int[] bits) {
        int count = 0;
        int i = bits.length - 2;
        
       while (i >= 0 && bits[i] == 1) {
            count++;
            i--;
        }
        
        return count % 2 == 0;
    }
}