import java.util.*;

class Solution {
    public int[] avoidFlood(int[] rains) {
        int n = rains.length;
        // Initialize answer array; default to 1 for dry days (can be any lake)
        int[] ans = new int[n];
        Arrays.fill(ans, 1);
        
        // Tracks last rain day for each lake: lake -> lastRainDay
        Map<Integer, Integer> full = new HashMap<>();
        
        // Stores indices of dry days (rains[i] == 0) in sorted order
        TreeSet<Integer> dryDays = new TreeSet<>();
        
        for (int i = 0; i < n; i++) {
            if (rains[i] > 0) {
                int lake = rains[i];
                ans[i] = -1; // Must be -1 on rainy days
                
                // If this lake is already full, we need to have dried it since last rain
                if (full.containsKey(lake)) {
                    int lastRainDay = full.get(lake);
                    // Find the earliest dry day AFTER the last rain on this lake
                    Integer dryDay = dryDays.ceiling(lastRainDay + 1);
                    
                    // If no such dry day exists, flood is unavoidable
                    if (dryDay == null) {
                        return new int[0];
                    }
                    
                    // Use this dry day to dry the lake
                    ans[dryDay] = lake;
                    dryDays.remove(dryDay); // This dry day is now used
                }
                
                // Update the last rain day for this lake
                full.put(lake, i);
                
            } else {
                // It's a dry day: record the index for future use
                dryDays.add(i);
                // ans[i] remains 1 (or any value) if unused — problem allows drying empty lakes
            }
        }
        
        return ans;
    }
}