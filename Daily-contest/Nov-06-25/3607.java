import java.util.*;

class Solution {
    public int[] processQueries(int c, int[][] connections, int[][] queries) {
        DSU dsu = new DSU(c + 1); // 1-based indexing
        for (int[] conn : connections) {
            dsu.union(conn[0], conn[1]);
        }

        // Initialize online status: all stations are initially online
        boolean[] online = new boolean[c + 1];
        Arrays.fill(online, true);

        // For each root, maintain a TreeSet of online stations in its component
        Map<Integer, TreeSet<Integer>> componentMin = new HashMap<>();
        for (int i = 1; i <= c; i++) {
            int root = dsu.find(i);
            componentMin.computeIfAbsent(root, k -> new TreeSet<>()).add(i);
        }

        List<Integer> result = new ArrayList<>();
        for (int[] query : queries) {
            int type = query[0];
            int x = query[1];
            if (type == 1) {
                if (online[x]) {
                    result.add(x);
                } else {
                    int root = dsu.find(x);
                    TreeSet<Integer> set = componentMin.get(root);
                    if (set == null || set.isEmpty()) {
                        result.add(-1);
                    } else {
                        result.add(set.first());
                    }
                }
            } else if (type == 2) {
                if (online[x]) {
                    online[x] = false;
                    int root = dsu.find(x);
                    TreeSet<Integer> set = componentMin.get(root);
                    if (set != null) {
                        set.remove(x);
                    }
                }
            }
        }

        int[] resArray = new int[result.size()];
        for (int i = 0; i < resArray.length; i++) {
            resArray[i] = result.get(i);
        }
        return resArray;
    }

    static class DSU {
        int[] parent;
        int[] rank;

        public DSU(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int xRoot = find(x);
            int yRoot = find(y);
            if (xRoot == yRoot) {
                return;
            }
            if (rank[xRoot] < rank[yRoot]) {
                parent[xRoot] = yRoot;
            } else {
                parent[yRoot] = xRoot;
                if (rank[xRoot] == rank[yRoot]) {
                    rank[xRoot]++;
                }
            }
        }
    }
}