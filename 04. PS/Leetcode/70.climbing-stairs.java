/*
 * @lc app=leetcode id=70 lang=java
 *
 * [70] Climbing Stairs
 */

// @lc code=start
class Solution {
    public int climbStairs(int n) {
        int[] dp = new int[n+1];

        // only can 1 or 2 step
        // 1 -> 1
        // 2 -> 1, 1 -> 2
        // 3 -> 1, 2 -> 3
        // when 4 -> 5
        //      1+1+1+1
        //      1+1+2
        //      1+2+1
        //      2+1+1
        //      2+2
        // when 5 -> 8
        //      1+1+1+1+1
        //      1+1+1+2
        //      1+1+2+1
        //      1+2+1+1
        //      2+1+1+1
        //      1+2+2
        //      2+1+2
        //      2+2+1
        // dynamic logic : dp[n] = dp[n-1] + dp[n-2]
        dp[0] = 1;
        dp[1] = 1;

        for(int i=2; i<n+1; i++){
            dp[i] = dp[i-1] + dp[i-2];
        }

        return dp[n];
    }
}
// @lc code=end

