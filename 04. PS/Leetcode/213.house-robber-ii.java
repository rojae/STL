/*
 * @lc app=leetcode id=213 lang=java
 *
 * [213] House Robber II
 */

// @lc code=start
class Solution {
    public int rob(int[] nums) {
        if(nums.length == 1)
            return nums[0];

        // two-case
        // 1. first-house included, except last-house
        // 2. first-house excepted, included last-house
        return Math.max(
            robRange(nums, 0, nums.length-2),
            robRange(nums, 1, nums.length-1)
        );
    }

    int robRange(int[] nums, int start, int end){
        int prev2 = 0, prev1 = 0;

        for(int i=start; i<=end; i++){
            int current = Math.max(prev1, prev2 + nums[i]);
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }
}
// @lc code=end

