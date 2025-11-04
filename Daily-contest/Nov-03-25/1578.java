import java.util.*;

class Solution {
    public int minCost(String colors, int[] neededTime) {
        int n = colors.length();
        if (n <= 1) {
            return 0;
        }
        
        int totalTime = 0;
        char currentColor = colors.charAt(0);
        int groupSum = neededTime[0];
        int maxTime = neededTime[0];
        
        // 🔄 Process each balloon starting from index 1
        for (int i = 1; i < n; i++) {
            if (colors.charAt(i) == currentColor) {
                // 🟢 Same color: extend current group
                groupSum += neededTime[i];
                maxTime = Math.max(maxTime, neededTime[i]);
            } else {
                // 🟠 Different color: process previous group
                totalTime += groupSum - maxTime;
                // 🆕 Start new group
                currentColor = colors.charAt(i);
                groupSum = neededTime[i];
                maxTime = neededTime[i];
            }
        }
        
        // 🟣 Process final group
        totalTime += groupSum - maxTime;
        
        return totalTime;
    }
}       