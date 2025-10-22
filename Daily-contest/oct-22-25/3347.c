typedef struct {
    int key;
    int val;
    UT_hash_handle hh;
} HashItem;

HashItem* hashFindItem(HashItem** obj, int key) {
    HashItem* pEntry = NULL;
    HASH_FIND_INT(*obj, &key, pEntry);
    return pEntry;
}

bool hashAddItem(HashItem** obj, int key, int val) {
    if (hashFindItem(obj, key)) {
        return false;
    }
    HashItem* pEntry = (HashItem*)malloc(sizeof(HashItem));
    pEntry->key = key;
    pEntry->val = val;
    HASH_ADD_INT(*obj, key, pEntry);
    return true;
}

bool hashSetItem(HashItem** obj, int key, int val) {
    HashItem* pEntry = hashFindItem(obj, key);
    if (!pEntry) {
        hashAddItem(obj, key, val);
    } else {
        pEntry->val = val;
    }
    return true;
}

int hashGetItem(HashItem** obj, int key, int defaultVal) {
    HashItem* pEntry = hashFindItem(obj, key);
    if (!pEntry) {
        return defaultVal;
    }
    return pEntry->val;
}

void hashFree(HashItem** obj) {
    HashItem *curr = NULL, *tmp = NULL;
    HASH_ITER(hh, *obj, curr, tmp) {
        HASH_DEL(*obj, curr);
        free(curr);
    }
}

int compare(const void* a, const void* b) { return (*(int*)a - *(int*)b); }

void addMode(int* nums, int numsSize, int k, HashItem** modes, int value) {
    hashAddItem(modes, value, 1);
    if (value - k >= nums[0]) {
        hashAddItem(modes, value - k, 1);
    }
    if (value + k <= nums[numsSize - 1]) {
        hashAddItem(modes, value + k, 1);
    }
}

int leftBound(int* nums, int numsSize, int value) {
    int left = 0, right = numsSize - 1;
    while (left < right) {
        int mid = (left + right) / 2;
        if (nums[mid] < value) {
            left = mid + 1;
        } else {
            right = mid;
        }
    }
    return left;
}

int rightBound(int* nums, int numsSize, int value) {
    int left = 0, right = numsSize - 1;
    while (left < right) {
        int mid = (left + right + 1) / 2;
        if (nums[mid] > value) {
            right = mid - 1;
        } else {
            left = mid;
        }
    }
    return left;
}

int maxFrequency(int* nums, int numsSize, int k, int numOperations) {
    qsort(nums, numsSize, sizeof(int), compare);
    int ans = 0;
    HashItem* numCount = NULL;
    HashItem* modes = NULL;

    int lastNumIndex = 0;
    for (int i = 0; i < numsSize; ++i) {
        if (nums[i] != nums[lastNumIndex]) {
            hashSetItem(&numCount, nums[lastNumIndex], i - lastNumIndex);
            if (i - lastNumIndex > ans) {
                ans = i - lastNumIndex;
            }
            addMode(nums, numsSize, k, &modes, nums[lastNumIndex]);
            lastNumIndex = i;
        }
    }

    hashSetItem(&numCount, nums[lastNumIndex], numsSize - lastNumIndex);
    if (numsSize - lastNumIndex > ans) {
        ans = numsSize - lastNumIndex;
    }
    addMode(nums, numsSize, k, &modes, nums[lastNumIndex]);

    int modesSize = HASH_COUNT(modes);
    int* sortModes = (int*)malloc(sizeof(int) * modesSize);
    int pos = 0;
    for (HashItem* pEntry = modes; pEntry; pEntry = pEntry->hh.next) {
        sortModes[pos++] = pEntry->key;
    }
    qsort(sortModes, modesSize, sizeof(int), compare);

    for (int i = 0; i < modesSize; i++) {
        int mode = sortModes[i];
        int l = leftBound(nums, numsSize, mode - k);
        int r = rightBound(nums, numsSize, mode + k);
        int tempAns;
        if (hashFindItem(&numCount, mode)) {
            int count = hashGetItem(&numCount, mode, 0);
            tempAns = fmin(r - l + 1, count + numOperations);
        } else {
            tempAns = fmin(r - l + 1, numOperations);
        }
        if (tempAns > ans) {
            ans = tempAns;
        }
    }

    hashFree(&numCount);
    hashFree(&modes);
    free(sortModes);
    return ans;
}