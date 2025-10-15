class Solution:
    def maxIncreasingSubarrays(self, nums: List[int]) -> int:
        n = len(nums)
        inc = [1] * n
        for i in range(1, n):
            if nums[i] > nums[i - 1]:
                inc[i] = inc[i - 1] + 1
            else:
                inc[i] = 1

        def is_valid(k: int) -> bool:
            for a in range(n - 2 * k + 1):
                if inc[a + k - 1] >= k and inc[a + 2 * k - 1] >= k:
                    return True
            return False

        left, right = 1, n // 2
        result = 0
        while left <= right:
            k = (left + right) // 2
            if is_valid(k):
                result = k
                left = k + 1
            else:
                right = k - 1
        return result
