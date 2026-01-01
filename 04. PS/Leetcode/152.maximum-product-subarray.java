/*
 * @lc app=leetcode id=152 lang=java
 *
 * [152] Maximum Product Subarray
 */

// @lc code=start
class Solution {
    public int maxProduct(int[] nums) {
        int answer = nums[0];
        int min = nums[0];
        int max = nums[0];

        for(int i=1; i<nums.length; i++){
            int temp = Math.min(nums[i], Math.min(min * nums[i], max * nums[i]));
            max = Math.max(nums[i], Math.max(min * nums[i], max * nums[i]));
            min = temp;
            answer = Math.max(answer, Math.max(min, max));
        }

        return answer;
    }
}
// @lc code=end

