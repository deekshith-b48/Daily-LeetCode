public class Solution {
    public int maxArea(int[] height) {
        int left = 0;                   // Left pointer
        int right = height.length - 1;  // Right pointer
        int maxArea = 0;                // Track maximum area found

        while (left < right) {
            // Calculate current area
            int width = right - left;
            int minHeight = Math.min(height[left], height[right]);
            int currentArea = width * minHeight;

            // Update maximum area
            maxArea = Math.max(maxArea, currentArea);

            // Move the pointer with smaller height inward
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }
}
