import java.util.*;

class Solution {
    public List<String> removeAnagrams(String[] words) {
        if (words.length == 0) return new ArrayList<>();
        List<String> result = new ArrayList<>();
        result.add(words[0]);
        for (int i = 1; i < words.length; i++) {
            if (!areAnagrams(result.get(result.size() - 1), words[i])) {
                result.add(words[i]);
            }
        }
        return result;
    }

    private boolean areAnagrams(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        Arrays.sort(c1);
        Arrays.sort(c2);
        return Arrays.equals(c1, c2);
    }
}
