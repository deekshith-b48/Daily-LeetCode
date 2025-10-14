class Solution:
    def hasIncreasingSubarrays(self, nums: List[int], k: int) -> bool:
        n = len(nums)
        for a in range(n - 2 * k + 1):
            first_increasing = True
            for i in range(a, a + k - 1):
                if nums[i] >= nums[i + 1]:
                    first_increasing = False
                    break
            if not first_increasing:
                continue
            second_increasing = True
            for i in range(a + k, a + 2 * k - 1):
                if nums[i] >= nums[i + 1]:
                    second_increasing = False
                    break
            if first_increasing and second_increasing:
                return True
        return False
