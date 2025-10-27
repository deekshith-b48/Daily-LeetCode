
class Solution {
    public int numberOfBeams(String[] bank) {
        int prevDevices = 0;
        int totalBeams = 0;
        
        // 🔄 Process each row
        for (String row : bank) {
            // 📊 Count devices in current row
            int currentDevices = 0;
            for (char c : row.toCharArray()) {
                if (c == '1') {
                    currentDevices++;
                }
            }
            
            // 🎯 If current row has devices, calculate beams with previous non-empty row
            if (currentDevices > 0) {
                totalBeams += prevDevices * currentDevices;
                prevDevices = currentDevices; // Update for next iteration
            }
        }
        
        return totalBeams;
    }
}