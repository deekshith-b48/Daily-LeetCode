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

typedef struct {
    int freq;
    int num;
} FreqPair;

int compareFreq(const void* a, const void* b) {
    FreqPair* pa = (FreqPair*)a;
    FreqPair* pb = (FreqPair*)b;
    if (pa->freq != pb->freq) {
        return pb->freq - pa->freq;
    }
    return pb->num - pa->num;
}

int* findXSum(int* nums, int numsSize, int k, int x, int* returnSize) {
    *returnSize = numsSize - k + 1;
    int* ans = (int*)malloc(*returnSize * sizeof(int));

    for (int i = 0; i <= numsSize - k; ++i) {
        HashItem* cnt = NULL;
        for (int j = i; j < i + k; ++j) {
            hashSetItem(&cnt, nums[j], hashGetItem(&cnt, nums[j], 0) + 1);
        }

        int len = HASH_COUNT(cnt);
        FreqPair* freq = (FreqPair*)malloc(len * sizeof(FreqPair));
        int pos = 0;
        for (HashItem* pEntry = cnt; pEntry; pEntry = pEntry->hh.next) {
            freq[pos].freq = pEntry->val;
            freq[pos].num = pEntry->key;
            pos++;
        }
        qsort(freq, len, sizeof(FreqPair), compareFreq);
        int xsum = 0;
        for (int j = 0; j < x && j < len; ++j) {
            xsum += freq[j].freq * freq[j].num;
        }
        ans[i] = xsum;
        hashFree(&cnt);
        free(freq);
    }

    return ans;
}