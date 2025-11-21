
class Solution {
    public int countPalindromicSubsequence(String s) {
        int n = s.length();
        
        int[] first = new int[26];
        int[] last = new int[26];
        
        for (int i = 0; i < 26; i++) {
            first[i] = -1;
            last[i] = -1;
        }
        
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            int idx = c - 'a';
            
            if (first[idx] == -1) {
                first[idx] = i;
            }
            last[idx] = i;
        }
        
        int result = 0;
        
        for (int c = 0; c < 26; c++) {
            if (first[c] == -1 || last[c] == first[c]) {
                continue;
            }
            
            boolean[] seen = new boolean[26];
            
            for (int i = first[c] + 1; i < last[c]; i++) {
                seen[s.charAt(i) - 'a'] = true;
            }
            
            // 🧮 Count unique middle characters
            for (boolean exists : seen) {
                if (exists) {
                    result++;
                }
            }
        }
        
        return result;
    }
}