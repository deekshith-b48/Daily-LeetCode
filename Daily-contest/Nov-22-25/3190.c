int minimumOperations(int* nums, int numsSize) {
    int sum = 0;
    for (int i = 0; i < numsSize; i++) {
        int remainder = nums[i] % 3;
        sum += (remainder < 3 - remainder) ? remainder : (3 - remainder);
    }
    return sum;
}