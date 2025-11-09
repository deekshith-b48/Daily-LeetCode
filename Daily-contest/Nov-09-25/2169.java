
class Solution {
    
    public int countOperations(int num1, int num2) {
        // 🎯 Edge case: already at zero
        if (num1 == 0 || num2 == 0) {
            return 0;
        }
        
        int operations = 0;
        
        // 🔁 Continue until one number becomes zero
        while (num1 > 0 && num2 > 0) {
            if (num1 >= num2) {
                // 📈 Add quotient to operations count
                operations += num1 / num2;
                // 📉 Update num1 to remainder
                num1 %= num2;
            } else {
                // 📈 Add quotient to operations count
                operations += num2 / num1;
                // 📉 Update num2 to remainder  
                num2 %= num1;
            }
        }
        
        return operations;
    }
}