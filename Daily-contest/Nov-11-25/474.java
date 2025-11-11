
class Solution {
   
    public int findMaxForm(String[] strs, int m, int n) {
        // 📊 DP table: dp[j][k] = max strings with j zeros and k ones
        int[][] dp = new int[m + 1][n + 1];
        
        // 🔁 Process each string
        for (String str : strs) {
            // 📈 Count zeros and ones in current string
            int zeros = 0, ones = 0;
            for (char c : str.toCharArray()) {
                if (c == '0') {
                    zeros++;
                } else {
                    ones++;
                }
            }
            
            // 🚫 Skip if string exceeds individual constraints
            if (zeros > m || ones > n) {
                continue;
            }
            
            // 🔙 Update DP table in reverse order (0-1 knapsack)
            for (int j = m; j >= zeros; j--) {
                for (int k = n; k >= ones; k--) {
                    // 🎯 Choose maximum between including and excluding current string
                    dp[j][k] = Math.max(dp[j][k], dp[j - zeros][k - ones] + 1);
                }
            }
        }
        
        return dp[m][n];
    }
}