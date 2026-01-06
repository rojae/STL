/*
 * @lc app=leetcode id=198 lang=java
 *
 * [198] House Robber
 */

// @lc code=start
class Solution {
    public int rob(int[] nums) {
        ////////////////////////////////////
        // this house do rob
        // dp[i-2]
        // this house doesn't rob
        // dp[i-1]
        ////////////////////////////////////
        // Formula : dp[i] = max(dp[i-1], dp[i-2]+dp[i]);

        int len = nums.length;
        int[] matrix = new int[len+1];
        matrix[0] = 0;
        matrix[1] = nums[0];

        for(int i=2; i<nums.length+1; i++){
            matrix[i] = Math.max(matrix[i-1], matrix[i-2] + nums[i-1]);    
        }

        return matrix[len];
    }
}
// @lc code=end

