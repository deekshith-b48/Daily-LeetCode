
class Solution {
    public boolean hasSameDigits(String s) {
        // \U0001f4ca Convert string to list of integers for easier manipulation
        List<Integer> digits = new ArrayList<>();
        for (char c : s.toCharArray()) {
            digits.add(c - '0');
        }
        
        // \U0001f501 Repeatedly apply the operation until only 2 digits remain
        while (digits.size() > 2) {
            List<Integer> newDigits = new ArrayList<>();
            
            // \U0001f4d0 Compute adjacent sums modulo 10
            for (int i = 0; i < digits.size() - 1; i++) {
                int sum = (digits.get(i) + digits.get(i + 1)) % 10;
                newDigits.add(sum);
            }
            
            // \U0001f504 Replace current digits with new digits
            digits = newDigits;
        }
        
        // ✅ Check if the final two digits are equal
        return digits.get(0) == digits.get(1);
    }
}