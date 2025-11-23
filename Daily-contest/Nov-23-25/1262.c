static int cmp(const void* a, const void* b) { return *(int*)b - *(int*)a; }

int maxSumDivThree(int* nums, int numsSize) {
    // Use v[0], v[1], v[2] to represent a, b, c respectively.
    int n = numsSize;
    int v[3][n];
    int vColSize[3];
    memset(vColSize, 0, sizeof(vColSize));
    for (int i = 0; i < numsSize; i++) {
        v[nums[i] % 3][vColSize[nums[i] % 3]++] = nums[i];
    }
    qsort(v[1], vColSize[1], sizeof(int), cmp);
    qsort(v[2], vColSize[2], sizeof(int), cmp);

    int ans = 0;
    int lb = vColSize[1], lc = vColSize[2];
    for (int cntb = lb - 2; cntb <= lb; ++cntb) {
        if (cntb >= 0) {
            for (int cntc = lc - 2; cntc <= lc; ++cntc) {
                if (cntc >= 0 && (cntb - cntc) % 3 == 0) {
                    int sum1 = 0, sum2 = 0;
                    for (int i = 0; i < cntb; i++) {
                        sum1 += v[1][i];
                    }
                    for (int i = 0; i < cntc; i++) {
                        sum2 += v[2][i];
                    }
                    ans = fmax(ans, sum1 + sum2);
                }
            }
        }
    }
    for (int i = 0; i < vColSize[0]; i++) {
        ans += v[0][i];
    }
    return ans;
}
