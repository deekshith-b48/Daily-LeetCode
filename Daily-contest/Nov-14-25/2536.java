
class Solution {
    
    public int[][] rangeAddQueries(int n, int[][] queries) {
        // 📋 Initialize (n+1) × (n+1) difference array to avoid boundary checks
        int[][] diff = new int[n + 1][n + 1];
        
        // 🔁 Process each query with 2D difference array updates
        for (int[] query : queries) {
            int r1 = query[0];
            int c1 = query[1];
            int r2 = query[2];
            int c2 = query[3];
            
            // 📌 Mark four corners of the query rectangle
            diff[r1][c1] += 1;
            diff[r1][c2 + 1] -= 1;
            diff[r2 + 1][c1] -= 1;
            diff[r2 + 1][c2 + 1] += 1;
        }
        
        // 🧮 Reconstruct final matrix using 2D prefix sum
        int[][] mat = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // 📈 Compute 2D prefix sum
                mat[i][j] = diff[i][j];
                if (i > 0) {
                    mat[i][j] += mat[i - 1][j];
                }
                if (j > 0) {
                    mat[i][j] += mat[i][j - 1];
                }
                if (i > 0 && j > 0) {
                    mat[i][j] -= mat[i - 1][j - 1];
                }
            }
        }
        
        return mat;
    }
}