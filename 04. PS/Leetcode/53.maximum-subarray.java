/*
 * @lc app=leetcode id=53 lang=java
 *
 * [53] Maximum Subarray
 */

// @lc code=start
class Solution {
    public int maxSubArray(int[] nums) {
        int current = nums[0];
        int max = nums[0];
        
        for(int i=1; i<nums.length; i++){
            current = Math.max(nums[i], current + nums[i]);
            max = Math.max(current, max);
        }

        return max;
    }
}
// @lc code=end

