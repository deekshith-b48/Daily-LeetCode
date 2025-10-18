class Solution:
    def maxDistinctElements(self, nums: List[int], k: int) -> int:
        N = len(nums)
        nums.sort()

        ans = 0
        # Try to use the smallest number possible for each given value
        maxVal = -inf
        for val in nums:
            if maxVal < val - k:
                # This val can be made distinct by swapping it with the smallest value it can make
                maxVal = val - k
                ans += 1
            elif maxVal < val + k:
                # This val can be made distinct by using another number in between (val-k, val+k]
                # Use the next smallest one, which is +1 of our largest one so far
                maxVal = maxVal + 1
                ans += 1                   

        return ans