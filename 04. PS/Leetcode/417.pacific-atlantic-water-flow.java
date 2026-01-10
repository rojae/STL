/*
 * @lc app=leetcode id=417 lang=java
 *
 * [417] Pacific Atlantic Water Flow
 */

// @lc code=start

import java.util.ArrayList;
import java.util.List;

class Solution {

    int[][] canMove = {
        {-1, 0}, {0, -1}, {1, 0}, {0, 1}
    };
    
    int m, n;

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        m = heights.length;
        n = heights[0].length;
        
        boolean[][] pacific = new boolean[m][n];
        boolean[][] atlantic = new boolean[m][n];
        List<List<Integer>> result = new ArrayList<>();

        // pacific
        for(int i=0; i<m; i++)
            dfs(heights, pacific, i, 0);
        for(int i=0; i<n; i++)
            dfs(heights, pacific, 0, i);

        // atlantic
        for(int i=0; i<m; i++)
            dfs(heights, atlantic, i, n-1);
        for(int i=0; i<n; i++)
            dfs(heights, atlantic, m-1, i);

        // pacific & atlantic
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                if(pacific[i][j] && atlantic[i][j]){
                    result.add(List.of(i, j));
                }
            }
        }

        return result;
    }

    void dfs(int[][] heights, boolean[][] visited, int r, int c){
        visited[r][c] = true;

        for(int[] move : canMove){
            int nr = r + move[0];
            int nc = c + move[1];
            
            if(nr < 0 || nr >= m || nc < 0 || nc >= n)
                continue;
            if(visited[nr][nc])
                continue;
            if(heights[nr][nc] < heights[r][c])
                continue;

            dfs(heights, visited, nr, nc);
        }
    }
}
// @lc code=end

