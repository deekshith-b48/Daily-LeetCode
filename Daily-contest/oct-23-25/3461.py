class Solution:
    def hasSameDigits(self, s: str) -> bool:
        while len(s) > 2:
            new_s = []
            for i in range(len(s) - 1):
                total = int(s[i]) + int(s[i+1])
                new_s.append(str(total % 10))
            s = ''.join(new_s)
        return s[0] == s[1]