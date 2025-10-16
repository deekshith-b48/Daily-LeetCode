class Solution:
    def findSmallestInteger(self, nums: List[int], value: int) -> int:
        # Count frequency of each residue class modulo 'value'
        count = [0] * value
        
        # Process each number and compute its non-negative residue
        for num in nums:
            # Handle negative numbers: (num % value + value) % value
            residue = ((num % value) + value) % value
            count[residue] += 1
        
        # Find the maximum MEX by checking constructibility of each integer
        # The maximum possible MEX is at most len(nums) + 1
        for x in range(len(nums) + 1):
            residue = x % value
            quotient = x // value
            
            # To construct integer x, we need at least (quotient + 1) numbers 
            # with residue 'residue'. If we have only 'quotient' or fewer,
            # then x cannot be constructed.
            if count[residue] <= quotient:
                return x
        
        # This line should never be reached since MEX <= len(nums) + 1
        return len(nums)