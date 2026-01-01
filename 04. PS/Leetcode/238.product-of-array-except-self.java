/*
 * @lc app=leetcode id=238 lang=java
 *
 * [238] Product of Array Except Self
 */

// @lc code=start


class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] answer = new int[n];

        // product incoming left side
        answer[0] = 1;
        for(int i=1; i<n; i++){
            answer[i] = answer[i-1] * nums[i-1];
        }

        // product incoming right side
        int suffix = 1;
        for(int i=n-1; i>=0; i--){
            answer[i] *= suffix;
            suffix *= nums[i];
        }

        return answer;
    }
}
// @lc code=end

