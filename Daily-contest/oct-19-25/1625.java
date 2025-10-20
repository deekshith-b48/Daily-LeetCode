class Solution {
    public String findLexSmallestString(String s, int a, int b) {
        int n = s.length();
        String best = s;
        
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer(s);
        visited.add(s);
        
        while (!queue.isEmpty()) {
            String current = queue.poll();
            
            String candidate = tryAddOperations(current, a, b);
            if (candidate.compareTo(best) < 0) {
                best = candidate;
            }
            
            String rotated = rotateRight(current, b);
            if (!visited.contains(rotated)) {
                visited.add(rotated);
                queue.offer(rotated);
            }
        }
        
        return best;
    }
    
    
    private String tryAddOperations(String s, int a, int b) {
        int n = s.length();
        String best = s;
        
        
        for (int addOdd = 0; addOdd < 10; addOdd++) {
            StringBuilder sb = new StringBuilder(s);
            
            
            for (int i = 1; i < n; i += 2) {
                int digit = s.charAt(i) - '0';
                int newDigit = (digit + addOdd * a) % 10;
                sb.setCharAt(i, (char) ('0' + newDigit));
            }
            
            String withOdd = sb.toString();
            if (b % 2 == 1) {
                
                for (int addEven = 0; addEven < 10; addEven++) {
                    StringBuilder sb2 = new StringBuilder(withOdd);
                    for (int i = 0; i < n; i += 2) {
                        int digit = withOdd.charAt(i) - '0';
                        int newDigit = (digit + addEven * a) % 10;
                        sb2.setCharAt(i, (char) ('0' + newDigit));
                    }
                    String candidate = sb2.toString();
                    if (candidate.compareTo(best) < 0) {
                        best = candidate;
                    }
                }
            } else {
                
                if (withOdd.compareTo(best) < 0) {
                    best = withOdd;
                }
            }
        }
        
        return best;
    }
    
    /**
     * Rotates the string right by b positions.
     */
    private String rotateRight(String s, int b) {
        int n = s.length();
        b = b % n;
        if (b == 0) return s;
        return s.substring(n - b) + s.substring(0, n - b);
    }
}