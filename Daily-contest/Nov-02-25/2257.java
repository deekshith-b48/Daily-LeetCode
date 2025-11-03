
class Solution {
    
    public int countUnguarded(int m, int n, int[][] guards, int[][] walls) {
      
        boolean[][] isGuard = new boolean[m][n];
        boolean[][] isWall = new boolean[m][n];
        boolean[][] isGuarded = new boolean[m][n];
        
        for (int[] guard : guards) {
            isGuard[guard[0]][guard[1]] = true;
        }
        for (int[] wall : walls) {
            isWall[wall[0]][wall[1]] = true;
        }
        
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};
        
        for (int[] guard : guards) {
            int x = guard[0], y = guard[1];
            
            for (int d = 0; d < 4; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];
                
                while (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                    if (isWall[nx][ny] || isGuard[nx][ny]) {
                        break;
                    }
                    isGuarded[nx][ny] = true;
                    
                    nx += dx[d];
                    ny += dy[d];
                }
            }
        }
        
        int unguardedCount = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (!isGuard[i][j] && !isWall[i][j] && !isGuarded[i][j]) {
                    unguardedCount++;
                }
            }
        }
        
        return unguardedCount;
    }
}